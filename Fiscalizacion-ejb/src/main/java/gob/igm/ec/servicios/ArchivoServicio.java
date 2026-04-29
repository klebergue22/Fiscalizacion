/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.Archivo;
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
public class ArchivoServicio extends CrudDAO{
    
    public  List<Archivo> getAllArchivos()
    {
        return super.em.createNamedQuery("Archivo.findAll",Archivo.class).getResultList();
    }  
    
    public List<Archivo> listadoArchivos(BigDecimal idArchivo) {
            List<Archivo> listadoArch;
            //BigDecimal idPoapac = new BigDecimal("186");
            Query query = em.createQuery("SELECT c FROM Archivo c "
                    + "WHERE c.idArchivo = :id ");
                
            
            query.setParameter("id", idArchivo);
            listadoArch = query.getResultList();
            
            return listadoArch;
    }
}
