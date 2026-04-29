/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.rh.servicio;

import gob.igm.rh.dao.crudRH;
import gob.igm.rh.modelo.TRhTipoAsistencia;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Alexander Jimenez
 */
@Stateless
@LocalBean
public class TipoAsistenciaServicio extends crudRH {
    
      public List<TRhTipoAsistencia> getAllTipoAsistencia(){
    
        return super.em.createNamedQuery("TRhTipoAsistencia.findAll", TRhTipoAsistencia.class).getResultList();
    }
      
    public List<TRhTipoAsistencia> listadoTipoPermiso() {
        List<TRhTipoAsistencia> listadoAcc;

        Query query = super.em.createQuery("select o from TRhTipoAsistencia as o "
                + "where o.noAsistAux != NULL ");
        
        //Query query = em.createQuery("SELECT v FROM VAccionesPer v WHERE v.noPersona = :noPersona ");
        // + "WHERE c.noPersona = :id ");
        listadoAcc = query.getResultList();

        return listadoAcc;
    }
    
}
