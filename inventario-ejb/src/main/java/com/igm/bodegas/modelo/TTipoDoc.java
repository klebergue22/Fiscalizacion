/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igm.bodegas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alexander Jimenez
 */
@Entity
@Table(name = "T_TIPO_DOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TTipoDoc.findAll", query = "SELECT t FROM TTipoDoc t")
    , @NamedQuery(name = "TTipoDoc.findByIdtipodoc", query = "SELECT t FROM TTipoDoc t WHERE t.idtipodoc = :idtipodoc")
    , @NamedQuery(name = "TTipoDoc.findByNombreDoc", query = "SELECT t FROM TTipoDoc t WHERE t.nombreDoc = :nombreDoc")})
public class TTipoDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPODOC")
    private BigDecimal idtipodoc;
    @Size(max = 100)
    @Column(name = "NOMBRE_DOC")
    private String nombreDoc;

    public TTipoDoc() {
    }

    public TTipoDoc(BigDecimal idtipodoc) {
        this.idtipodoc = idtipodoc;
    }

    public BigDecimal getIdtipodoc() {
        return idtipodoc;
    }

    public void setIdtipodoc(BigDecimal idtipodoc) {
        this.idtipodoc = idtipodoc;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipodoc != null ? idtipodoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TTipoDoc)) {
            return false;
        }
        TTipoDoc other = (TTipoDoc) object;
        if ((this.idtipodoc == null && other.idtipodoc != null) || (this.idtipodoc != null && !this.idtipodoc.equals(other.idtipodoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.igm.bodegas.modelo.TTipoDoc[ idtipodoc=" + idtipodoc + " ]";
    }
    
}
