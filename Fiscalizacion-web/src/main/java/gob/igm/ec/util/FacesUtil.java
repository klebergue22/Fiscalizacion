/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.util;

import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import gob.igm.ec.administracion.MenuOP;
//import gob.igm.ec.administracion.OperadorOP;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author VERA_MAYRA
 */
public class FacesUtil {

    /** La variable recursoGeneral. */
    private ResourceBundle recursoGeneral;
    /* Inicializa las variables de clase. */
    /**
     * Inicializa las variables de clase y
     * crea una nueva instancia de faces util.
     */
    public FacesUtil() {
        this.recursoGeneral = ResourceBundle.getBundle("gob.igm.ec.recursos.General");
    }

    /**
     * Agrega un mensaje de error.
     * @param msg Mensaje de error
     */
    public void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    /**
     * Agrega un mensaje de satisfacción.
     * @param msg Mensaje
     */
    public void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    /**
     * 
     * Agrega un mensaje de advertencia 
     * @param msg 
     */
     public void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    /**
     * Retorna el FacesContex de la aplicación.
     * @return FacesContext
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    /**
     * Añade sesiones de usuario
     * @param key
     * @param value 
     */
     public void add(String key, Object value) {
        getFacesContext().getExternalContext().getSessionMap().put(key, value);
    }
    
     /**
      * Obtiene la sesión del usuario
      * @param key 
      */
    public void get(String key) {
        getFacesContext().getExternalContext().getSessionMap().get(key);
    }

    /**
     * Retorna el valor ELContext.
     *
     * @return El eL context
     */
    public ELContext getELContext() {
        return this.getFacesContext().getELContext();
    }

    /**
     * Retorna el valor Bean.
     *
     * @param <T> the generic type
     * @param nombreBean la nombre bean
     * @return El bean
     */
    public <T> T getBean(String nombreBean) {
        return (T) this.getELContext().getELResolver().getValue(this.getELContext(), null, nombreBean);
    }

    /**
     * Retorna el valor Request.
     *
     * @return El request
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) this.getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Retorna el valor RecursoGeneral.
     *
     * @return El recurso general
     */
    public ResourceBundle getRecursoGeneral() {
        return recursoGeneral;
    }

    /**
     * Ingresa el valor de RecursoGeneral.
     *
     * @param recursoGeneral la recurso general
     */
    public void setRecursoGeneral(ResourceBundle recursoGeneral) {
        this.recursoGeneral = recursoGeneral;
    }
    
     /**
     * Retorna el valor MenuOP.
     *
     * @return El menu op
     */
    public MenuOP getMenuOP() {
        return (MenuOP) this.getBean(MenuOP.NOMBRE_BEAN);
    }

//     public OperadorOP getOperadorOP() {
//        return (OperadorOP) this.getBean(MenuOP.NOMBRE_BEAN);
//    }
     
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.US_ASCII));
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
