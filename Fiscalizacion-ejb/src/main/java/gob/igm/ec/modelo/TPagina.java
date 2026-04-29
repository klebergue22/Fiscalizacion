/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VERA_MAYRA
 */
@Entity
@Table(name = "T_PAGINA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPagina.findAll", query = "SELECT t FROM TPagina t"),
    @NamedQuery(name = "TPagina.findByIdPagina", query = "SELECT t FROM TPagina t WHERE t.idPagina = :idPagina"),
    @NamedQuery(name = "TPagina.findByNombre", query = "SELECT t FROM TPagina t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TPagina.findByUrl", query = "SELECT t FROM TPagina t WHERE t.url = :url"),
    @NamedQuery(name = "TPagina.findByFechaModificacion", query = "SELECT t FROM TPagina t WHERE t.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "TPagina.findByVersion", query = "SELECT t FROM TPagina t WHERE t.version = :version")})
public class TPagina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGINA")
    private Long idPagina;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 500)
    @Column(name = "URL")
    private String url;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tPagina")
    private List<TPaginaperfil> tPaginaperfilList;
    @OneToMany(mappedBy = "idPagina")
    private List<TMenu> tMenuList;

    public TPagina() {
    }

    public TPagina(Long idPagina) {
        this.idPagina = idPagina;
    }

    public TPagina(Long idPagina, BigInteger version) {
        this.idPagina = idPagina;
        this.version = version;
    }

    public Long getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Long idPagina) {
        this.idPagina = idPagina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @XmlTransient
    public List<TPaginaperfil> getTPaginaperfilList() {
        return tPaginaperfilList;
    }

    public void setTPaginaperfilList(List<TPaginaperfil> tPaginaperfilList) {
        this.tPaginaperfilList = tPaginaperfilList;
    }

    @XmlTransient
    public List<TMenu> getTMenuList() {
        return tMenuList;
    }

    public void setTMenuList(List<TMenu> tMenuList) {
        this.tMenuList = tMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagina != null ? idPagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPagina)) {
            return false;
        }
        TPagina other = (TPagina) object;
        if ((this.idPagina == null && other.idPagina != null) || (this.idPagina != null && !this.idPagina.equals(other.idPagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TPagina[ idPagina=" + idPagina + " ]";
    }
    
}
