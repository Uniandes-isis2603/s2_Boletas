/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.dtos.SillaDTO;
import co.edu.uniandes.csw.boletas.ejb.SillaLogic;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author ja.amortegui10
 */

@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped //A partir de ahí va a iniciar una transacción.
public class SillaResource {
    private static final Logger LOGGER = Logger.getLogger(SillaResource.class.getName());
    @Inject
    private SillaLogic logic;
    
    @POST
    public SillaDTO createSilla(SillaDTO sillaDTO)throws WebApplicationException
    {
        LOGGER.info("SillaResource createSilla: input: " + SillaResource.class.getName());
        SillaEntity created = null;
        try
        {
            created = logic.createSilla(sillaDTO.toEntity());
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        
        return new SillaDTO(created);
    }
    
    @PUT
    @Path("{silla_id : \\d+}")
    public SillaDTO updateSilla(@PathParam("silla_id") Long id, SillaDTO sillaDTO)throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "SillaResource updateSilla: ", sillaDTO.toString());
        SillaEntity sillaEntity = sillaDTO.toEntity();
        SillaEntity updated = null;
        try
        {
            updated = logic.updateSilla(id, sillaEntity);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return new SillaDTO(updated);
    }
    
    @GET
    @Path("{silla_id: \\d+}")
    public SillaDTO getSilla(@PathParam("silla_id")Long silla_id)
    {
        SillaEntity finded = logic.getSillaById(silla_id);
        return new SillaDTO(finded);
    }
    
    @GET
    public List<SillaDTO> getSillas()
    {
        List<SillaEntity> sillasEntity = logic.getSillas();
        List<SillaDTO> sillasDTO = new ArrayList<SillaDTO>();
        for(SillaEntity entitieActual : sillasEntity)
            sillasDTO.add(new SillaDTO(entitieActual));
        return sillasDTO;
    }
    
    @DELETE
    @Path("{silla_id: \\d+}")
    public SillaDTO deleteSilla(@PathParam("silla_id")Long id)
    {
        return null;
    }
}
