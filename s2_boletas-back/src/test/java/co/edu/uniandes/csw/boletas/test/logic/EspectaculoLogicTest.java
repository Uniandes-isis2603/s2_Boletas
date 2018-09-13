/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class EspectaculoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EspectaculoLogic espectaculoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
            
    private List<EspectaculoEntity> data = new ArrayList<EspectaculoEntity>();
    
    private List<ComentarioEntity> comentarios = new ArrayList<ComentarioEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspectaculoEntity.class.getPackage())
                .addPackage(EspectaculoLogic.class.getPackage())
                .addPackage(EspectaculoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData() {
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentario);
            comentarios.add(comentario);
        }
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity entity = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comentarios.get(i).setEspectaculo(entity);
            }
        }
    }
    
    @Test
    public void createEditorialTest() throws BusinessLogicException
    {
        EspectaculoEntity newEntity = factory.manufacturePojo(EspectaculoEntity.class);
        EspectaculoEntity result = espectaculoLogic.createEntity(newEntity);
        Assert.assertNotNull(result);
        EspectaculoEntity entity = em.find(EspectaculoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createEditorialConMismoNombreTest() throws BusinessLogicException {
        EspectaculoEntity newEntity = factory.manufacturePojo(EspectaculoEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        espectaculoLogic.createEntity(newEntity);
    }
}
