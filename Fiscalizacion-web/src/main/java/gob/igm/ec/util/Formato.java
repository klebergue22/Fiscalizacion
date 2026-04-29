/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.util;

import java.util.TimeZone;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author VERA_MAYRA
 */
@Named(value = "formato")
@ApplicationScoped
public class Formato {

    /**
     * La variable formatoFechaHora.
     */
    private String formatoFechaHora = "dd/MM/yyyy HH:mm";
    /**
     * La variable formatoFecha.
     */
    private String formatoFecha = "dd/MM/yyyy";

    /**
     * La variable timeZone.
     */
    private TimeZone timeZone;

    /**
     * Inicializa las variables de clase.
     */
    public Formato() {
        this.timeZone = TimeZone.getTimeZone("GMT-5");
    }

    /**
     * Retorna el valor FormatoFechaHora.
     *
     * @return El formato fecha hora
     */
    public String getFormatoFechaHora() {
        return formatoFechaHora;
    }

    /**
     * Ingresa el valor de FormatoFechaHora.
     *
     * @param formatoFechaHora la formato fecha hora
     */
    public void setFormatoFechaHora(String formatoFechaHora) {
        this.formatoFechaHora = formatoFechaHora;
    }

    /**
     * Retorna el valor FormatoFecha.
     *
     * @return El formato fecha
     */
    public String getFormatoFecha() {
        return formatoFecha;
    }

    /**
     * Ingresa el valor de FormatoFecha.
     *
     * @param formatoFecha la formato fecha
     */
    public void setFormatoFecha(String formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    /**
     * Obtiene el valor TimeZone.
     *
     * @return the time zone
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Ingresa el valor TimeZone.
     *
     * @param timeZone nuevo valor para time zone
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
