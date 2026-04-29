/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.rh.servicio;

import gob.igm.rh.dao.crudRH;
import gob.igm.rh.modelo.VAccionesPer;
import gob.igm.rh.modelo.VDatoEmp;
import gob.igm.rh.modelo.VGestionesVigentes;
import java.math.BigDecimal;
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
public class DatosEmpleadoServicio extends crudRH{
    
    public List<VDatoEmp> getAllEmpleados(){
    
        return super.em.createNamedQuery("VDatoEmp.findAll", VDatoEmp.class).getResultList();
    }
    
    public List<VAccionesPer> getAllAcciones(){
    
        return super.em.createNamedQuery("VAccionesPer.findAll", VAccionesPer.class).getResultList();
    }
    
    public List<VGestionesVigentes> obtenerTodasGestiones(){
    
        return super.em.createNamedQuery("VGestionesVigentes.findAll", VGestionesVigentes.class).getResultList();
    }
    
    public List<VAccionesPer> listadoAcciones(Integer idPersona) {
            List<VAccionesPer> listadoAcc;
            
            Query query = super.em.createQuery("select o from VAccionesPer as o "
                    + "where o.noPersona = ?1 ");
            query.setParameter(1, idPersona);
          
            
            //Query query = em.createQuery("SELECT v FROM VAccionesPer v WHERE v.noPersona = :noPersona ");
                   // + "WHERE c.noPersona = :id ");
            
           
            listadoAcc = query.getResultList();
            
            return listadoAcc;
    } 
    
    
       public List<VDatoEmp> obtenerCodigoTimbrado(String cedula){
    
        List<VDatoEmp> listadoCodigo;
            
            Query query = super.em.createQuery("select o from VDatoEmp as o "
                    + "where o.noCedula = ?1 ");
            query.setParameter(1, cedula);
          
            
            //Query query = em.createQuery("SELECT v FROM VAccionesPer v WHERE v.noPersona = :noPersona ");
                   // + "WHERE c.noPersona = :id ");
            
           
            listadoCodigo = query.getResultList();
            
            return listadoCodigo;
    }
    
    
}
