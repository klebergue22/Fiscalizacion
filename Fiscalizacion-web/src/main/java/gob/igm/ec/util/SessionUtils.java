/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.util;

import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author VERA_MAYRA
 */
public class SessionUtils implements Serializable {
    
    /**
     * Añade una sesión de usuario
     * @param key
     * @param value 
     */
    public void add(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
    
    /**
     * Obtiene la sesión del usuario
     * @param key 
     */
    public void get(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
}
