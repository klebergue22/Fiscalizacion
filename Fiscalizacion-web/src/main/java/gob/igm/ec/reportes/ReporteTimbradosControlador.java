package gob.igm.ec.reportes;

import gob.igm.ec.administracion.MenuOP;
import gob.igm.ec.util.FacesUtil;
import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alexander Jiménez
 */
@Named
@SessionScoped
public class ReporteTimbradosControlador extends FacesUtil implements Serializable {

private StreamedContent media;
private ByteArrayOutputStream outputStream;
private String number;
private boolean renderBarra;
private String uno;
MenuOP menuOP = super.getBean(MenuOP.NOMBRE_BEAN);
String nombre;
private String path;
private Date fechaDesde;
private Date fechaHasta;
private String codigo;private String codigo1;private String codigo2;private String codigo3;private String codigo4;private String codigo5;
private String codigo6;private String codigo7;private String codigo8;private String codigo9;private String codigo10;private String codigo11;
private String codigo12;private String codigo13;

DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

//@EJB
    //private gob.igm.ec.servicios.TProduccionPapeletasFacade ejbFacade;
    /**
     * Creates a new instance of reporteGlobalP
     */
    public ReporteTimbradosControlador() {
        this.setRenderBarra(false);
        this.setUno(JasperReportUtil.PATH_IMAGES);
        //this.setPath(JasperReportUtil.PATH_REPORTE_AVANCE_PRODUCCCION);
        this.setPath(JasperReportUtil.PATH_REPORTE_TIMBRADOS);
    }
   
   public void generateReport() {
        try {
            this.setRenderBarra(true);
            
            System.out.println("codigo :" + this.codigo);
            System.out.println("fecha desde :" + this.fechaDesde);
            System.out.println("fecha hasta :" + this.fechaHasta);
            if (codigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde == null || fechaHasta == null ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } 
            else  {
                Map<String, Object> map = new HashMap<>();
                Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "RH", "oraclerrhh2010");
                map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
                map.put("CODIGO", this.codigo);
                map.put("CODIGO1", this.codigo1);
                map.put("CODIGO2", this.codigo2);
                map.put("CODIGO3", this.codigo3);
                map.put("CODIGO4", this.codigo4);
                map.put("CODIGO5", this.codigo5);
                map.put("CODIGO6", this.codigo6);
                map.put("CODIGO7", this.codigo7);
                map.put("CODIGO8", this.codigo8);
                map.put("CODIGO9", this.codigo9);
                map.put("CODIGO10", this.codigo10);
                map.put("CODIGO11", this.codigo11);
                map.put("CODIGO12", this.codigo12);
                map.put("CODIGO13", this.codigo13);
                
                String fecha = formatoFecha.format(fechaDesde);
                String fecha2 = formatoFecha.format(fechaHasta);
                map.put("FechaDesde", fecha);
                map.put("FechaHasta", fecha2);
                JasperReportUtil jasper = new JasperReportUtil();
                JRExporter exporter = null;
                outputStream = JasperReportUtil.getOutputStreamFromReport(conexion, map, JasperReportUtil.PATH_REPORTE_TIMBRADOS);
                media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
                conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }
   
   
      public void prueba2() {
        try {
            this.setRenderBarra(true);
            
            if (codigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE INGRESAR UN CODIGO DE TIMBRADO"));
            } else if (fechaDesde == null || fechaHasta == null ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR FECHA DESDE Y FECHA HASTA"));
            } 
            else  {
                Map<String, Object> map = new HashMap<>();
                Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.80:1521:IGM1", "RH", "oraclerrhh2010");
                map.put("pathImagen", JasperReportUtil.PATH_IMAGES);
                map.put("CODIGO", this.codigo);
                map.put("CODIGO1", this.codigo1);
                map.put("CODIGO2", this.codigo2);
                map.put("CODIGO3", this.codigo3);
                map.put("CODIGO4", this.codigo4);
                map.put("CODIGO5", this.codigo5);
                
                String fecha = formatoFecha.format(fechaDesde);
                String fecha2 = formatoFecha.format(fechaHasta);
                map.put("FechaDesde", fecha);
                map.put("FechaHasta", fecha2);
                JasperReportUtil jasper = new JasperReportUtil();
                JRExporter exporter = null;
                outputStream = JasperReportUtil.getSalidaExcel(conexion, map, JasperReportUtil.PATH_REPORTE_TIMBRADOS);
                media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf", getNameFilePdf());
                conexion.close();
            }
            
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
    }
   
    
    public String getNameFilePdf() {
        return "ReporteTimbrados";
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

    public String getCodigo1() {
        return codigo1;
    }

    public void setCodigo1(String codigo1) {
        this.codigo1 = codigo1;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public String getCodigo3() {
        return codigo3;
    }

    public void setCodigo3(String codigo3) {
        this.codigo3 = codigo3;
    }

    public String getCodigo4() {
        return codigo4;
    }

    public void setCodigo4(String codigo4) {
        this.codigo4 = codigo4;
    }

    public String getCodigo5() {
        return codigo5;
    }

    public void setCodigo5(String codigo5) {
        this.codigo5 = codigo5;
    }

    public String getCodigo6() {
        return codigo6;
    }

    public void setCodigo6(String codigo6) {
        this.codigo6 = codigo6;
    }

    public String getCodigo7() {
        return codigo7;
    }

    public void setCodigo7(String codigo7) {
        this.codigo7 = codigo7;
    }

    public String getCodigo8() {
        return codigo8;
    }

    public void setCodigo8(String codigo8) {
        this.codigo8 = codigo8;
    }

    public String getCodigo9() {
        return codigo9;
    }

    public void setCodigo9(String codigo9) {
        this.codigo9 = codigo9;
    }

    public String getCodigo10() {
        return codigo10;
    }

    public void setCodigo10(String codigo10) {
        this.codigo10 = codigo10;
    }

    public String getCodigo11() {
        return codigo11;
    }

    public void setCodigo11(String codigo11) {
        this.codigo11 = codigo11;
    }

    public String getCodigo12() {
        return codigo12;
    }

    public void setCodigo12(String codigo12) {
        this.codigo12 = codigo12;
    }

    public String getCodigo13() {
        return codigo13;
    }

    public void setCodigo13(String codigo13) {
        this.codigo13 = codigo13;
    }

   
}

