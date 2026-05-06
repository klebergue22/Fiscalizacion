-- Correccion de nombres de reportes existentes.
-- Convierte:
--   ID_PAGINA 30 / ID_MENU 37: Verificacion mensual -> Personal que no timbra a la salida
--   ID_PAGINA 29 / ID_MENU 36: Almuerzo sin timbrar -> Retraso almuerzo

UPDATE FISCALIZACION.T_PAGINA
   SET NOMBRE = 'Personal que no timbra a la salida',
       URL = 'faces/reportes/ReporteJornadaLaboralMensual.xhtml',
       VERSION = 1
 WHERE ID_PAGINA = 30;

UPDATE FISCALIZACION.T_MENU
   SET NOMBRE = 'Personal que no timbra a la salida',
       ID_MENU_PADRE = 17,
       ORDEN = 13,
       VERSION = '1         ',
       ID_PAGINA = 30
 WHERE ID_MENU = 37;

UPDATE FISCALIZACION.T_PAGINA
   SET NOMBRE = 'Retraso almuerzo',
       URL = 'faces/reportes/ReporteAlmuerzoSinTimbrarSemanal.xhtml',
       VERSION = 1
 WHERE ID_PAGINA = 29;

UPDATE FISCALIZACION.T_MENU
   SET NOMBRE = 'Retraso almuerzo',
       ID_MENU_PADRE = 17,
       ORDEN = 12,
       VERSION = '1         ',
       ID_PAGINA = 29
 WHERE ID_MENU = 36;

-- Asegura acceso a los perfiles que ya tienen el menu padre "Reportes Talento Humano".
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT paginas.ID_PAGINA, pp.ID_PERFIL, 1
  FROM FISCALIZACION.T_PAGINAPERFIL pp
 CROSS JOIN (
        SELECT 29 ID_PAGINA FROM DUAL
        UNION ALL SELECT 30 FROM DUAL
       ) paginas
 WHERE pp.ID_PAGINA = 17
   AND NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_PAGINAPERFIL existe
        WHERE existe.ID_PAGINA = paginas.ID_PAGINA
          AND existe.ID_PERFIL = pp.ID_PERFIL
   );

-- Perfiles usados por los reportes recientes.
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT paginas.ID_PAGINA, perfiles.ID_PERFIL, 1
  FROM (
        SELECT 29 ID_PAGINA FROM DUAL
        UNION ALL SELECT 30 FROM DUAL
       ) paginas
 CROSS JOIN (
        SELECT 1 ID_PERFIL FROM DUAL
        UNION ALL SELECT 2 FROM DUAL
        UNION ALL SELECT 4 FROM DUAL
        UNION ALL SELECT 6 FROM DUAL
        UNION ALL SELECT 100 FROM DUAL
       ) perfiles
 WHERE NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_PAGINAPERFIL existe
        WHERE existe.ID_PAGINA = paginas.ID_PAGINA
          AND existe.ID_PERFIL = perfiles.ID_PERFIL
   );

COMMIT;
