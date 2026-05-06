-- Configuracion de menu para exponer el reporte:
-- "Reporte para la verificacion que no se encuentre duplicados los codigos de
-- identificacion asignados a los servidores y trabajadores publicos del IGM".
-- Siguiente menu luego de ID_PAGINA=30 e ID_MENU=37.

-- Si ya se ejecuto el script anterior con ID 904, primero corregir los IDs.
UPDATE FISCALIZACION.T_PAGINAPERFIL
   SET ID_PAGINA = 31
 WHERE ID_PAGINA = 904;

UPDATE FISCALIZACION.T_MENU
   SET ID_MENU = 38,
       ID_PAGINA = 31,
       ORDEN = 14,
       VERSION = '1         ',
       NOMBRE = 'Codigos identificacion duplicados'
 WHERE ID_MENU = 904
    OR ID_PAGINA = 904;

UPDATE FISCALIZACION.T_PAGINA
   SET ID_PAGINA = 31,
       NOMBRE = 'Codigos identificacion duplicados',
       URL = 'faces/reportes/ReporteCodigosIdentificacionDuplicados.xhtml',
       VERSION = 1
 WHERE ID_PAGINA = 904;

-- Correccion para ambiente donde ya exista la pagina/menu con ID 31/38.
UPDATE FISCALIZACION.T_PAGINA
   SET NOMBRE = 'Codigos identificacion duplicados',
       URL = 'faces/reportes/ReporteCodigosIdentificacionDuplicados.xhtml',
       VERSION = 1
 WHERE ID_PAGINA = 31;

UPDATE FISCALIZACION.T_MENU
   SET NOMBRE = 'Codigos identificacion duplicados',
       ID_MENU_PADRE = 17,
       ORDEN = 14,
       VERSION = '1         ',
       ID_PAGINA = 31
 WHERE ID_MENU = 38;

-- Si el menu existe pero no se despliega, normalmente falta permiso en T_PAGINAPERFIL.
-- Se copian los perfiles que ya tienen acceso al menu padre "Reportes Talento Humano".
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, pp.ID_PERFIL, 1
  FROM FISCALIZACION.T_PAGINAPERFIL pp
 WHERE pp.ID_PAGINA = 17
   AND NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_PAGINAPERFIL existe
        WHERE existe.ID_PAGINA = 31
          AND existe.ID_PERFIL = pp.ID_PERFIL
   );

-- Crear pagina
INSERT INTO FISCALIZACION.T_PAGINA
(ID_PAGINA, NOMBRE, URL, VERSION, FECHA_MODIFICACION)
SELECT 31, 'Codigos identificacion duplicados', 'faces/reportes/ReporteCodigosIdentificacionDuplicados.xhtml', 1, NULL
  FROM DUAL
 WHERE NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_PAGINA
        WHERE ID_PAGINA = 31
   );

-- Crear opcion de menu dentro de "Reportes Talento Humano" (ID_MENU_PADRE=17)
INSERT INTO FISCALIZACION.T_MENU
(ID_MENU, ID_MENU_PADRE, ORDEN, VERSION, ID_PAGINA, NOMBRE)
SELECT 38, 17, 14, '1         ', 31, 'Codigos identificacion duplicados'
  FROM DUAL
 WHERE NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_MENU
        WHERE ID_MENU = 38
   );

-- Asignar permisos a perfiles
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, 1, 1 FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM FISCALIZACION.T_PAGINAPERFIL WHERE ID_PAGINA = 31 AND ID_PERFIL = 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, 2, 1 FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM FISCALIZACION.T_PAGINAPERFIL WHERE ID_PAGINA = 31 AND ID_PERFIL = 2);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, 4, 1 FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM FISCALIZACION.T_PAGINAPERFIL WHERE ID_PAGINA = 31 AND ID_PERFIL = 4);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, 6, 1 FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM FISCALIZACION.T_PAGINAPERFIL WHERE ID_PAGINA = 31 AND ID_PERFIL = 6);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, 100, 1 FROM DUAL
 WHERE NOT EXISTS (SELECT 1 FROM FISCALIZACION.T_PAGINAPERFIL WHERE ID_PAGINA = 31 AND ID_PERFIL = 100);

COMMIT;
