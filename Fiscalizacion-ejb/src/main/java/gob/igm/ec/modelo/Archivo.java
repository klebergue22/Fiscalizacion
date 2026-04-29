/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "ARCHIVO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivo.findAll", query = "SELECT a FROM Archivo a")
    , @NamedQuery(name = "Archivo.findByIdArchivo", query = "SELECT a FROM Archivo a WHERE a.idArchivo = :idArchivo")
    , @NamedQuery(name = "Archivo.findByFechaRegistro", query = "SELECT a FROM Archivo a WHERE a.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Archivo.findByUrlAcceso", query = "SELECT a FROM Archivo a WHERE a.urlAcceso = :urlAcceso")
    , @NamedQuery(name = "Archivo.findByNombreArchivo", query = "SELECT a FROM Archivo a WHERE a.nombreArchivo = :nombreArchivo")})
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)    
    @Column(name = "ID_ARCHIVO")
    private BigDecimal idArchivo;
    
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Size(max = 300)
    @Column(name = "URL_ACCESO")
    private String urlAcceso;
    @Size(max = 50)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    
    @Lob
    @Column(name = "ARCHIVOPDF")
    private byte[] archivopdf;

    public Archivo() {
    }

    public Archivo(BigDecimal idArchivo) {
        this.idArchivo = idArchivo;
    }

    public BigDecimal getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(BigDecimal idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getArchivopdf() {
        return archivopdf;
    }

    public void setArchivopdf(byte[] archivopdf) {
        this.archivopdf = archivopdf;
    }
    
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivo != null ? idArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archivo)) {
            return false;
        }
        Archivo other = (Archivo) object;
        if ((this.idArchivo == null && other.idArchivo != null) || (this.idArchivo != null && !this.idArchivo.equals(other.idArchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.Archivo[ idArchivo=" + idArchivo + " ]";
    }
    
}
