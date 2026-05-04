
package gob.igm.ec.util;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class DataManagerUsuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //private TUsuarios usuario;
    private String usuario;
    
//    public TUsuarios getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(TUsuarios usuario) {
//        this.usuario = usuario;
//    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
    public DataManagerUsuario(){
    
        this.usuario = new String();
        
    }
    
}
