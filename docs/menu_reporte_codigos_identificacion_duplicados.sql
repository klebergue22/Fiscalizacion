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

-- Crear pagina
INSERT INTO FISCALIZACION.T_PAGINA
(ID_PAGINA, NOMBRE, URL, VERSION, FECHA_MODIFICACION)
VALUES
(31, 'Codigos identificacion duplicados', 'faces/reportes/ReporteCodigosIdentificacionDuplicados.xhtml', 1, NULL);

-- Crear opcion de menu dentro de "Reportes Talento Humano" (ID_MENU_PADRE=17)
INSERT INTO FISCALIZACION.T_MENU
(ID_MENU, ID_MENU_PADRE, ORDEN, VERSION, ID_PAGINA, NOMBRE)
VALUES
(38, 17, 14, '1         ', 31, 'Codigos identificacion duplicados');

-- Asignar permisos a perfiles
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(31, 1, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(31, 2, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(31, 4, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(31, 6, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(31, 100, 1);

COMMIT;
