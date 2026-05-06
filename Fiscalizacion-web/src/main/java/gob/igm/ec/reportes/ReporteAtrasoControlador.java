package gob.igm.ec.reportes;

import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VGestionesVigentes;
import gob.igm.rh.servicio.DatosEmpleadoServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Named
public class ReporteAtrasoControlador extends FacesUtil implements Serializable {
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    //private String number;
    private boolean renderBarra;
    private String uno;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;
    private Short noGestion;
    
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    private List<VGestionesVigentes> listadoGestiones;
    
    @EJB
    private DatosEmpleadoServicio servicioListadoGestiones;  
    
    @PostConstruct
    public void init(){
        
        listadoGestiones = servicioListadoGestiones.obtenerTodasGestiones();
//        for (VGestionesVigentes tmp : listadoGestiones) {
//                System.out.println("ID >>>>" + tmp.getNoCd());
//                System.out.println("GESTION.  >>>>" + tmp.getDescrip());
//                
//            }
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);
        this.setPath(JasperReportUtil.PATH_REPORTE_ATRASOS);
 
    }
    
    public void generarReporteAtrasos() {
        try {
            this.setRenderBarra(true);
     
            if (fechaDesde == null || fechaHasta == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
                return;
            }

            if (fechaHasta.before(fechaDesde)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA HASTA NO PUEDE SER MENOR A LA FECHA DESDE"));
                return;
            }

            if (noGestion == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR LA GESTION"));
                return;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
            map.put("FechaDesde", formatoFecha.format(fechaDesde));
            map.put("FechaHasta", formatoFecha.format(fechaHasta));
            map.put("NoGestion", noGestion);

            try (Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "PERMISOS", "PERMIGM2012")) {
                outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_ATRASOS);
            }

            if (outputStream == null || outputStream.size() == 0) {
                media = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL PDF. REVISE EL LOG DEL SERVIDOR."));
                return;
            }

            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
            
        } catch (Exception e) {
            media = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage()));
        }
    }

    
    public String getNameFilePdf() {
        return "ReporteDeAtrasos";
    }

    public StreamedContent getArchivoDescarga() {
        try {
            if (outputStream == null || outputStream.size() == 0) {
                return null;
            }

            return new org.primefaces.model.DefaultStreamedContent(
                    new ByteArrayInputStream(outputStream.toByteArray()),
                    "application/pdf",
                    getNameFilePdf() + ".pdf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage()));
            return null;
        }
    }
    
 public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }
 
    /**
     * @return the renderBarra
     */
    public boolean isRenderBarra() {
        return renderBarra;
    }

    /**
     * @param renderBarra the renderBarra to set
     */
    public void setRenderBarra(boolean renderBarra) {
        this.renderBarra = renderBarra;
    }

    /**
     * @return the uno
     */
    public String getUno() {
        return uno;
    }

    /**
     * @param uno the uno to set
     */
    public void setUno(String uno) {
        this.uno = uno;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<VGestionesVigentes> getListadoGestiones() {
        return listadoGestiones;
    }

    public void setListadoGestiones(List<VGestionesVigentes> listadoGestiones) {
        this.listadoGestiones = listadoGestiones;
    }

    public Short getNoGestion() {
        return noGestion;
    }

    public void setNoGestion(Short noGestion) {
        this.noGestion = noGestion;
    }
    
    
    
}

    
