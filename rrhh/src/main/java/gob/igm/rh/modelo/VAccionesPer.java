/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.rh.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alexander Jimenez
 */
@Entity
@Table(name = "V_ACCIONES_PER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VAccionesPer.findAll", query = "SELECT v FROM VAccionesPer v")
    , @NamedQuery(name = "VAccionesPer.findByNoPersona", query = "SELECT v FROM VAccionesPer v WHERE v.noPersona = :noPersona")
    , @NamedQuery(name = "VAccionesPer.findByNombreC", query = "SELECT v FROM VAccionesPer v WHERE v.nombreC = :nombreC")
    , @NamedQuery(name = "VAccionesPer.findByFechaResolucion", query = "SELECT v FROM VAccionesPer v WHERE v.fechaResolucion = :fechaResolucion")
    , @NamedQuery(name = "VAccionesPer.findByFechaRige", query = "SELECT v FROM VAccionesPer v WHERE v.fechaRige = :fechaRige")
    , @NamedQuery(name = "VAccionesPer.findByNoAccion", query = "SELECT v FROM VAccionesPer v WHERE v.noAccion = :noAccion")})

public class VAccionesPer implements Serializable {

    private static final long serialVersionUID = 1L;
  
    @Column(name = "NO_PERSONA")
    private Integer noPersona;
    @Size(max = 81)
    @Column(name = "NOMBRE_C")
    private String nombreC;
    @Column(name = "FECHA_RESOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResolucion;
    @Column(name = "FECHA_RIGE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRige;
    
    @Basic(optional = false)
    @NotNull
    @Id
    @Column(name = "NO_ACCION")
    private long noAccion;

    public VAccionesPer() {
    }

    public Integer getNoPersona() {
        return noPersona;
    }

    public void setNoPersona(Integer noPersona) {
        this.noPersona = noPersona;
    }


    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Date getFechaRige() {
        return fechaRige;
    }

    public void setFechaRige(Date fechaRige) {
        this.fechaRige = fechaRige;
    }

    public long getNoAccion() {
        return noAccion;
    }

    public void setNoAccion(long noAccion) {
        this.noAccion = noAccion;
    }
    
}
