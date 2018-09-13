/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class LugarDetailDTO extends LugarDTO implements Serializable {
    
    private List<SillaDTO> sillas;
    
    public LugarDetailDTO(LugarEntity entity)
    {
        super(entity);
        sillas = new ArrayList<SillaDTO>();
        
        List<SillaEntity> sillasEntities = entity.getSillas();
        if(sillasEntities != null)
            for(SillaEntity sillaEntityActual : sillasEntities)
                this.sillas.add(new SillaDTO(sillaEntityActual));
    }
    
    public LugarEntity toEntity()
    {
        LugarEntity lugarEntity = super.toEntity();
        List<SillaEntity> sillasEntities = new ArrayList<SillaEntity>();
        for(SillaDTO sillaDTOActual : sillas)
            sillasEntities.add(sillaDTOActual.toEntity());
        lugarEntity.setSillas(sillasEntities);
        return lugarEntity;
    }

    public void setSillas(List<SillaDTO> sillas) {
        this.sillas = sillas;
    }

    public List<SillaDTO> getSillas() {
        return sillas;
    }
}