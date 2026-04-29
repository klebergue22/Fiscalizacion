/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author VERA_MAYRA
 */
@Embeddable
public class TPaginaperfilPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGINA")
    private long idPagina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERFIL")
    private long idPerfil;

    public TPaginaperfilPK() {
    }

    public TPaginaperfilPK(long idPagina, long idPerfil) {
        this.idPagina = idPagina;
        this.idPerfil = idPerfil;
    }

    public long getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(long idPagina) {
        this.idPagina = idPagina;
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(long idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPagina;
        hash += (int) idPerfil;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPaginaperfilPK)) {
            return false;
        }
        TPaginaperfilPK other = (TPaginaperfilPK) object;
        if (this.idPagina != other.idPagina) {
            return false;
        }
        if (this.idPerfil != other.idPerfil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TPaginaperfilPK[ idPagina=" + idPagina + ", idPerfil=" + idPerfil + " ]";
    }
    
}
