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
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VERA_MAYRA
 */
@Entity
@Table(name = "T_PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPerfil.findAll", query = "SELECT t FROM TPerfil t"),
    @NamedQuery(name = "TPerfil.findByIdperfil", query = "SELECT t FROM TPerfil t WHERE t.idperfil = :idperfil"),
    @NamedQuery(name = "TPerfil.findByDescripcion", query = "SELECT t FROM TPerfil t WHERE t.descripcion = :descripcion")})
public class TPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPERFIL")
    private BigDecimal idperfil;
    @Size(max = 25)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    ///////// SE COMENTA ESTE RELACION ////////////////////
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "tPerfil")
    //private List<TPaginaperfil> tPaginaperfilList;

    public TPerfil() {
    }

    public TPerfil(BigDecimal idperfil) {
        this.idperfil = idperfil;
    }

    public BigDecimal getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(BigDecimal idperfil) {
        this.idperfil = idperfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    @XmlTransient
//    public List<TPaginaperfil> getTPaginaperfilList() {
//        return tPaginaperfilList;
//    }
//
//    public void setTPaginaperfilList(List<TPaginaperfil> tPaginaperfilList) {
//        this.tPaginaperfilList = tPaginaperfilList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperfil != null ? idperfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPerfil)) {
            return false;
        }
        TPerfil other = (TPerfil) object;
        if ((this.idperfil == null && other.idperfil != null) || (this.idperfil != null && !this.idperfil.equals(other.idperfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TPerfil[ idperfil=" + idperfil + " ]";
    }
    
}
