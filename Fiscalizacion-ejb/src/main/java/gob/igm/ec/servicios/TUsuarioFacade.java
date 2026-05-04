/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.TUsuarios;
import java.math.BigDecimal;
import java.math.BigInteger;
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
            final boolean tieneEstado = this.tieneColumnaEstado();
            final String sql = tieneEstado
                    ? "SELECT ID_USUARIO, USUARIO, PASSWORD, NOMBRE_COMPLETO, PERFIL, DIGNIDAD, ID_DIGNIDAD, BODEGA, ID_PERFIL, ESTADO "
                    + "FROM T_USUARIOS WHERE USUARIO = ? AND PASSWORD = ?"
                    : "SELECT ID_USUARIO, USUARIO, PASSWORD, NOMBRE_COMPLETO, PERFIL, DIGNIDAD, ID_DIGNIDAD, BODEGA, ID_PERFIL "
                    + "FROM T_USUARIOS WHERE USUARIO = ? AND PASSWORD = ?";

            Query query = super.em.createNativeQuery(sql);
            query.setParameter(1, usuario);
            query.setParameter(2, clave);
            Object[] row = (Object[]) query.getSingleResult();

            TUsuarios tu = new TUsuarios();
            tu.setIdUsuario((BigDecimal) row[0]);
            tu.setUsuario((String) row[1]);
            tu.setPassword((String) row[2]);
            tu.setNombreCompleto((String) row[3]);
            tu.setPerfil((String) row[4]);
            tu.setDignidad((String) row[5]);
            tu.setIdDignidad(this.toBigInteger(row[6]));
            tu.setBodega(this.toBigInteger(row[7]));
            tu.setEstado(tieneEstado ? this.toBigInteger(row[9]) : null);
            return tu;
        } catch (NoResultException e) {
            throw new Exception(e.getMessage(), e);
        } catch (NonUniqueResultException e) {
            throw new Exception(e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private BigInteger toBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof Number) {
            return BigInteger.valueOf(((Number) value).longValue());
        }
        return new BigInteger(value.toString());
    }

    private boolean tieneColumnaEstado() {
        Query query = super.em.createNativeQuery("SELECT COUNT(1) FROM ALL_TAB_COLUMNS WHERE OWNER = USER AND TABLE_NAME = 'T_USUARIOS' AND COLUMN_NAME = 'ESTADO'");
        Number total = (Number) query.getSingleResult();
        return total != null && total.intValue() > 0;
    }
}
