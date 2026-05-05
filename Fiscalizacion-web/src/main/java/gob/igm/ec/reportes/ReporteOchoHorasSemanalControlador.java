package gob.igm.ec.reportes;

import gob.igm.ec.reportes.servicio.ReporteOchoHorasSemanalServicio;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ReporteOchoHorasSemanalControlador extends FacesUtil implements Serializable {

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private boolean renderBarra;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;

    @EJB
    private ReporteOchoHorasSemanalServicio reporteOchoHorasSemanalServicio;

    public ReporteOchoHorasSemanalControlador() {
        this.renderBarra = false;
        this.path = JasperReportUtil.PATH_REPORTE_OCHO_HORAS_SEMANAL;
    }

    public void generarReporteOchoHorasSemanal() {
        try {
            this.renderBarra = true;

            if (fechaDesde == null || fechaHasta == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA INICIO Y FECHA FIN"));
                return;
            }

            if (fechaHasta.before(fechaDesde)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA FECHA FIN NO PUEDE SER MENOR A LA FECHA INICIO"));
                return;
            }

            outputStream = reporteOchoHorasSemanalServicio.generar(fechaDesde, fechaHasta);
            media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }

    public String getNameFilePdf() {
        return "reporteOchoHorasSemanal";
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
