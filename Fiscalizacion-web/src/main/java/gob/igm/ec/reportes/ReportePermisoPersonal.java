package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.DataManagerUsuario;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VDatoEmp;
import gob.igm.rh.servicio.DatosEmpleadoServicio;
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
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alexander Jimenez
 */
@ManagedBean
@SessionScoped
@Named
public class ReportePermisoPersonal extends FacesUtil implements Serializable {

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
    private String codigoTimbre;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat formatoComparacion = new SimpleDateFormat("dd/MM/yyyy");
    private Date date1;

    private List<VDatoEmp> listaEmpleado;

    @ManagedProperty("#{dataManagerUsuario}")
    private DataManagerUsuario usuarioManager;

    @EJB
    private DatosEmpleadoServicio datosEmpleadoEJB;

    public ReportePermisoPersonal() {
    }

    @PostConstruct
    public void init() {
        System.out.println("CODIGO DE LA PERSONA >>>>" + usuarioManager.getUsuario());
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
    }

    public void generateReport() {
        try {
            this.setRenderBarra(true);
            limpiarReporteGenerado();
            date1 = formatoComparacion.parse("01/01/2023");

            String codigoReporte = normalizarCodigoTimbrado(this.codigoTimbre);
            if (codigoReporte == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde == null || fechaHasta == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } else if (fechaDesde.after(fechaHasta)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA DESDE NO PUEDE SER MAYOR QUE LA FECHA HASTA"));
            } else if (fechaDesde.before(date1) || fechaHasta.before(date1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FECHAS DEBEN ESTAR EN ANIO 2023"));
            } else {
                Map<String, Object> map = new HashMap<>();

                Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "PERMISOS", "PERMIGM2012");
                String fecha = formatoFecha.format(fechaDesde);
                String fecha2 = formatoFecha.format(fechaHasta);
                if (!existenPermisos(conexion, codigoReporte, fecha, fecha2)) {
                    conexion.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SIN DATOS", "NO EXISTEN PERMISOS DE PERSONAL PARA EL RANGO INGRESADO"));
                    return;
                }

                map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
                map.put("pathImagen1", JasperReportUtil.PATH_IMAGES1);
                map.put("pathImagen2", JasperReportUtil.PATH_IMAGES2);
                map.put("CODIGO", codigoReporte);
                map.put("FechaDesde", fecha);
                map.put("FechaHasta", fecha2);

                JasperReportUtil.ReportOutput reportOutput = JasperReportUtil.getOutputStreamsFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_PERMISOS_IGM);
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

    private boolean existenPermisos(Connection conexion, String codigoReporte, String fechaDesdeReporte, String fechaHastaReporte) throws SQLException {
        String sql = "SELECT COUNT(1) "
                + "FROM V_PERMISOS_EMPLEADO "
                + "WHERE TO_NUMBER(CODIGO) = TO_NUMBER(?) "
                + "AND TRUNC(F_INICO) BETWEEN TO_DATE(?, 'DD/MM/YYYY') AND TO_DATE(?, 'DD/MM/YYYY')";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoReporte);
            ps.setString(2, fechaDesdeReporte);
            ps.setString(3, fechaHastaReporte);
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

    public DataManagerUsuario getUsuarioManager() {
        return usuarioManager;
    }

    public void setUsuarioManager(DataManagerUsuario usuarioManager) {
        this.usuarioManager = usuarioManager;
    }
}
