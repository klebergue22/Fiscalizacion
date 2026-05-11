package gob.igm.ec.reportes.servicio;

import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;

@Stateless
public class ReporteAlmuerzoSinTimbrarSemanalServicio {

    private static final String URL = "jdbc:oracle:thin:@192.168.1.80:1521:IGM1";
    private static final String USER = "PERMISOS";
    private static final String PASSWORD = "PERMIGM2012";

    private final DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public ByteArrayOutputStream generar(Date fechaDesde, Date fechaHasta) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("pathImagen", JasperReportUtil.PATH_IMAGES);
        parametros.put("FechaDesde", formatoFecha.format(fechaDesde));
        parametros.put("FechaHasta", formatoFecha.format(fechaHasta));

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return JasperReportUtil.getOutputStreamFromReport(conexion, parametros, JasperReportUtil.PATH_REPORTE_ALMUERZO_SIN_TIMBRAR_SEMANAL);
        }
    }
    public ByteArrayOutputStream generarExcel(Date fechaDesde, Date fechaHasta) throws Exception {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pathImagen", JasperReportUtil.PATH_IMAGES);
        parametros.put("FechaDesde", formatoFecha.format(fechaDesde));
        parametros.put("FechaHasta", formatoFecha.format(fechaHasta));

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return JasperReportUtil.getExcelOutputStreamFromReport(conexion, parametros, JasperReportUtil.PATH_REPORTE_ALMUERZO_SIN_TIMBRAR_SEMANAL);
        }
    }
}