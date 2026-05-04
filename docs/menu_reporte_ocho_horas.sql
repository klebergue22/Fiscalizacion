-- Opcion independiente: "Personal que no ha cumplido con las ocho horas de trabajo, (semanal)".
-- Debe aparecer dentro del menu "Reportes Talento Humano" (ID_MENU_PADRE = 17).
-- Usa los siguientes IDs libres segun el script actual:
--   T_PAGINA.ID_PAGINA = 28
--   T_MENU.ID_MENU = 35
-- No modifica la pagina existente ID_PAGINA = 27 ni el menu existente ID_MENU = 34.

MERGE INTO FISCALIZACION.T_PAGINA dst
USING (
    SELECT
        28 AS ID_PAGINA,
        'Personal que no ha cumplido con las ocho horas de trabajo, (semanal)' AS NOMBRE,
        'faces/reportes/ReporteOchoHorasSemanal.xhtml' AS URL,
        1 AS VERSION,
        CAST(NULL AS DATE) AS FECHA_MODIFICACION
    FROM DUAL
) src
ON (dst.ID_PAGINA = src.ID_PAGINA)
WHEN MATCHED THEN UPDATE SET
    dst.NOMBRE = src.NOMBRE,
    dst.URL = src.URL,
    dst.VERSION = src.VERSION,
    dst.FECHA_MODIFICACION = src.FECHA_MODIFICACION
WHEN NOT MATCHED THEN INSERT
    (ID_PAGINA, NOMBRE, URL, VERSION, FECHA_MODIFICACION)
VALUES
    (src.ID_PAGINA, src.NOMBRE, src.URL, src.VERSION, src.FECHA_MODIFICACION);

MERGE INTO FISCALIZACION.T_MENU dst
USING (
    SELECT
        35 AS ID_MENU,
        17 AS ID_MENU_PADRE,
        11 AS ORDEN,
        '1         ' AS VERSION,
        28 AS ID_PAGINA,
        'Personal que no ha cumplido con las ocho horas de trabajo, (semanal)' AS NOMBRE
    FROM DUAL
) src
ON (dst.ID_MENU = src.ID_MENU)
WHEN MATCHED THEN UPDATE SET
    dst.ID_MENU_PADRE = src.ID_MENU_PADRE,
    dst.ORDEN = src.ORDEN,
    dst.VERSION = src.VERSION,
    dst.ID_PAGINA = src.ID_PAGINA,
    dst.NOMBRE = src.NOMBRE
WHEN NOT MATCHED THEN INSERT
    (ID_MENU, ID_MENU_PADRE, ORDEN, VERSION, ID_PAGINA, NOMBRE)
VALUES
    (src.ID_MENU, src.ID_MENU_PADRE, src.ORDEN, src.VERSION, src.ID_PAGINA, src.NOMBRE);

MERGE INTO FISCALIZACION.T_PAGINAPERFIL dst
USING (
    SELECT 28 AS ID_PAGINA, 1 AS ID_PERFIL, 1 AS VERSION FROM DUAL
    UNION ALL
    SELECT 28 AS ID_PAGINA, 2 AS ID_PERFIL, 1 AS VERSION FROM DUAL
    UNION ALL
    SELECT 28 AS ID_PAGINA, 4 AS ID_PERFIL, 1 AS VERSION FROM DUAL
    UNION ALL
    SELECT 28 AS ID_PAGINA, 6 AS ID_PERFIL, 1 AS VERSION FROM DUAL
    UNION ALL
    SELECT 28 AS ID_PAGINA, 100 AS ID_PERFIL, 1 AS VERSION FROM DUAL
) src
ON (dst.ID_PAGINA = src.ID_PAGINA AND dst.ID_PERFIL = src.ID_PERFIL)
WHEN MATCHED THEN UPDATE SET
    dst.VERSION = src.VERSION
WHEN NOT MATCHED THEN INSERT
    (ID_PAGINA, ID_PERFIL, VERSION)
VALUES
    (src.ID_PAGINA, src.ID_PERFIL, src.VERSION);

COMMIT;
