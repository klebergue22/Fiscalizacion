/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.TUsuarios;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author VERA_MAYRA
 */
@Stateless
@LocalBean
public class TUsuarioFacade extends CrudDAO {
    
     /**
      * Lista todos los usuarios
      * @return 
      */
     public  List<TUsuarios> getAll()
    {
        return super.em.createNamedQuery("TUsuarios.findAll",TUsuarios.class).getResultList();
    }
     
     /**
      * Obtiene el máximo del id del usuario
      * @return
      * @throws Exception 
      */
     public BigDecimal maximoID() throws Exception {
        try {
            Query query = super.em.createQuery("select max(o.idUsuario) "
                    + "from TUsuarios as o ");
            return ((BigDecimal) query.getSingleResult()); 
        } catch (Exception e) {
           throw new Exception(e.getMessage(), e);
        }
    }
     
     /**
      * Busca los usuarios
      * @param usuario
      * @param clave
      * @return
      * @throws Exception 
      */
     public TUsuarios buscarUsuarioClave(final String usuario, final String clave) throws Exception {
        try {
            Query query = super.em.createQuery("select o from TUsuarios as o "
                    + "where o.usuario = ?1 and o.password = ?2");
            query.setParameter(1, usuario);
            query.setParameter(2, clave);
            return (TUsuarios) query.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception(e.getMessage(), e);
        } catch (NonUniqueResultException e) {
            throw new Exception(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }   
}
