package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.TRhTipoAsistencia;
import gob.igm.rh.servicio.TipoAsistenciaServicio;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import org.primefaces.model.StreamedContent;

@SessionScoped
@Named
public class ReporteTipoPermisoControlador extends FacesUtil implements Serializable {
    
private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream excelOutputStream;
    private String number;
    private boolean renderBarra;
    private String uno;
    MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
    String nombre;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;
    private String codigo;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private List<TRhTipoAsistencia> listaTipoAsistencia;
    private String idTipoPermiso;
    
    @EJB
    private TipoAsistenciaServicio tipoAsistenciaEJB;    
    
    @PostConstruct
    public void init(){
        listaTipoAsistencia = tipoAsistenciaEJB.listadoTipoPermiso();
        
         for (TRhTipoAsistencia tmp : listaTipoAsistencia) {
                System.out.println("EMPLEADO >>>>" + tmp.getDescrip());
                System.out.println("NRO.  >>>>" + tmp.getNoAsist());
                System.out.println("FECHA.  >>>>" + tmp.getNoAsistAux());
            }
        
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);
       // this.setPath(JasperReportUtil.PATH_REPORTE_ACCIONES);
 
    }
    
    public void metodoPrueba(){
    }
    
    public void generateReport() {
        try {
            this.setRenderBarra(true);
            limpiarReporteGenerado();
     
            String codigoReporte = normalizarCodigoTimbrado(this.codigo);
            String tipoPermiso = normalizarTexto(this.idTipoPermiso);
            System.out.println("TIPO PERMISO :" + tipoPermiso);
         
            if (codigoReporte == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (tipoPermiso == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR TIPO DE PERMISO"));
            } else if (fechaDesde == null || fechaHasta == null ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } else if (fechaDesde.after(fechaHasta)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA DESDE NO PUEDE SER MAYOR QUE LA FECHA HASTA"));
            } else  {
            Map<String, Object> map = new HashMap<>();
            
            Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1","PERMISOS","PERMIGM2012");
            //Connection  conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.35.88:1521:GEO","PERMISOS","PERMIGM2012");
            String fecha=formatoFecha.format(fechaDesde);
            String fecha2=formatoFecha.format(fechaHasta);
            if (!existenDatosReporte(conexion, codigoReporte, tipoPermiso, fecha, fecha2)) {
                conexion.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SIN DATOS", "NO EXISTEN PERMISOS PARA LOS FILTROS INGRESADOS"));
                return;
            }

            map.put("pathImagen",JasperReportUtil.PATH_IMAGES);
            
            map.put("CODIGO", codigoReporte);
            map.put("TipoPermiso", tipoPermiso);
            map.put("FechaDesde",fecha);
            map.put("FechaHasta",fecha2);
            
            JasperReportUtil jasper = new JasperReportUtil();
            JRExporter exporter = null;
            JasperReportUtil.ReportOutput reportOutput = JasperReportUtil.getOutputStreamsFromReport(conexion, map,JasperReportUtil.PATH_REPORTE_TIPO_PERMISO);
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

    private String normalizarCodigoTimbrado(String codigoIngresado) {
        if (codigoIngresado == null) {
            return null;
        }
        String codigoNormalizado = codigoIngresado.replaceAll("\\D", "");
        return codigoNormalizado.isEmpty() ? null : codigoNormalizado;
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return null;
        }
        String texto = valor.trim();
        return texto.isEmpty() ? null : texto;
    }

    private boolean existenDatosReporte(Connection conexion, String codigoReporte, String tipoPermiso, String fechaDesdeReporte, String fechaHastaReporte) throws SQLException {
        String sql = "SELECT COUNT(1) "
                + "FROM V_PERMISOS_EMPLEADO "
                + "WHERE TO_NUMBER(CODIGO) = TO_NUMBER(?) "
                + "AND UPPER(TRIM(DESCRIP)) = UPPER(TRIM(?)) "
                + "AND TRUNC(F_INICO) BETWEEN TO_DATE(?, 'DD/MM/YYYY') AND TO_DATE(?, 'DD/MM/YYYY')";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoReporte);
            ps.setString(2, tipoPermiso);
            ps.setString(3, fechaDesdeReporte);
            ps.setString(4, fechaHastaReporte);
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
        return "ReporteTipoPermiso";
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

    public List<TRhTipoAsistencia> getListaTipoAsistencia() {
        return listaTipoAsistencia;
    }

    public void setListaTipoAsistencia(List<TRhTipoAsistencia> listaTipoAsistencia) {
        this.listaTipoAsistencia = listaTipoAsistencia;
    }

    public String getIdTipoPermiso() {
        return idTipoPermiso;
    }

    public void setIdTipoPermiso(String idTipoPermiso) {
        this.idTipoPermiso = idTipoPermiso;
    }
    
    
    
    
}

    
    
