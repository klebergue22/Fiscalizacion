# Diagnóstico de puertos y error UTF-8 en GlassFish

El error `java.io.CharConversionException: Conversion error 4` en `org.glassfish.grizzly.http.util.UTF8Decoder` se produce antes de que la petición llegue a JSF o a los filtros de la aplicación. Normalmente indica que GlassFish recibió una URL mal codificada o tráfico que no corresponde al listener HTTP.

## Puertos por defecto

En una instalación típica de GlassFish:

- `8080` es el listener HTTP (`http-listener-1`). La URL debe iniciar con `http://`.
- `8181` es el listener HTTPS (`http-listener-2`). La URL debe iniciar con `https://`.
- `4848` es la consola de administración.

Por eso, para esta aplicación la URL esperada en HTTP es:

```text
http://<servidor>:8080/Fiscalizacion-web/faces/index.xhtml
```

Y si se usa el listener HTTPS por defecto:

```text
https://<servidor>:8181/Fiscalizacion-web/faces/index.xhtml
```

No se debe abrir `https://<servidor>:8080/...` si el listener `8080` está configurado como HTTP, porque GlassFish intentará interpretar bytes de TLS como una URI HTTP y puede registrar errores de conversión UTF-8.

## Verificación con `asadmin`

Para confirmar qué puerto usa cada listener:

```bash
asadmin get server.network-config.network-listeners.network-listener.http-listener-1.port
asadmin get server.network-config.network-listeners.network-listener.http-listener-2.port
asadmin get server.network-config.network-listeners.network-listener.admin-listener.port
```

Para revisar si el listener tiene seguridad SSL activada:

```bash
asadmin get server.network-config.network-listeners.network-listener.http-listener-1.security-enabled
asadmin get server.network-config.network-listeners.network-listener.http-listener-2.security-enabled
```

## Si la aplicación no responde en 8080

1. Confirmar que el dominio esté iniciado.
2. Confirmar que `http-listener-1` está habilitado y usando el puerto `8080`.
3. Confirmar que no exista otro proceso ocupando el puerto `8080`.
4. Probar con el esquema correcto: `http://` para `8080` y `https://` para `8181`.
5. Confirmar que el despliegue tenga el context root `/Fiscalizacion-web`.

## Ajuste incluido en la aplicación

El descriptor `glassfish-web.xml` declara explícitamente el context root `/Fiscalizacion-web` y la codificación UTF-8 para parámetros de petición. Esto evita diferencias entre despliegues del EAR y despliegues directos del WAR, aunque una URL realmente mal codificada o tráfico HTTPS enviado al puerto HTTP debe corregirse en el cliente, navegador, proxy o configuración del listener.
