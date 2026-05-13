package gob.igm.ec.financiero;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@SessionScoped
@Named
public class ReporteFinancieroControlador extends FacesUtil implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ReporteFinancieroControlador.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:oracle:thin:@192.168.1.80:1521:IGM1";
    private static final String DB_USER = "PERMISOS";
    private static final String DB_PASSWORD = "PERMIGM2012";

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
    private Date fechaCorte;
    private String codigo;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    @PostConstruct
    public void init() {
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);
    }

    public void generarReporteFinanciero() {
        try {
            this.setRenderBarra(true);
            limpiarReporteGenerado();

            String codigoReporte = normalizarCodigoTimbrado(this.codigo);

            if (codigoReporte == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde == null || fechaHasta == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } else if (fechaCorte == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA CORTE"));
            } else if (fechaDesde.after(fechaHasta)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA DESDE NO PUEDE SER MAYOR QUE LA FECHA HASTA"));
            } else {
                Map<String, Object> map = new HashMap<>();

                try (Connection conexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (!existeEmpleado(conexion, codigoReporte)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SIN DATOS", "NO EXISTE EMPLEADO CON EL CODIGO INGRESADO"));
                        return;
                    }

                    map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
                    map.put("CODIGO", codigoReporte);
                    map.put("FechaDesde", formatoFecha.format(fechaDesde));
                    map.put("FechaHasta", formatoFecha.format(fechaHasta));
                    map.put("FechaCorte", formatoFecha.format(fechaCorte));

                    JasperReportUtil.ReportOutput reportOutput = JasperReportUtil.getOutputStreamsFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_ASISTENCIAS);
                    outputStream = reportOutput.getPdfOutputStream();
                    excelOutputStream = reportOutput.getExcelOutputStream();
                    media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
                }
            }
        } catch (Exception e) {
            limpiarReporteGenerado();
            LOGGER.log(Level.SEVERE, "Error generando reporte financiero", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL REPORTE: " + e.getMessage()));
        }
    }

    private String normalizarCodigoTimbrado(String codigoIngresado) {
        if (codigoIngresado == null) {
            return null;
        }
        String codigoNormalizado = codigoIngresado.replaceAll("\\D", "");
        return codigoNormalizado.isEmpty() ? null : codigoNormalizado;
    }

    private boolean existeEmpleado(Connection conexion, String codigoReporte) throws SQLException {
        String sql = "SELECT COUNT(1) FROM T_DAT_EMPLEADO WHERE TO_NUMBER(CODIGO) = TO_NUMBER(?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoReporte);
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
        return "ReporteFinanciero";
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
            LOGGER.log(Level.SEVERE, "Error descargando reporte financiero", e);
        }
    }

    public StreamedContent getArchivoDescarga() {
        try {
            if (outputStream == null || outputStream.size() == 0) {
                return null;
            }

            return new DefaultStreamedContent(
                    new ByteArrayInputStream(outputStream.toByteArray()),
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

            return new DefaultStreamedContent(
                    new ByteArrayInputStream(excelOutputStream.toByteArray()),
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

    public boolean isRenderBarra() {
        return renderBarra;
    }

    public void setRenderBarra(boolean renderBarra) {
        this.renderBarra = renderBarra;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getPath() {
        return path;
    }

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

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }
}
