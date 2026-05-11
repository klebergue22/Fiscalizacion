package gob.igm.ec.reportes;

import gob.igm.ec.reportes.servicio.ReporteSalidaAntesJornadaSemanalServicio;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import gob.igm.rh.modelo.VGestionesVigentes;
import gob.igm.rh.servicio.DatosEmpleadoServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ReporteSalidaAntesJornadaSemanalControlador extends FacesUtil implements Serializable {

    private StreamedContent media;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream excelOutputStream;
    private boolean renderBarra;
    private String path;
    private Date fechaDesde;
    private Date fechaHasta;
    private Short noGestion;
    private String codigo;
    private List<VGestionesVigentes> listadoGestiones;

    @EJB
    private DatosEmpleadoServicio servicioListadoGestiones;

    @EJB
    private ReporteSalidaAntesJornadaSemanalServicio reporteSalidaAntesJornadaSemanalServicio;

    @PostConstruct
    public void init() {
        listadoGestiones = servicioListadoGestiones.obtenerTodasGestiones();
        Collections.sort(listadoGestiones, new Comparator<VGestionesVigentes>() {
            @Override
            public int compare(VGestionesVigentes gestion1, VGestionesVigentes gestion2) {
                String descripcion1 = gestion1.getDescrip() == null ? "" : gestion1.getDescrip();
                String descripcion2 = gestion2.getDescrip() == null ? "" : gestion2.getDescrip();
                return descripcion1.compareToIgnoreCase(descripcion2);
            }
        });
        this.renderBarra = false;
        this.path = JasperReportUtil.PATH_REPORTE_SALIDA_ANTES_JORNADA_SEMANAL;
    }

    public void generarReporteSalidaAntesJornadaSemanal() {
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

            outputStream = reporteSalidaAntesJornadaSemanalServicio.generar(fechaDesde, fechaHasta, noGestion, normalizarCodigo());
            excelOutputStream = reporteSalidaAntesJornadaSemanalServicio.generarExcel(fechaDesde, fechaHasta, noGestion, normalizarCodigo());
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
        return "reporteSalidaAntesJornadaSemanal";
    }

    public StreamedContent getArchivoDescarga() {
        try {
            if (outputStream == null || outputStream.size() == 0) {
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

    private String normalizarCodigo() {
        return codigo == null || codigo.trim().isEmpty() ? null : codigo.trim();
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

    public Short getNoGestion() {
        return noGestion;
    }

    public void setNoGestion(Short noGestion) {
        this.noGestion = noGestion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<VGestionesVigentes> getListadoGestiones() {
        return listadoGestiones;
    }

    public void setListadoGestiones(List<VGestionesVigentes> listadoGestiones) {
        this.listadoGestiones = listadoGestiones;
    }
}
