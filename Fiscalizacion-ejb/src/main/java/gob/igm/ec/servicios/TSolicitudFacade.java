/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.TUsuarios;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alexander Jimenez
 */
@Stateless
@LocalBean
public class TSolicitudFacade extends CrudDAO {
    
public  List<TUsuarios> getAll()
    {
        return super.em.createNamedQuery("TUsuarios.findAll",TUsuarios.class).getResultList();
    }    
    
}
