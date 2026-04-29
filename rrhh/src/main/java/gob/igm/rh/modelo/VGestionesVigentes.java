/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.rh.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alexander Jimenez
 */
@Entity
@Table(name = "V_GESTIONES_VIGENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VGestionesVigentes.findAll", query = "SELECT v FROM VGestionesVigentes v ORDER BY v.noCd")
    , @NamedQuery(name = "VGestionesVigentes.findByNoCd", query = "SELECT v FROM VGestionesVigentes v WHERE v.noCd = :noCd")
    , @NamedQuery(name = "VGestionesVigentes.findByDescrip", query = "SELECT v FROM VGestionesVigentes v WHERE v.descrip = :descrip")})
public class VGestionesVigentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NO_CD")
    private Short noCd;
    @Size(max = 200)
    @Column(name = "DESCRIP")
    private String descrip;

    public VGestionesVigentes() {
    }

    public Short getNoCd() {
        return noCd;
    }

    public void setNoCd(Short noCd) {
        this.noCd = noCd;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    
}
