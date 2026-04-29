package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VAccionesPer;
import gob.igm.rh.modelo.VDatoEmp;
import gob.igm.rh.servicio.DatosEmpleadoServicio;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;


import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alexander Jimenez
 */
@SessionScoped
@Named
public class ReporteAccionPerControlador extends FacesUtil implements Serializable{
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private String number;
    private boolean renderBarra;
    private String uno;
    MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
    
    private String path;
    
    private String codigo;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    private Integer idEmpleado;
    private List<VDatoEmp> listaEmpleados;
    private List<VAccionesPer> listaAcciones;
    
    @EJB
    private DatosEmpleadoServicio datosEmpleadoEJB;    

    
    @PostConstruct
    public void init(){
        listaEmpleados = datosEmpleadoEJB.getAllEmpleados();
        
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);

        this.setPath(JasperReportUtil.PATH_REPORTE_ACCIONES);
 
    }
    
    public void handleFacturaChange() {
        try {
            listaAcciones = datosEmpleadoEJB.listadoAcciones(idEmpleado);

            for (VAccionesPer tmp : listaAcciones) {
                System.out.println("EMPLEADO >>>>" + tmp.getNombreC());
                System.out.println("NRO.  >>>>" + tmp.getNoAccion());
                System.out.println("FECHA.  >>>>" + tmp.getFechaResolucion());
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
    }
    
    public void generateReport() {
     
        try {
            this.setRenderBarra(true);
     
            System.out.println("codigo :" + this.codigo);
            if (codigo == null) {                         
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UNA ACCION DE PERSONAL"));
            
            } 
            else  {
            Map<String, Object> map = new HashMap<>();
            
            Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","RH","oraclerrhh2010");
            //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");            
            map.put("fondo3",JasperReportUtil.PATH_IMAGES1);
            map.put("fondo4",JasperReportUtil.PATH_IMAGES2);
            map.put("NUM_DESDE", this.codigo);
            map.put("NUM_HASTA", this.codigo);
            
            JasperReportUtil jasper = new JasperReportUtil();
            JRExporter exporter = null;
            outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map,JasperReportUtil.PATH_REPORTE_ACCIONES_LIBRO);
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
            conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }
    
    public void generateReverso() {     
        try {
            this.setRenderBarra(true);
     
            System.out.println("codigo :" + this.codigo);
            if (codigo == null) {                         
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UNA ACCION DE PERSONAL"));
            
            } 
            else  {
            Map<String, Object> map = new HashMap<>();
            
            Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","RH","oraclerrhh2010");
            //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");
            
            map.put("ACCION", this.codigo);
            
            JasperReportUtil jasper = new JasperReportUtil();
            JRExporter exporter = null;
            outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map,JasperReportUtil.PATH_REPORTE_ACCIONES_REVERSO);
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
            conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }
        
    public String getNameFilePdf() {
        return "reporteAccionesPersonal";
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<VDatoEmp> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<VDatoEmp> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<VAccionesPer> getListaAcciones() {
        return listaAcciones;
    }

    public void setListaAcciones(List<VAccionesPer> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }
}
