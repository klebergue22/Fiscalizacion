package com.igm.bodegas.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alexander Jimenez
 */
public class crudBodega {
     @PersistenceContext(unitName = "inventario")
        protected EntityManager em;
     
     public void insertar(Object entidad) throws Exception {
        try {
            em.persist(entidad);
            //em.refresh(entidad);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
     
     public void actualizar(Object entidad) {
        try {
            int numero;
            em.merge(entidad);
//            em.refresh(entidad);
        } catch (Exception e) {
            //logger.error("No se puede actualizar. ", e);
        }
        //return entidad;

    }    
    
}
