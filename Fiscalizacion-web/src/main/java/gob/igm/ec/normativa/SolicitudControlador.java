/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.normativa;

import gob.igm.ec.modelo.Archivo;
import gob.igm.ec.modelo.Solicitud;
import gob.igm.ec.servicios.ArchivoServicio;
import gob.igm.ec.servicios.TSolicitudServicio;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alexander Jimenez
 */

@Named(value = "solicitudControlador")
//@SessionScoped
@ViewScoped
public class SolicitudControlador implements Serializable{
    
    private Solicitud solicitud;
    private Archivo archivo;
    private BigDecimal idArc;
    private List<Solicitud> listaSolicitud;
    private List<Archivo> listaArchivos;
    private List<Archivo> listaArchivosRecuperados;
    private UploadedFile file;
    @EJB
    private TSolicitudServicio solicitudFacade;
    @EJB
    private ArchivoServicio archivoFacade;
            
    
    @PostConstruct
    public void init(){
        
        solicitud = new Solicitud();    
        archivo = new Archivo();
        listaSolicitud = solicitudFacade.getAll();
        listaArchivos = archivoFacade.getAllArchivos();
        //lstMeses = tipoPagosFacade.getAllMeses();        
    }
    
    public void ingresarSolicitud(){
    try{
        
        InputStream input = file.getInputstream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        for (int length = 0; (length = input.read(buffer)) > 0;) {
            output.write(buffer, 0, length);
        }
        archivo.setArchivopdf(output.toByteArray());
        
        solicitud.setIdSolicitud(BigDecimal.ONE);
        //solicitudFacade.insertar(solicitud);
        archivoFacade.insertar(this.archivo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Archivo: " + file.getFileName() + "  GUARDADO"));
        listaSolicitud = solicitudFacade.getAll();
        solicitud = new Solicitud();        
        archivo = new Archivo();
    
        //FacesMessage.SEVERITY_ERROR, "Aviso","ERROR >>>" + e.getMessage()
    }catch (Exception ex) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR:", "No se guardo el Archivo"));
        }   
    }
    
    public void recuperarArchivo() {
        try {

            listaArchivosRecuperados = archivoFacade.listadoArchivos(idArc);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Recuperación Exitosa"));

            //FacesMessage.SEVERITY_ERROR, "Aviso","ERROR >>>" + e.getMessage()
        } catch (Exception ex) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR:", "No se pudo Recuperar"));
        }
    }
    
    
    public void upload(){
        try{
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            
            for(int length = 0; (length=input.read(buffer)) > 0;){
                output.write(buffer, 0, length);
            }
           archivo.setArchivopdf(output.toByteArray());
            
            
        }catch (Exception ex){
        
        }
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<Solicitud> getListaSolicitud() {
        return listaSolicitud;
    }

    public void setListaSolicitud(List<Solicitud> listaSolicitud) {
        this.listaSolicitud = listaSolicitud;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public List<Archivo> getListaArchivos() {
        return listaArchivos;
    }

    public void setListaArchivos(List<Archivo> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }
    
    
    
    
}
