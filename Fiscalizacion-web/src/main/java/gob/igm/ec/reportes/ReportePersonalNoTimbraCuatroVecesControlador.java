package gob.igm.ec.reportes;

import gob.igm.ec.reportes.servicio.ReportePersonalNoTimbraCuatroVecesServicio;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ReportePersonalNoTimbraCuatroVecesControlador extends FacesUtil implements Serializable {

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream excelOutputStream;
    private boolean renderBarra;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;

    @EJB
    private ReportePersonalNoTimbraCuatroVecesServicio reportePersonalNoTimbraCuatroVecesServicio;

    public ReportePersonalNoTimbraCuatroVecesControlador() {
        this.renderBarra = false;
        this.path = JasperReportUtil.PATH_REPORTE_PERSONAL_NO_TIMBRA_CUATRO_VECES;
    }

    public void generarReportePersonalNoTimbraCuatroVeces() {
        try {
            this.renderBarra = true;

            if (!validarFechas()) {
                return;
            }

            outputStream = reportePersonalNoTimbraCuatroVecesServicio.generar(fechaDesde, fechaHasta);
            excelOutputStream = reportePersonalNoTimbraCuatroVecesServicio.generarExcel(fechaDesde, fechaHasta);
            if (outputStream == null || outputStream.size() == 0) {
                media = null;
                excelOutputStream = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL PDF. REVISE EL LOG DEL SERVIDOR."));
                return;
            }

            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
        } catch (Exception e) {
            media = null;
                excelOutputStream = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage()));
        }
    }

    public String getNameFilePdf() {
        return "reportePersonalNoTimbraCuatroVeces";
    }

    public StreamedContent getArchivoDescarga() {
        try {
            if (!validarFechas()) {
                return null;
            }

            outputStream = reportePersonalNoTimbraCuatroVecesServicio.generar(fechaDesde, fechaHasta);
            excelOutputStream = reportePersonalNoTimbraCuatroVecesServicio.generarExcel(fechaDesde, fechaHasta);
            if (outputStream == null || outputStream.size() == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO GENERAR EL PDF. REVISE EL LOG DEL SERVIDOR."));
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

    private boolean validarFechas() {
        if (fechaDesde == null || fechaHasta == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA INICIO Y FECHA FIN"));
            return false;
        }

        if (fechaHasta.before(fechaDesde)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA FIN NO PUEDE SER MENOR A LA FECHA INICIO"));
            return false;
        }

        return true;
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
