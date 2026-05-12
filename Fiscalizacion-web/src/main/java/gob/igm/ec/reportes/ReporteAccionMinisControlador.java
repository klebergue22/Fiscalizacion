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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class ReporteAccionMinisControlador extends FacesUtil implements Serializable{
    
    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream excelOutputStream;
    private String number;
    private boolean renderBarra;
    private String uno;
    MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
    
    private String path;
    
    private String codigo;
    private String hasta;
    int valor1; int valor2;
    
    //DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    @PostConstruct
    public void init(){        
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);

        this.setPath(JasperReportUtil.PATH_REPORTE_ACCIONES_MINISTERIO); // ojo MC: quite el comentario y puse el path de ministerio
    }  
    
    
    public void generarReport() {
     
        try {
            this.setRenderBarra(true);
            limpiarReporteGenerado();

            String accionDesde = normalizarAccion(this.codigo);
            String accionHasta = normalizarAccion(this.hasta);
            System.out.println("codigo :" + accionDesde);
            if (accionDesde == null || accionHasta == null) {                         
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UNA ACCION DE PERSONAL"));            
            }else if (Integer.parseInt(accionDesde) > Integer.parseInt(accionHasta)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "ACCION HASTA DEBE SER MAYOR"));
            }else  {
            Map<String, Object> map = new HashMap<>();
            
            Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","RH","oraclerrhh2010");
            if (!existenAcciones(conexion, accionDesde, accionHasta)) {
                conexion.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SIN DATOS", "NO EXISTEN ACCIONES EN EL RANGO INGRESADO"));
                return;
            }
            
            //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");
            map.put("fondo3",JasperReportUtil.PATH_IMAGES3);
            map.put("fondo4",JasperReportUtil.PATH_IMAGES4);
            map.put("NUM_DESDE", accionDesde);
            map.put("NUM_HASTA", accionHasta);
            
            JasperReportUtil jasper = new JasperReportUtil();
            JRExporter exporter = null;
            JasperReportUtil.ReportOutput reportOutput = JasperReportUtil.getOutputStreamsFromReport(conexion, map,JasperReportUtil.PATH_REPORTE_ACCIONES_MINISTERIO);
                outputStream = reportOutput.getPdfOutputStream();
                excelOutputStream = reportOutput.getExcelOutputStream();
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
            conexion.close();
            }
            
        } catch (Exception e) {
            limpiarReporteGenerado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL REPORTE: " + e.getMessage()));
        }
    }

    private String normalizarAccion(String accion) {
        if (accion == null) {
            return null;
        }
        String accionNormalizada = accion.replaceAll("\\D", "");
        return accionNormalizada.isEmpty() ? null : accionNormalizada;
    }

    private boolean existenAcciones(Connection conexion, String accionDesde, String accionHasta) throws SQLException {
        String sql = "SELECT COUNT(1) "
                + "FROM RH.T_ACCION_PERSONAL2025 "
                + "WHERE NO_ACCION BETWEEN TO_NUMBER(?) AND TO_NUMBER(?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, accionDesde);
            ps.setString(2, accionHasta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void limpiarReporteGenerado() {
        media = null;
        outputStream = null;
        excelOutputStream = null;
    }
        
    public String getNameFilePdf() {
        return "reporteAccionesLibro";
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
    

    public StreamedContent getArchivoDescarga() {
        try {
            if (outputStream == null || outputStream.size() == 0) {
                return null;
            }

            return new org.primefaces.model.DefaultStreamedContent(
                    new java.io.ByteArrayInputStream(outputStream.toByteArray()),
                    "application/pdf",
                    getNameFilePdf() + ".pdf");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage()));
            return null;
        }
    }
    public StreamedContent getArchivoDescargaExcel() {
        try {
            if (excelOutputStream == null || excelOutputStream.size() == 0) {
                return null;
            }

            return new org.primefaces.model.DefaultStreamedContent(
                    new java.io.ByteArrayInputStream(excelOutputStream.toByteArray()),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    getNameFilePdf() + ".xlsx");
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

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }
    
}
