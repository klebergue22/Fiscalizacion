package gob.igm.ec.reportes;

import gob.igm.ec.reportes.servicio.ReporteCodigosIdentificacionDuplicadosServicio;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ReporteCodigosIdentificacionDuplicadosControlador extends FacesUtil implements Serializable {

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private boolean renderBarra;
    private String path;

    @EJB
    private ReporteCodigosIdentificacionDuplicadosServicio reporteCodigosIdentificacionDuplicadosServicio;

    public ReporteCodigosIdentificacionDuplicadosControlador() {
        this.renderBarra = false;
        this.path = JasperReportUtil.PATH_REPORTE_CODIGOS_IDENTIFICACION_DUPLICADOS;
    }

    public void generarReporteCodigosIdentificacionDuplicados() {
        try {
            this.renderBarra = true;

            outputStream = reporteCodigosIdentificacionDuplicadosServicio.generar();
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
        return "reporteCodigosIdentificacionDuplicados";
    }

    public StreamedContent getArchivoDescarga() {
        try {
            outputStream = reporteCodigosIdentificacionDuplicadosServicio.generar();
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
}
