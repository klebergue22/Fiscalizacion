/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VERA_MAYRA
 */
@Entity
@Table(name = "T_PAGINAPERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPaginaperfil.findAll", query = "SELECT t FROM TPaginaperfil t"),
    @NamedQuery(name = "TPaginaperfil.findByIdPagina", query = "SELECT t FROM TPaginaperfil t WHERE t.tPaginaperfilPK.idPagina = :idPagina"),
    @NamedQuery(name = "TPaginaperfil.findByIdPerfil", query = "SELECT t FROM TPaginaperfil t WHERE t.tPaginaperfilPK.idPerfil = :idPerfil"),
    @NamedQuery(name = "TPaginaperfil.findByVersion", query = "SELECT t FROM TPaginaperfil t WHERE t.version = :version")})
public class TPaginaperfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TPaginaperfilPK tPaginaperfilPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "ID_PAGINA", referencedColumnName = "ID_PAGINA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TPagina tPagina;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "IDPERFIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TPerfil tPerfil;

    public TPaginaperfil() {
    }

    public TPaginaperfil(TPaginaperfilPK tPaginaperfilPK) {
        this.tPaginaperfilPK = tPaginaperfilPK;
    }

    public TPaginaperfil(TPaginaperfilPK tPaginaperfilPK, BigInteger version) {
        this.tPaginaperfilPK = tPaginaperfilPK;
        this.version = version;
    }

    public TPaginaperfil(long idPagina, long idPerfil) {
        this.tPaginaperfilPK = new TPaginaperfilPK(idPagina, idPerfil);
    }

    public TPaginaperfilPK getTPaginaperfilPK() {
        return tPaginaperfilPK;
    }

    public void setTPaginaperfilPK(TPaginaperfilPK tPaginaperfilPK) {
        this.tPaginaperfilPK = tPaginaperfilPK;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public TPagina getTPagina() {
        return tPagina;
    }

    public void setTPagina(TPagina tPagina) {
        this.tPagina = tPagina;
    }

    public TPerfil getTPerfil() {
        return tPerfil;
    }

    public void setTPerfil(TPerfil tPerfil) {
        this.tPerfil = tPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tPaginaperfilPK != null ? tPaginaperfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPaginaperfil)) {
            return false;
        }
        TPaginaperfil other = (TPaginaperfil) object;
        if ((this.tPaginaperfilPK == null && other.tPaginaperfilPK != null) || (this.tPaginaperfilPK != null && !this.tPaginaperfilPK.equals(other.tPaginaperfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TPaginaperfil[ tPaginaperfilPK=" + tPaginaperfilPK + " ]";
    }
    
}
