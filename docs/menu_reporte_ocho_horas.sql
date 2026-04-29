-- Configuración de menú para exponer el reporte "No cumple 8 horas (semanal)"
-- Ajuste IDs de acuerdo a su ambiente.

-- 1) Crear página
INSERT INTO T_PAGINA (ID_PAGINA, NOMBRE, URL, VERSION)
VALUES (901, 'Reporte 8 horas semanal', 'faces/reportes/ReporteNoTimbrados.xhtml', '1');

-- 2) Crear opción de menú dentro de "Reportes Talento Humano" (ID_MENU_PADRE=17)
INSERT INTO T_MENU (ID_MENU, NOMBRE, ORDEN, VERSION, ID_MENU_PADRE, ID_PAGINA)
VALUES (901, 'R. 8 Horas Semanal', 99, '1', 17, 901);

-- 3) Asignar permiso al perfil (ejemplo: perfil administrador = 1)
INSERT INTO T_PAGINAPERFIL (ID_PERFIL, ID_PAGINA, VERSION)
VALUES (1, 901, '1');

-- 4) Si ya existe la opción en su ambiente, renombrar a un texto corto en menú:
UPDATE T_MENU
   SET NOMBRE = 'R. 8 Horas Semanal'
 WHERE ID_PAGINA = 901;

COMMIT;
