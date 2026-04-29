/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

// ESTA ES UNA PRUEBA DE VERSION

@Entity
@Table(name = "SOLICITUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s")
    , @NamedQuery(name = "Solicitud.findByIdSolicitud", query = "SELECT s FROM Solicitud s WHERE s.idSolicitud = :idSolicitud")
    , @NamedQuery(name = "Solicitud.findByNombreProyectoSolicitud", query = "SELECT s FROM Solicitud s WHERE s.nombreProyectoSolicitud = :nombreProyectoSolicitud")
    , @NamedQuery(name = "Solicitud.findByFechaAceptacion", query = "SELECT s FROM Solicitud s WHERE s.fechaAceptacion = :fechaAceptacion")
    , @NamedQuery(name = "Solicitud.findByFechaContrato", query = "SELECT s FROM Solicitud s WHERE s.fechaContrato = :fechaContrato")
    , @NamedQuery(name = "Solicitud.findByValorContrato", query = "SELECT s FROM Solicitud s WHERE s.valorContrato = :valorContrato")
    , @NamedQuery(name = "Solicitud.findByFechaAnticipo", query = "SELECT s FROM Solicitud s WHERE s.fechaAnticipo = :fechaAnticipo")
    , @NamedQuery(name = "Solicitud.findByValorAnticipo", query = "SELECT s FROM Solicitud s WHERE s.valorAnticipo = :valorAnticipo")
    , @NamedQuery(name = "Solicitud.findByObservaciones", query = "SELECT s FROM Solicitud s WHERE s.observaciones = :observaciones")})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SOLICITUD")
    private BigDecimal idSolicitud;
    @Size(max = 1000)
    @Column(name = "NOMBRE_PROYECTO_SOLICITUD")
    private String nombreProyectoSolicitud;
    @Column(name = "FECHA_ACEPTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAceptacion;
    @Column(name = "FECHA_CONTRATO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContrato;
    @Column(name = "VALOR_CONTRATO")
    private BigInteger valorContrato;
    @Column(name = "FECHA_ANTICIPO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnticipo;
    @Column(name = "VALOR_ANTICIPO")
    private BigInteger valorAnticipo;
    @Size(max = 1000)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @JoinColumn(name = "ID_EST_SOLICITUD", referencedColumnName = "ID_EST_SOLICITUD")
    @ManyToOne(optional = false)
    private EstadoSolicitud idEstSolicitud;

    public Solicitud() {
    }

    public Solicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreProyectoSolicitud() {
        return nombreProyectoSolicitud;
    }

    public void setNombreProyectoSolicitud(String nombreProyectoSolicitud) {
        this.nombreProyectoSolicitud = nombreProyectoSolicitud;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public BigInteger getValorContrato() {
        return valorContrato;
    }

    public void setValorContrato(BigInteger valorContrato) {
        this.valorContrato = valorContrato;
    }

    public Date getFechaAnticipo() {
        return fechaAnticipo;
    }

    public void setFechaAnticipo(Date fechaAnticipo) {
        this.fechaAnticipo = fechaAnticipo;
    }

    public BigInteger getValorAnticipo() {
        return valorAnticipo;
    }

    public void setValorAnticipo(BigInteger valorAnticipo) {
        this.valorAnticipo = valorAnticipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoSolicitud getIdEstSolicitud() {
        return idEstSolicitud;
    }

    public void setIdEstSolicitud(EstadoSolicitud idEstSolicitud) {
        this.idEstSolicitud = idEstSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.Solicitud[ idSolicitud=" + idSolicitud + " ]";
    }
    
}
