package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.DataManagerUsuario;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VDatoEmp;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alexander Jiménez
 */
@ManagedBean
@Named
@SessionScoped
public class ReporteTimbradoPersonal extends FacesUtil implements Serializable {

private StreamedContent media;
private ByteArrayOutputStream outputStream;
private String number;
private boolean renderBarra;
private String uno;
MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
String nombre;
private String path;
private Date fechaDesde2;
private Date fechaHasta2;
private String codigo;
DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
DateFormat formatoComparacion = new SimpleDateFormat("dd/MM/yyyy");
private Date date1; 
private String codigoTimbre;

private List<VDatoEmp> listaEmpleado;

@EJB
private DatosEmpleadoServicio datosEmpleadoEJB;    

@ManagedProperty("#{dataManagerUsuario}")
private DataManagerUsuario usuarioManager;

//@EJB
    //private gob.igm.ec.servicios.TProduccionPapeletasFacade ejbFacade;
    /**
     * Creates a new instance of reporteGlobalP
     */
@PostConstruct
    public void init(){
       System.out.println("CODIGO DE LA PERSONA >>>>" + usuarioManager.getUsuario());
       //codigo = usuario.
       codigo = usuarioManager.getUsuario();
       listaEmpleado = datosEmpleadoEJB.obtenerCodigoTimbrado(codigo);
        
         for (VDatoEmp tmp : listaEmpleado) {
                System.out.println("CODIGO TIMBRE >>>>" + tmp.getCodigo());
                codigoTimbre = tmp.getCodigo();
                System.out.println("NOMBRE  >>>>" + tmp.getNombreC());
                System.out.println("CEDULA  >>>>" + tmp.getNoCedula());
            }
        
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);
       // this.setPath(JasperReportUtil.PATH_REPORTE_ACCIONES);
 
    }
    
   
   public void generateReport() {
        try {
            date1 = formatoComparacion.parse("01/01/2023");
            this.setRenderBarra(true);
    
            if (codigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde2 == null || fechaHasta2 == null ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } else if (fechaDesde2.before(date1) || fechaHasta2.before(date1) ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FECHAS DEBEN ESTAR EN AÑO 2023"));
            } 
            else  {
                Map<String, Object> map = new HashMap<>();
                //Connection conexion=this.ejbFacade.Conexion();
                //Class.forName("oracle.jdbc.driver.OracleDriver");
                ////Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","CNEELECCIONES","ELECCIONES2020!");
                Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "RH", "oraclerrhh2010");
                //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","ELECCIONES","ELECCIONES2019");            
//map.put("TITULO","AVANCES PRODUCCIÓN");
                //map.put("PROYECTO","SECCIONALES 2019");
                //map.put("NombreIngreso","FABRICIO GALEAS");
                //map.put("NombreIngreso",nombre);
                //map.put("JefeProyecto","");
                map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
                map.put("pathImagen1", JasperReportUtil.PATH_IMAGES1);
                map.put("pathImagen2", JasperReportUtil.PATH_IMAGES2);
                map.put("CODIGO", this.codigoTimbre);
                String fecha = formatoFecha.format(fechaDesde2);
                String fecha2 = formatoFecha.format(fechaHasta2);
                map.put("FechaDesde", fecha);
                map.put("FechaHasta", fecha2);
                //map.put("REPORT_CONNECTION", conexion);
                JasperReportUtil jasper = new JasperReportUtil();
                JRExporter exporter = null;
                outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_TIMBRADOS_IGM);
                media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
                conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }
  
    public String getNameFilePdf() {
        return "ReporteTimbradosPersonal";
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
 
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Date getFechaDesde2() {
        return fechaDesde2;
    }

    public void setFechaDesde2(Date fechaDesde2) {
        this.fechaDesde2 = fechaDesde2;
    }

    public Date getFechaHasta2() {
        return fechaHasta2;
    }

    public void setFechaHasta2(Date fechaHasta2) {
        this.fechaHasta2 = fechaHasta2;
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public DataManagerUsuario getUsuarioManager() {
        return usuarioManager;
    }

    public void setUsuarioManager(DataManagerUsuario usuarioManager) {
        this.usuarioManager = usuarioManager;
    }
    
    

}
