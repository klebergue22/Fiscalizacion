/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Alexander Jimenez
 */
@Entity
@Table(name = "ESTADO_SOLICITUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoSolicitud.findAll", query = "SELECT e FROM EstadoSolicitud e")
    , @NamedQuery(name = "EstadoSolicitud.findByIdEstSolicitud", query = "SELECT e FROM EstadoSolicitud e WHERE e.idEstSolicitud = :idEstSolicitud")
    , @NamedQuery(name = "EstadoSolicitud.findByDescripcion", query = "SELECT e FROM EstadoSolicitud e WHERE e.descripcion = :descripcion")})
public class EstadoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EST_SOLICITUD")
    private BigDecimal idEstSolicitud;
    
    @Size(max = 300)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @OneToMany(mappedBy = "idEstSolicitud")
    private List<Solicitud> solicitudLista;

    public EstadoSolicitud() {
    }

    public EstadoSolicitud(BigDecimal idEstSolicitud) {
        this.idEstSolicitud = idEstSolicitud;
    }

    public BigDecimal getIdEstSolicitud() {
        return idEstSolicitud;
    }

    public void setIdEstSolicitud(BigDecimal idEstSolicitud) {
        this.idEstSolicitud = idEstSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Solicitud> getSolicitudLista() {
        return solicitudLista;
    }

    public void setSolicitudLista(List<Solicitud> solicitudLista) {
        this.solicitudLista = solicitudLista;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstSolicitud != null ? idEstSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSolicitud)) {
            return false;
        }
        EstadoSolicitud other = (EstadoSolicitud) object;
        if ((this.idEstSolicitud == null && other.idEstSolicitud != null) || (this.idEstSolicitud != null && !this.idEstSolicitud.equals(other.idEstSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.EstadoSolicitud[ idEstSolicitud=" + idEstSolicitud + " ]";
    }
    
}
