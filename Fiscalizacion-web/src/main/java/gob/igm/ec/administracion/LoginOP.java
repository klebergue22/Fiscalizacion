/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.administracion;


import gob.igm.ec.modelo.TUsuarios;
import gob.igm.ec.servicios.TUsuarioFacade;
import gob.igm.ec.util.DataManagerUsuario;
import gob.igm.ec.util.EncriptUtil;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.SessionUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@Named(value = "loginOP")
@ViewScoped
//@RequestScoped
public class LoginOP extends FacesUtil {
    
    private EncriptUtil encriptUtil;
    private String clave;
    private String aliasBase;
    private String mensaje;
    private String prueba;
    @EJB
    private TUsuarioFacade tUsuarioFacade;
    private Boolean renderMensaje;
    //@Inject // inyectamos la dependencia
    //private SessionUtils session;
    @ManagedProperty("#{dataManagerUsuario}")
    private DataManagerUsuario usuarioManager;

    /**
     * Creates a new instance of LoginOP
     */
    public LoginOP() {
        this.encriptUtil=new EncriptUtil(); 
         this.setRenderMensaje(false);
         //usuarioManager = new DataManagerUsuario();
    }
    
    public String ingresar() {
        String regla = "faces/inicial.xhtml";
        //String regla="faces/welcomePrimefaces";
        //String regla = "/inicial.xhtml";
        try {
            String cifrado = this.encriptUtil.getMD5(this.clave);
            TUsuarios usuario = this.tUsuarioFacade.buscarUsuarioClave(this.aliasBase, cifrado);           
            
            if (usuario != null) {
                this.usuarioManager.setUsuario(usuario.getUsuario());
                super.add("usuarioLogueado", usuario.getUsuario());
                //usuarioManager.setUsuario(usuario);
                MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
                menuOP.setUsuario(usuario);
                
                //menuOP.setPeriodo(this.calculaPeriodo());
                //obtenemos el centro de distribución
                //menuOP.setIdCdResponsable(this.centroServicio.buscaCentroResponsable(usuario.getNoPersona()));
                menuOP.cargarMenus();
                this.setMensaje("");
                this.setRenderMensaje(false);
               
            }
        } catch (Exception ex) {
            regla = "#";
            //Logger.getLogger(BodegaOP.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null);
            this.setMensaje(super.getRecursoGeneral().getString("msgErrorLogin"));
            this.setRenderMensaje(true);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, super.getRecursoGeneral().getString("msgErrorLogin"), ""));
        }
        return regla;
    }
    
     /**
     * Cierra la sesión del usuario.
     * @param evento Evento generado por la página
     */
    //public void cerrarSession(ActionEvent event) {
   public void cerrarSession() {
        super.getRequest().getSession(true).invalidate();
    }
    
   public String cerrarSession1() {
    //public void cerrarSession() {
         String regla = "faces/index.xhtml";
        super.getRequest().getSession(true).invalidate();
         this.setRenderMensaje(false);
        return regla;
    }
    


    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the aliasBase
     */
    public String getAliasBase() {
        return aliasBase;
    }

    /**
     * @param aliasBase the aliasBase to set
     */
    public void setAliasBase(String aliasBase) {
        this.aliasBase = aliasBase;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the renderMensaje
     */
    public Boolean getRenderMensaje() {
        return renderMensaje;
    }

    /**
     * @param renderMensaje the renderMensaje to set
     */
    public void setRenderMensaje(Boolean renderMensaje) {
        this.renderMensaje = renderMensaje;
    }

    public DataManagerUsuario getUsuarioManager() {
        return usuarioManager;
    }

    public void setUsuarioManager(DataManagerUsuario usuarioManager) {
        this.usuarioManager = usuarioManager;
    }
    
    
}
