# Solución: Error "Error loading expression class" en JasperReports

## Problema Identificado

**Error:** `Error loading expression class: reporte_permisos_1778607128011_729313`

**Causa Raíz:** El código intenta compilar dinámicamente archivos `.jrxml` en runtime, pero GlassFish no tiene el compilador Java disponible en el classpath. Esto causa fallos al generar clases de expresiones dinámicamente.

---

## Cambios Implementados

### 1. **pom.xml (Fiscalizacion-web)**
Se agregó el plugin `jasperreports-maven-plugin` que:
- Precompila todos los archivos `.jrxml` → `.jasper` en tiempo de construcción
- Evita compilación dinámica en runtime
- Mejora el rendimiento al no compilar en cada solicitud

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>jasperreports-maven-plugin</artifactId>
    <version>2.1</version>
    <executions>
        <execution>
            <phase>process-resources</phase>
            <goals>
                <goal>compile-reports</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <sourceDirectory>src/main/webapp/resources/jrxml</sourceDirectory>
        <outputDirectory>${project.build.directory}/${project.build.finalName}/resources/jrxml</outputDirectory>
        <compiler>net.sf.jasperreports.compilers.JRGroovyCompiler</compiler>
    </configuration>
    ...
</plugin>
```

### 2. **JasperReportUtil.java**
Se mejoró el método `fillReport()` para:
- Buscar primero archivos `.jasper` precompilados
- Usar esos en lugar de compilar desde `.jrxml`
- Si no encuentra `.jasper`, intenta compilar como fallback con mejor manejo de errores

```java
// Ahora prefiere archivos .jasper precompilados
if (pathJasper.toLowerCase().endsWith(".jrxml")) {
    String jasperPath = pathJasper.replaceAll("(?i)\\.jrxml$", ".jasper");
    if (new java.io.File(jasperPath).exists()) {
        return JasperFillManager.fillReport(jasperPath, map, conn);
    }
    // Fallback: compilar si no existe precompilado
    ...
}
```

---

## Pasos para Aplicar la Solución

### Paso 1: Limpiar y Compilar
```bash
cd Fiscalizacion-web
mvn clean install
```

El plugin `jasperreports-maven-plugin` compilará automáticamente:
- Origen: `src/main/webapp/resources/jrxml/*.jrxml`
- Destino: `target/Fiscalizacion-web-1.0/resources/jrxml/*.jasper`

### Paso 2: Desplegar en GlassFish
```bash
asadmin deploy --force target/Fiscalizacion-web-1.0.war
```

### Paso 3: Verificar
Después de desplegar, los reportes deberían funcionar sin el error `Error loading expression class`.

---

## Beneficios de Esta Solución

✅ **Elimina compilación dinámica en runtime** → No necesita compilador en GlassFish  
✅ **Mejora el rendimiento** → Los reportes ya están compilados  
✅ **Mayor estabilidad** → Errores de compilación se detectan en build time, no runtime  
✅ **Compatible con contenedores** → Funciona en ambientes sin compilador Java  

---

## Alternativa: Si Aún Hay Problemas

Si después de compilar sigues viendo errores de compilación, verifica:

1. **Que los archivos `.jasper` se generaron:**
   ```bash
   ls target/Fiscalizacion-web-1.0/resources/jrxml/*.jasper
   ```

2. **Que GlassFish tenga permisos de lectura** en el directorio `/resources/jrxml/`

3. **Que no haya caracteres especiales** en los nombres de parámetros de los reportes JRXML

---

## Referencia de Archivos Modificados

- `Fiscalizacion-web/pom.xml` - Plugin Maven agregado
- `Fiscalizacion-web/src/main/java/gob/igm/ec/util/JasperReportUtil.java` - Método `fillReport()` mejorado

