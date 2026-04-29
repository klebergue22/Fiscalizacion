/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.TPerfil;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TPerfilServicio extends CrudDAO{
    
    public  List<TPerfil> getAll()
    {
        return super.em.createNamedQuery("TPerfil.findAll",TPerfil.class).getResultList();
    } 
    
}
