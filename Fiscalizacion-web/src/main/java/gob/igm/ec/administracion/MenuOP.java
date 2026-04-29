/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.administracion;

import gob.igm.ec.modelo.TUsuarios;
import gob.igm.ec.modelo.TMenu;
import gob.igm.ec.servicios.TMenuFacade;
import gob.igm.ec.util.FacesUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author VERA_MAYRA
 */
@Named(value = "menuOP")
@SessionScoped
public class MenuOP extends FacesUtil implements Serializable{

   private List<TMenu> menuPapeletas;
   private List<TMenu> menuDocumentos;
   private List<TMenu> menuAdministracion;
   private List<TMenu> menuPreimpresos;
   private List<TMenu> menuReportes;
@EJB
private TMenuFacade menuServicio;
private TUsuarios usuario;
public static String NOMBRE_BEAN = "menuOP";

    /**
     * Creates a new instance of MenuOP
     */
    public MenuOP() {
        this.menuPapeletas = new ArrayList();
        this.menuDocumentos=new ArrayList();
        this.menuAdministracion=new ArrayList();
        this.menuPreimpresos=new ArrayList();
        this.menuReportes=new ArrayList();
    }
    
     public void cargarMenus() {
        this.obtenerMenuPapeletas();
        this.obtenerMenuDocumentos();
        this.obtenerMenuAdministracion();
        this.obtenerMenuPreimpresos();
        this.obtenerMenuReportes();
        
    }
    
    public void obtenerMenuPapeletas() {
        this.getMenuPapeletas().clear();

        //Valor del menú principal es 7 puesto que no esta progrado
        try {
            //this.menuProduccion.addAll(this.menuServicio.obtenerSubMenuRol(1L));
            this.getMenuPapeletas().addAll(this.menuServicio.obtenerSubMenuRol(this.usuario.getIdPerfil().getIdperfil().longValue(),2L));
        }
        catch (Exception e) {
           
        }
    }
    
    public void obtenerMenuDocumentos() {
        this.menuDocumentos.clear();
        //Valor del menú principal es 7 puesto que no esta progrado
        try {
            this.menuDocumentos.addAll(this.menuServicio.obtenerSubMenuRol(this.usuario.getIdPerfil().getIdperfil().longValue(),3L));
        }
        catch (Exception e) {
           
        }
    }
    
    public void obtenerMenuAdministracion() {
        this.menuAdministracion.clear();
        //Valor del menú principal es 7 puesto que no esta progrado
        try {
            this.menuAdministracion.addAll(this.menuServicio.obtenerSubMenuRol(this.usuario.getIdPerfil().getIdperfil().longValue(),11L));
        }
        catch (Exception e) {
           
        }
    }
    
    public void obtenerMenuPreimpresos() {
        this.menuPreimpresos.clear();
        //Valor del menú principal es 7 puesto que no esta progrado
        try {
            this.menuPreimpresos.addAll(this.menuServicio.obtenerSubMenuRol(this.usuario.getIdPerfil().getIdperfil().longValue(),9L));
        }
        catch (Exception e) {
           
        }
    }
    
    public void obtenerMenuReportes() {
        this.menuReportes.clear();
        //Valor del menú principal es 7 puesto que no esta progrado
        try {
            this.menuReportes.addAll(this.menuServicio.obtenerSubMenuRol(this.usuario.getIdPerfil().getIdperfil().longValue(),17L));
        }
        catch (Exception e) {
           
        }
    }

  

    /**
     * @return the usuario
     */
    public TUsuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(TUsuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the menuDocumentos
     */
    public List<TMenu> getMenuDocumentos() {
        return menuDocumentos;
    }

    /**
     * @param menuDocumentos the menuDocumentos to set
     */
    public void setMenuDocumentos(List<TMenu> menuDocumentos) {
        this.menuDocumentos = menuDocumentos;
    }

    /**
     * @return the menuPapeletas
     */
    public List<TMenu> getMenuPapeletas() {
        return menuPapeletas;
    }

    /**
     * @param menuPapeletas the menuPapeletas to set
     */
    public void setMenuPapeletas(List<TMenu> menuPapeletas) {
        this.menuPapeletas = menuPapeletas;
    }

    /**
     * @return the menuAdministracion
     */
    public List<TMenu> getMenuAdministracion() {
        return menuAdministracion;
    }

    /**
     * @param menuAdministracion the menuAdministracion to set
     */
    public void setMenuAdministracion(List<TMenu> menuAdministracion) {
        this.menuAdministracion = menuAdministracion;
    }

    /**
     * @return the menuPreimpresos
     */
    public List<TMenu> getMenuPreimpresos() {
        return menuPreimpresos;
    }

    /**
     * @param menuPreimpresos the menuPreimpresos to set
     */
    public void setMenuPreimpresos(List<TMenu> menuPreimpresos) {
        this.menuPreimpresos = menuPreimpresos;
    }

    /**
     * @return the menuReportes
     */
    public List<TMenu> getMenuReportes() {
        return menuReportes;
    }

    /**
     * @param menuReportes the menuReportes to set
     */
    public void setMenuReportes(List<TMenu> menuReportes) {
        this.menuReportes = menuReportes;
    }
    
}
