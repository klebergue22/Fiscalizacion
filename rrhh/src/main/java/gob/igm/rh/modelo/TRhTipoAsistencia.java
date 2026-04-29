/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.rh.modelo;

import java.io.Serializable;
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
@Table(name = "T_RH_TIPO_ASISTENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TRhTipoAsistencia.findAll", query = "SELECT t FROM TRhTipoAsistencia t")
    , @NamedQuery(name = "TRhTipoAsistencia.findByNoAsist", query = "SELECT t FROM TRhTipoAsistencia t WHERE t.noAsist = :noAsist")
    , @NamedQuery(name = "TRhTipoAsistencia.findByDescrip", query = "SELECT t FROM TRhTipoAsistencia t WHERE t.descrip = :descrip")
    , @NamedQuery(name = "TRhTipoAsistencia.findByNoAsistAux", query = "SELECT t FROM TRhTipoAsistencia t WHERE t.noAsistAux = :noAsistAux")})
public class TRhTipoAsistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_ASIST")
    private long noAsist;
    @Size(max = 50)
    @Column(name = "DESCRIP")
    private String descrip;
    @Column(name = "NO_ASIST_AUX")
    private long noAsistAux;

    public TRhTipoAsistencia() {
    }

    public long getNoAsist() {
        return noAsist;
    }

    public void setNoAsist(long noAsist) {
        this.noAsist = noAsist;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public long getNoAsistAux() {
        return noAsistAux;
    }

    public void setNoAsistAux(long noAsistAux) {
        this.noAsistAux = noAsistAux;
    }

    

    @Override
    public String toString() {
        return "gob.igm.rh.modelo.TRhTipoAsistencia[ noAsist=" + noAsist + " ]";
    }
    
}
