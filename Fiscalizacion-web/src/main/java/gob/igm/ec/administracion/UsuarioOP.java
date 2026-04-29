package gob.igm.ec.administracion;

//import gob.igm.ec.general.BusquedaParametros;
import gob.igm.ec.modelo.TPerfil;
import gob.igm.ec.modelo.TUsuarios;
import gob.igm.ec.servicios.TPerfilServicio;
import gob.igm.ec.servicios.TUsuarioFacade;
import static gob.igm.ec.util.FacesUtil.getMD5;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
//import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;


@ManagedBean(name = "usuarioOP")
@SessionScoped
//public class UsuarioOP extends BusquedaParametros implements Serializable {
  public class UsuarioOP implements Serializable {
    @EJB
    private TUsuarioFacade tUsuarioFacade;
    
    @EJB
    private TPerfilServicio tPerfilServicio;

    private List<TUsuarios> usuarios;
    private List<TPerfil> perfiles;
    private TUsuarios usuario;
    private TUsuarios usuarioSeleccionado;
    private TUsuarios usuarioRecuperado;
    //private List<TDignidad> dignidades;

    private int seleccionadoItemDignidad;
    private BigInteger seleccionadoItemBodega;
    private BigDecimal seleccionadoItemPerfil;
    private TPerfil perfil;
    //private TDignidad dignidad;
   // private TDignidadXUsuario dignidadUsuario;
    private String passwordCambiado;

    /*
    public UsuarioOP() {
        usuario = new TUsuarios();
        usuarioSeleccionado=new TUsuarios();
        dignidades = new ArrayList<TDignidad>();
        perfil = new TPerfil();
        dignidad = new TDignidad();
        dignidadUsuario = new TDignidadXUsuario();
        usuarioRecuperado = new TUsuarios();
        perfiles = tPerfilServicio.getAll();
    }
    */
    
    @PostConstruct
    public void init(){
        usuario = new TUsuarios();
        usuarioSeleccionado=new TUsuarios();
        //dignidades = new ArrayList<TDignidad>();
        perfil = new TPerfil();
        //dignidad = new TDignidad();
        //dignidadUsuario = new TDignidadXUsuario();
        usuarioRecuperado = new TUsuarios();
        perfiles = tPerfilServicio.getAll();
    }
    
    public List<TUsuarios> getUsuarios() {
        return tUsuarioFacade.getAll();
    }

    /**
     * Ingresa el registro del usuario
     *
     * @param evento
     */
    public void ingresarUsuario(ActionEvent evento) {
        try {
            if (usuario.getUsuario().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN USUARIO"));
            } else if (usuario.getPassword().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL PASSWORD NO DEBE SER VACIO"));
            } else {
               
//                usuario.setBodega(this.seleccionadoItemBodega);
//                dignidad.setCodDignidad(this.seleccionadoItemDignidad);
//                perfil.setIdperfil(this.seleccionadoItemPerfil);
//                usuario.setIdPerfil(perfil);
//                usuario.setIdDignidad(dignidad);
//            
                usuario.setBodega(new BigInteger("1"));
              //  perfil.setIdperfil(new BigDecimal("1"));
                usuario.setIdPerfil(perfil);
              //  dignidad.setCodDignidad(2);
                //usuario.setIdDignidad(dignidad);
                usuario.setEstado(new BigInteger("1"));
                usuario.setPassword(getMD5(usuario.getPassword()));
                
                // SE COMENTA SOLO POR CUESTIONES DE PRUEBAS
                tUsuarioFacade.insertar(getUsuario());
                
                BigDecimal idusuario;
                idusuario = tUsuarioFacade.maximoID();
                //dignidadUsuario.setIdDignidad(dignidad);
                usuarioRecuperado.setIdUsuario(idusuario);
                //dignidadUsuario.setIdUsuario(usuarioRecuperado);
                //tDignidadUsuarioFacade.insertar(dignidadUsuario);
                ////FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, super.getRecursoGeneral().getString("mensajeGuardar"), ""));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "USUARIO CREADO EXITOSAMENTE"));
                setUsuario(new TUsuarios());
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioOP.class.getName()).log(Level.SEVERE, null, ex);
            ////FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, super.getRecursoGeneral().getString("mensajeGuardarError"), ""));
        }
    }
    
    public void resetearPassword(ActionEvent evento){
        
        try {
            if (this.usuarioSeleccionado != null) {
                //usuarioSeleccionado.setPassword(getMD5(usuarioSeleccionado.getPassword()));
                usuarioSeleccionado.setPassword(getMD5(this.passwordCambiado));
                tUsuarioFacade.actualizar(this.usuarioSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "PASSWORD CAMBIADO EXITOSAMENTE"));
                setUsuarioSeleccionado(new TUsuarios());

            } else {
                // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, super.getRecursoGeneral().getString("mensajeSeleccionarRegistro"), ""));
            }
        }catch (Exception ex){
        
        }
    }
    
    
//    @PostConstruct
//	public void init() {
//		
//               usuarioSeleccionado=new TUsuarios();
//                
//	}
   /* public void onRowSelect(SelectEvent selectEvent)
{
    this.usuarioSeleccionado=(TUsuarios) selectEvent.getObject();
}
    public void rowUnselect(UnselectEvent event) {
       this.usuarioSeleccionado=(TUsuarios) event.getObject();
    }*/

    /**
     * @return the seleccionadoItemPerfil
     */
    public BigDecimal getSeleccionadoItemPerfil() {
        return seleccionadoItemPerfil;
    }

    /**
     * @param seleccionadoItemPerfil the seleccionadoItemPerfil to set
     */
    public void setSeleccionadoItemPerfil(BigDecimal seleccionadoItemPerfil) {
        this.seleccionadoItemPerfil = seleccionadoItemPerfil;
    }

    /**
     * @return the seleccionadoItemBodega
     */
    public BigInteger getSeleccionadoItemBodega() {
        return seleccionadoItemBodega;
    }

    /**
     * @param seleccionadoItemBodega the seleccionadoItemBodega to set
     */
    public void setSeleccionadoItemBodega(BigInteger seleccionadoItemBodega) {
        this.seleccionadoItemBodega = seleccionadoItemBodega;
    }

    /**
     * @return the seleccionadoItemDignidad
     */
    public int getSeleccionadoItemDignidad() {
        return seleccionadoItemDignidad;
    }

    /**
     * @param seleccionadoItemDignidad the seleccionadoItemDignidad to set
     */
    public void setSeleccionadoItemDignidad(int seleccionadoItemDignidad) {
        this.seleccionadoItemDignidad = seleccionadoItemDignidad;
    }

    /*
    public List<TDignidad> getDignidades() {
        return dignidades;
    }

    
     * @param dignidades the dignidades to set
     
//    public void setDignidades(List<TDignidad> dignidades) {
//        this.dignidades = dignidades;
//    }
    public void setDignidades(List<TDignidad> dignidades) {
        this.dignidades = dignidades;
    }
    */

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<TUsuarios> usuarios) {
        this.usuarios = usuarios;
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
     * @return the dignidadUsuario
     */
    /*
    public TDignidadXUsuario getDignidadUsuario() {
        return dignidadUsuario;
    }

    
     * @param dignidadUsuario the dignidadUsuario to set
     
    public void setDignidadUsuario(TDignidadXUsuario dignidadUsuario) {
        this.dignidadUsuario = dignidadUsuario;
    }
*/
    /**
     * @return the usuarioRecuperado
     */
    public TUsuarios getUsuarioRecuperado() {
        return usuarioRecuperado;
    }

    /**
     * @param usuarioRecuperado the usuarioRecuperado to set
     */
    public void setUsuarioRecuperado(TUsuarios usuarioRecuperado) {
        this.usuarioRecuperado = usuarioRecuperado;
    }

    /**
     * @return the dignidad
     */
    /*
    public TDignidad getDignidad() {
        return dignidad;
    }

    
     * @param dignidad the dignidad to set
     
    public void setDignidad(TDignidad dignidad) {
        this.dignidad = dignidad;
    }
    */

    /**
     * @return the perfil
     */
    public TPerfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(TPerfil perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the usuarioSeleccionado
     */
    public TUsuarios getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    /**
     * @param usuarioSeleccionado the usuarioSeleccionado to set
     */
    public void setUsuarioSeleccionado(TUsuarios usuarioSeleccionado) {
        
             this.usuarioSeleccionado = usuarioSeleccionado;
    }

    /**
     * @return the passwordCambiado
     */
    public String getPasswordCambiado() {
        return passwordCambiado;
    }

    /**
     * @param passwordCambiado the passwordCambiado to set
     */
    public void setPasswordCambiado(String passwordCambiado) {
        this.passwordCambiado = passwordCambiado;
    }

    public List<TPerfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<TPerfil> perfiles) {
        this.perfiles = perfiles;
    }
    
    

}
