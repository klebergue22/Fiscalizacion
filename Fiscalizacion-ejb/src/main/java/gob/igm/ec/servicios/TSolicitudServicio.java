/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.Solicitud;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alexander Jimenez
 */
@Stateless
@LocalBean
public class TSolicitudServicio extends CrudDAO{
    
    public List<Solicitud> getAll() {
        return super.em.createNamedQuery("Solicitud.findAll",Solicitud.class).getResultList();
    }   
    
}
