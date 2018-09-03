/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ja.amortegui10
 */
@Stateless
public class SillaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(SillaPersistence.class.getName());
    
    @PersistenceContext(unitName = "DnsPU")
    EntityManager em;
    
    public SillaEntity create(SillaEntity silla)
    {
        LOGGER.log(Level.INFO, "Creando una nueva silla.");
        em.persist(silla);
        LOGGER.log(Level.INFO, "Una nueva silla creada.");
        return silla;
    }
    
    public SillaEntity find(Long id)
    {
        return em.find(SillaEntity.class, id);
    }
    
    public List<SillaEntity> findAll()
    {
        TypedQuery<SillaEntity> query = em.createQuery("", SillaEntity.class);
        return query.getResultList();
    }
    
    public SillaEntity udate(SillaEntity silla)
    {
        return em.merge(silla);
    }
    
    public void delete(Long id)
    {
        SillaEntity silla = find(id);
        em.remove(silla);
    }
}