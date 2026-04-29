package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VGestionesVigentes;
import gob.igm.rh.servicio.DatosEmpleadoServicio;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.inject.Named;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
@Named
public class ReporteAtrasoControlador extends FacesUtil implements Serializable {
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    //private String number;
    private boolean renderBarra;
    private String uno;
    MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
    
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;
    
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
       // this.setPath(JasperReportUtil.PATH_REPORTE_ACCIONES);
 
    }
    
    public void generarReporteAtrasos() {
        try {
            this.setRenderBarra(true);
     
            if (fechaDesde == null || fechaHasta == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else {
                Map<String, Object> map = new HashMap<>();

                Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "PERMISOS", "PERMIGM2012");
                //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");
                map.put("pathImagen", JasperReportUtil.PATH_IMAGES);

                String fecha = formatoFecha.format(fechaDesde);
                String fecha2 = formatoFecha.format(fechaHasta);
                map.put("FechaDesde", fecha);
                map.put("FechaHasta", fecha2);

                JasperReportUtil jasper = new JasperReportUtil();
                JRExporter exporter = null;
                outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_TIPO_PERMISO);
                media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
                conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }

    
    public String getNameFilePdf() {
        return "ReporteDeAtrasos";
    }

    public void downloadFile() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + getNameFilePdf());
            
            OutputStream output = response.getOutputStream();
            output.write(outputStream.toByteArray());
            output.close();
            
            facesContext.responseComplete();
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
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
    
    
    
}

    