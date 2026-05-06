-- Configuracion de menu para exponer el reporte:
-- "Personal que timbra antes de terminar la jornada normal de trabajo 16:30, (semanal)".
-- Ajustar ID_PAGINA, ID_MENU y ORDEN si ya existen registros posteriores en el ambiente.

INSERT INTO FISCALIZACION.T_PAGINA
(ID_PAGINA, NOMBRE, URL, VERSION, FECHA_MODIFICACION)
VALUES
(30, 'Personal que timbra antes de terminar la jornada normal de trabajo 16:30, (semanal)', 'faces/reportes/ReporteSalidaAntesJornadaSemanal.xhtml', 1, NULL);

INSERT INTO FISCALIZACION.T_MENU
(ID_MENU, ID_MENU_PADRE, ORDEN, VERSION, ID_PAGINA, NOMBRE)
VALUES
(37, 17, 13, '1         ', 30, 'Personal que timbra antes de terminar la jornada normal de trabajo 16:30, (semanal)');

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(30, 1, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(30, 2, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(30, 4, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(30, 6, 1);

INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
VALUES(30, 100, 1);

COMMIT;
