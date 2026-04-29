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
import java.sql.SQLException;
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
 * @author Alexander Jimenez
 */
//@Named(value = "ReportePermisoControlador")
@ManagedBean
@SessionScoped
@Named
//@ViewScoped
public class ReportePermisoPersonal extends FacesUtil implements Serializable {
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private boolean renderBarra;
    private String uno;
    MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
    String nombre;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;
    private String codigo;
    private String codigoTimbre;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat formatoComparacion = new SimpleDateFormat("dd/MM/yyyy");
    private Date date1; 
    
    private List<VDatoEmp> listaEmpleado;

    @ManagedProperty("#{dataManagerUsuario}")
    private DataManagerUsuario usuarioManager;
    
    @EJB
    private DatosEmpleadoServicio datosEmpleadoEJB;    
    
    public ReportePermisoPersonal(){
        //usuarioManager = new DataManagerUsuario();
    
    }
    
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
            this.setRenderBarra(true);
            date1 = formatoComparacion.parse("01/01/2023");
//            System.out.println("codigo :" + this.codigo);
//            System.out.println("fecha desde :" + this.fechaDesde);
//            System.out.println("fecha hasta :" + this.fechaHasta);
            if (codigoTimbre == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde == null || fechaHasta == null ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } else if (fechaDesde.before(date1) || fechaHasta.before(date1) ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FECHAS DEBEN ESTAR EN AÑO 2023"));
            } 
            else  {
            Map<String, Object> map = new HashMap<>();
            
            Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","PERMISOS","PERMIGM2012");
            //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");
            map.put("pathImagen",JasperReportUtil.PATH_IMAGES);
            map.put("pathImagen1",JasperReportUtil.PATH_IMAGES1);
            map.put("pathImagen2",JasperReportUtil.PATH_IMAGES2);
            map.put("CODIGO", this.codigoTimbre);
            String fecha=formatoFecha.format(fechaDesde);
            String fecha2=formatoFecha.format(fechaHasta);
            map.put("FechaDesde",fecha);
            map.put("FechaHasta",fecha2);
            
            JasperReportUtil jasper = new JasperReportUtil();
            JRExporter exporter = null;
            outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map,JasperReportUtil.PATH_REPORTE_PERMISOS_IGM);
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
            conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }   
    
    public String getNameFilePdf() {
        return "ReportePermisoPersonal";
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