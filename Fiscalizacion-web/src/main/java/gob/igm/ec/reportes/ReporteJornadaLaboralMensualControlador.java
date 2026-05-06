package gob.igm.ec.reportes;

import gob.igm.ec.reportes.servicio.ReporteJornadaLaboralMensualServicio;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ReporteJornadaLaboralMensualControlador extends FacesUtil implements Serializable {

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private boolean renderBarra;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;

    @EJB
    private ReporteJornadaLaboralMensualServicio reporteJornadaLaboralMensualServicio;

    public ReporteJornadaLaboralMensualControlador() {
        this.renderBarra = false;
        this.path = JasperReportUtil.PATH_REPORTE_JORNADA_LABORAL_MENSUAL;
    }

    public void generarReporteJornadaLaboralMensual() {
        try {
            this.renderBarra = true;

            if (!validarFechas("DEBE SELECCIONAR FECHA INICIO Y FECHA FIN")) {
                return;
            }

            outputStream = reporteJornadaLaboralMensualServicio.generar(fechaDesde, fechaHasta);
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
        return "reporteJornadaLaboralMensual";
    }

    public StreamedContent getArchivoDescarga() {
        try {
            if (!validarFechas("DEBE GENERAR EL REPORTE CON FECHA INICIO Y FECHA FIN")) {
                return null;
            }

            outputStream = reporteJornadaLaboralMensualServicio.generar(fechaDesde, fechaHasta);
            if (outputStream == null || outputStream.size() == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL PDF. REVISE EL LOG DEL SERVIDOR."));
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

    private boolean validarFechas(String mensajeFechasRequeridas) {
        if (fechaDesde == null || fechaHasta == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", mensajeFechasRequeridas));
            return false;
        }

        if (fechaHasta.before(fechaDesde)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA FIN NO PUEDE SER MENOR A LA FECHA INICIO"));
            return false;
        }

        if (diasEntre(fechaDesde, fechaHasta) > 31) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL RANGO DEL REPORTE MENSUAL NO PUEDE SUPERAR 31 DIAS"));
            return false;
        }

        return true;
    }

    private long diasEntre(Date desde, Date hasta) {
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.setTime(desde);
        limpiarHora(fechaInicial);

        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(hasta);
        limpiarHora(fechaFinal);

        long diferencia = fechaFinal.getTimeInMillis() - fechaInicial.getTimeInMillis();
        return (diferencia / (24L * 60L * 60L * 1000L)) + 1L;
    }

    private void limpiarHora(Calendar fecha) {
        fecha.set(Calendar.HOUR_OF_DAY, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);
    }

    public StreamedContent getMedia() {
        return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }

    public boolean isRenderBarra() {
        return renderBarra;
    }

    public void setRenderBarra(boolean renderBarra) {
        this.renderBarra = renderBarra;
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
}
