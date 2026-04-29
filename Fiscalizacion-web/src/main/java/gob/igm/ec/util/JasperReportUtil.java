/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author VERA_MAYRA
 */
public class JasperReportUtil {
    
    public final static String PATH;
    public final static String PATH_REPORTE_TIMBRADOS;
    public final static String PATH_REPORTE_ACCIONES;
    public final static String PATH_REPORTE_ACCIONES_LIBRO;
    public final static String PATH_REPORTE_ACCIONES_MINISTERIO;
    public final static String PATH_REPORTE_ACCIONES_REVERSO;
    public final static String PATH_REPORTE_PERMISOS_PERSONAL;
    public final static String PATH_REPORTE_PERMISOS_CODIGO;
    public final static String PATH_REPORTE_PRODUCCION_DOCUMENTOS_D;
    public final static String PATH_REPORTE_TIPO_PERMISO;
    public final static String PATH_REPORTE_ASISTENCIAS;
    public final static String PATH_REPORTE_ATRASOS;
    public final static String PATH_REPORTE_INVENTARIO_GRUPOS;
    public final static String PATH_REPORTE_PERMISOS_IGM;
    public final static String PATH_REPORTE_TIMBRADOS_IGM;
    public final static String PATH_REPORTE_NO_TIMBRADOS;
    public final static String PATH_REPORTE_NO_TIMBRADOS_LOSEP;     
    public final static String PATH_REPORTE_OCHO_HORAS_SEMANAL;
    
    
    public final static String PATH_IMAGES;
    public final static String PATH_IMAGES1;
    public final static String PATH_IMAGES2;
    public final static String PATH_IMAGES3;
    public final static String PATH_IMAGES4;
    public final static String TIPO_PDF;
    public final static String TIPO_EXCEL;
    public final static String TIPO_HTML;
  
    
    //public final static String PATH_REPORTE_SALDOS_VACACIONES;

    static {
        //PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jrxml/")+"\\";
                //PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\jrxml\\")+"\\";
        //PATH_REPORTE_AVANCE_PRODUCCCION = PATH + "AVANCE_PRODUCCIONcopy.jasper";
        //PATH_REPORTE_AVANCE_PRODUCCCION = PATH + "O.jasper";
        //PATH_IMAGES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\resources\\images\\")+"\\IGM1.png";
        //TIPO_PDF = "application/pdf";
        //TIPO_EXCEL = "application/xls";
        PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/jrxml/");
        
        PATH_REPORTE_PRODUCCION_DOCUMENTOS_D=PATH+"//reporte_personal_2.jasper/";
        PATH_REPORTE_TIMBRADOS = PATH+"//reporte_timbrados.jasper/";
        PATH_REPORTE_PERMISOS_PERSONAL = PATH + "//reporte_permisos_2.jasper/";
        PATH_REPORTE_ACCIONES = PATH + "//reporte_acciones_2.jasper/";
        PATH_REPORTE_ACCIONES_LIBRO = PATH + "//AccionPersonal_V2.jasper/";
        PATH_REPORTE_ACCIONES_REVERSO = PATH + "//reporte_acciones_reverso.jasper/";
        PATH_REPORTE_TIPO_PERMISO = PATH + "//reporte_tipo_permiso.jasper/";
        PATH_REPORTE_ATRASOS = PATH + "//reporte_tipo_permiso.jasper/";
        PATH_REPORTE_INVENTARIO_GRUPOS = PATH + "//CONSUMOXGRUPO.jasper/";
        PATH_REPORTE_ASISTENCIAS = PATH + "//liquidacion_asistencia.jasper/";
        PATH_REPORTE_PERMISOS_IGM = PATH + "//reporte_permisos_personal.jasper/";
        PATH_REPORTE_TIMBRADOS_IGM = PATH + "//reporte_timbrados_personal.jasper/";
        PATH_REPORTE_PERMISOS_CODIGO = PATH + "//reporte_permisos_codigo.jasper/";
        PATH_REPORTE_NO_TIMBRADOS = PATH + "//reporte_no_timbrados.jasper/";
        PATH_REPORTE_NO_TIMBRADOS_LOSEP = PATH + "//reporte_no_timbrados_losep.jasper/";
        PATH_REPORTE_OCHO_HORAS_SEMANAL = PATH + "//reporte_no_timbrados_losep.jasper/";
        PATH_REPORTE_ACCIONES_MINISTERIO = PATH + "//AccionPersonal_V4.jasper/";
        
        PATH_IMAGES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/")+"/LOGO1.png/";
        PATH_IMAGES1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/")+"/accion2JPG.jpg/";
        PATH_IMAGES2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/")+"/accionreverso2.jpg/";
        PATH_IMAGES3 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/")+"/accionf.jpeg/";
        PATH_IMAGES4 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/")+"/accionr.jpeg/";
        TIPO_PDF = "application/pdf";
        TIPO_EXCEL = "application/xls";
        TIPO_HTML="application/html";
    }
    
    public static Logger localLogger = Logger.getLogger(JasperReportUtil.class);

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Retorna el valor ELContext.
     *
     * @return El eL context
     */
    public ELContext getELContext() {
        return this.getFacesContext().getELContext();
    }
       
    public static ByteArrayOutputStream getOutputStreamFromReport(Connection conn, Map map, String pathJasper) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
    
        try{
       
        InputStream inputStream = new FileInputStream(pathJasper);
        

            if (inputStream == null) {
                throw new ClassNotFoundException("Archivo " + pathJasper + " no se encontró");
            }
       
        JasperPrint jp = JasperFillManager.fillReport(pathJasper,map,conn);
        JasperExportManager.exportReportToPdfStream(jp, os);
       
        os.flush();
        os.close();
            try {
                conn.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(JasperReportUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(ClassNotFoundException | JRException | IOException ex){
            localLogger.error(ex);
        }
          
        return os;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //  METODO PARA LA IMPRESION A EXCEL EN PRUEBAS
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static ByteArrayOutputStream getSalidaExcel(Connection conn, Map map, String pathJasper) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {

            InputStream inputStream = new FileInputStream(pathJasper);

            if (inputStream == null) {
                throw new ClassNotFoundException("Archivo " + pathJasper + " no se encontró");
            }

            JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, conn);
            //JasperExportManager.exportReportToPdfStream(jp, os);

            //jasperPrint = JasperFillManager.fillReport(reportePath, parametros, conn);
// Mostrando el documento
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

// .setHeader("Content-disposition", "attachment; filename=" + nombreReporte + ".xls");
//String fileName = PATH.substring(PATH.lastIndexOf("/") + 1);
            String fileName = "nombre";
            httpServletResponse.setHeader("Content-Disposition", "Attachment; filename=" + fileName + ".xls");

            httpServletResponse.setContentType("application/vnd.ms-excel");
//httpServletResponse.setContentLength(arrayOutputStream.toByteArray().length);

            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            FacesContext.getCurrentInstance().responseComplete();

            os.flush();
            os.close();
            try {
                conn.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(JasperReportUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | JRException | IOException ex) {
            localLogger.error(ex);
        }

        return os;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ESTE METODO LLAMA PARA CREAR EL ARCHIVO PDF    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static StreamedContent getStreamContentFromOutputStream(ByteArrayOutputStream os, String contentType, String nameFile) throws Exception {
        StreamedContent file = null;
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        file = new DefaultStreamedContent(is, contentType, nameFile);
        return file;
    }

    public static StreamedContent getStreamContentReport(List list, Map map, String pathJasper, String nameFilePdf) {
        StreamedContent pdf = null;
        try{
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jp = JasperFillManager.fillReport(pathJasper, map, dataSource);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jp, os);
        os.flush();
        os.close();
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        pdf = new DefaultStreamedContent(is, "application/pdf", nameFilePdf);
        } catch (Exception ex) {
            localLogger.error(ex);
        }
         return pdf;
    }
    
     public void jasperReport(Connection conn, final String urlReporte, String tipo, Map<String, Object> params) throws ClassNotFoundException {
       ExternalContext econtext = this.getFacesContext().getExternalContext();
        FacesContext fcontext = this.getFacesContext();
        try {
            JRExporter exporter = null;
            InputStream inputStream = new FileInputStream(urlReporte);
            if (inputStream == null) {
                throw new ClassNotFoundException("Archivo " + urlReporte + " no se encontró");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, conn);
            HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
            response.setContentType(tipo);
            if ("application/pdf".equals(tipo)) {
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
            } else {
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporter.setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, false);
                exporter.setParameter(
                        JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "inline; filename=\"reporte.xls\";");
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);

            }

            if (exporter != null) {
                exporter.exportReport();
            } else {
               //localLogger.error(ex);
            }
            conn.close();

        } catch (Exception ex) {
            localLogger.error(ex);

        }
        fcontext.responseComplete();
    }     
}
