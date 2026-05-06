-- Configuracion de menu para exponer el reporte de codigos de identificacion duplicados.
-- Ajuste IDs de acuerdo a su ambiente.

-- 1) Crear pagina
INSERT INTO T_PAGINA (ID_PAGINA, NOMBRE, URL, VERSION)
VALUES (904, 'Codigos identificacion duplicados', 'faces/reportes/ReporteCodigosIdentificacionDuplicados.xhtml', '1');

-- 2) Crear opcion de menu dentro de "Reportes Talento Humano" (ID_MENU_PADRE=17)
INSERT INTO T_MENU (ID_MENU, NOMBRE, ORDEN, VERSION, ID_MENU_PADRE, ID_PAGINA)
VALUES (904, 'R. Codigos Duplicados', 99, '1', 17, 904);

-- 3) Asignar permiso al perfil (ejemplo: perfil administrador = 1)
INSERT INTO T_PAGINAPERFIL (ID_PERFIL, ID_PAGINA, VERSION)
VALUES (1, 904, '1');

COMMIT;
