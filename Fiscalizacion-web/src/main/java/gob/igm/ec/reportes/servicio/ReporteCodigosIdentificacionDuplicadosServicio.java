package gob.igm.ec.reportes.servicio;

import gob.igm.ec.util.JasperReportUtil;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;

@Stateless
public class ReporteCodigosIdentificacionDuplicadosServicio {

    private static final String URL = "jdbc:oracle:thin:@192.168.1.80:1521:IGM1";
    private static final String USER = "PERMISOS";
    private static final String PASSWORD = "PERMIGM2012";

    public ByteArrayOutputStream generar() throws Exception {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pathImagen", JasperReportUtil.PATH_IMAGES);

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return JasperReportUtil.getOutputStreamFromReport(
                    conexion,
                    parametros,
                    JasperReportUtil.PATH_REPORTE_CODIGOS_IDENTIFICACION_DUPLICADOS);
        }
    }
}
