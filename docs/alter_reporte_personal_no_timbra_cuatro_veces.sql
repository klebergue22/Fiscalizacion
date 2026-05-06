-- Correccion para que se despliegue el menu "Personal que no timbra 4 veces".
-- El menu se filtra por T_PAGINAPERFIL, no solo por T_MENU.

UPDATE FISCALIZACION.T_PAGINA
   SET NOMBRE = 'Personal que no timbra 4 veces',
       URL = 'faces/reportes/ReportePersonalNoTimbraCuatroVeces.xhtml',
       VERSION = 1
 WHERE ID_PAGINA = 31;

UPDATE FISCALIZACION.T_MENU
   SET NOMBRE = 'Personal que no timbra 4 veces',
       ID_MENU_PADRE = 17,
       ORDEN = 14,
       VERSION = '1         ',
       ID_PAGINA = 31
 WHERE ID_MENU = 38;

-- Copia los perfiles que ya tienen acceso al menu padre "Reportes Talento Humano".
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

-- Perfiles usados por los reportes recientes.
INSERT INTO FISCALIZACION.T_PAGINAPERFIL
(ID_PAGINA, ID_PERFIL, VERSION)
SELECT 31, perfiles.ID_PERFIL, 1
  FROM (
        SELECT 1 ID_PERFIL FROM DUAL
        UNION ALL SELECT 2 FROM DUAL
        UNION ALL SELECT 4 FROM DUAL
        UNION ALL SELECT 6 FROM DUAL
        UNION ALL SELECT 100 FROM DUAL
       ) perfiles
 WHERE NOT EXISTS (
       SELECT 1
         FROM FISCALIZACION.T_PAGINAPERFIL existe
        WHERE existe.ID_PAGINA = 31
          AND existe.ID_PERFIL = perfiles.ID_PERFIL
   );

COMMIT;
