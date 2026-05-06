-- Configuracion de menu para exponer el reporte:
-- "Personal que no ha timbrado a la salida y retorno del tiempo designado
-- para la alimentacion, (semanal)".
-- Siguiente menu luego de ID_PAGINA=28 e ID_MENU=35.

INSERT INTO FISCALIZACION.T_PAGINA
(ID_PAGINA, NOMBRE, URL, VERSION, FECHA_MODIFICACION)
VALUES
(29, 'Personal que no ha timbrado a la salida y retorno del tiempo designado para la alimentacion, (semanal)', 'faces/reportes/ReporteAlmuerzoSinTimbrarSemanal.xhtml', 1, NULL);

INSERT INTO FISCALIZACION.T_MENU
(ID_MENU, ID_MENU_PADRE, ORDEN, VERSION, ID_PAGINA, NOMBRE)
VALUES
(36, 17, 12, '1         ', 29, 'Personal que no ha timbrado a la salida y retorno del tiempo designado para la alimentacion, (semanal)');

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(29, 1, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(29, 2, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(29, 4, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(29, 6, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(29, 100, 1);

COMMIT;
