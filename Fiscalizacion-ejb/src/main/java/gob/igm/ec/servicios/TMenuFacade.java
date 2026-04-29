/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.servicios;

import gob.igm.ec.dao.CrudDAO;
import gob.igm.ec.modelo.TMenu;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author VERA_MAYRA
 */
@Stateless
@LocalBean
public class TMenuFacade extends CrudDAO {

    /**
     * Obtiene el listado de los menus
     *
     * @param idMenu
     * @return
     */
    public List<TMenu> obtenerSubMenuRol(final Long idMenu) {
        List<TMenu> lista = new ArrayList();
        try {
            Query query = super.em.createQuery("SELECT t FROM TMenu t WHERE t.idMenu =?1"
            );
            query.setParameter(1, idMenu);
            lista.addAll(query.getResultList());
        } catch (Exception e) {

        }
        return lista;
    }

    /**
     * Obtiene los submenus
     *
     * @param idMenu
     * @return
     */
    public List<TMenu> getTipo(Long idMenu) {
        List<TMenu> lista = new ArrayList();
        lista = super.em.createNamedQuery("TMenu.findByIdMenuPadre", TMenu.class).setParameter("idMenuPadre", idMenu).getResultList();
        return lista;
    }

    /**
     * Obtiene los submenus por perfil de usuario
     *
     * @param idRol
     * @param idMenu
     * @return
     */
    public List<TMenu> obtenerSubMenuRol(final Long idRol, final Long idMenu) {
        List<TMenu> lista = new ArrayList();
        try {
            Query query = super.em.createQuery("select o from TMenu as o "
                    + "where o.idPagina.idPagina in ( "
                    + "select a.tPagina.idPagina from TPaginaperfil as a "
                    + "where a.tPerfil.idperfil = ?1 ) and o.idMenuPadre.idMenu= ?2 "
                    + "order by o.orden");
            query.setParameter(1, idRol);
            query.setParameter(2, idMenu);
            lista.addAll(query.getResultList());
        } catch (Exception e) {
        }
        return lista;
    }
}
