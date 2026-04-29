/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VERA_MAYRA
 */
@Entity
@Table(name = "T_DIGNIDAD_X_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDignidadXUsuario.findAll", query = "SELECT t FROM TDignidadXUsuario t"),
    @NamedQuery(name = "TDignidadXUsuario.findById", query = "SELECT t FROM TDignidadXUsuario t WHERE t.id = :id"),
    @NamedQuery(name = "TDignidadXUsuario.findByDignidad", query = "SELECT t FROM TDignidadXUsuario t WHERE t.dignidad = :dignidad")})
public class TDignidadXUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID",nullable = false)
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "DIGNIDAD")
    private String dignidad;
    @JoinColumn(name = "ID_DIGNIDAD", referencedColumnName = "COD_DIGNIDAD")
    @ManyToOne
    private TDignidad idDignidad;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private TUsuarios idUsuario;

    public TDignidadXUsuario() {
    }

    public TDignidadXUsuario(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDignidad() {
        return dignidad;
    }

    public void setDignidad(String dignidad) {
        this.dignidad = dignidad;
    }

    public TDignidad getIdDignidad() {
        return idDignidad;
    }

    public void setIdDignidad(TDignidad idDignidad) {
        this.idDignidad = idDignidad;
    }

    public TUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TDignidadXUsuario)) {
            return false;
        }
        TDignidadXUsuario other = (TDignidadXUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TDignidadXUsuario[ id=" + id + " ]";
    }
    
}
