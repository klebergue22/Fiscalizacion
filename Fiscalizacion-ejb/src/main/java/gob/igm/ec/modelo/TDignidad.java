/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VERA_MAYRA
 */
@Entity
@Table(name = "T_DIGNIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDignidad.findAll", query = "SELECT t FROM TDignidad t order by t.codDignidad desc"),
    @NamedQuery(name = "TDignidad.findByCodDignidad", query = "SELECT t FROM TDignidad t WHERE t.codDignidad = :codDignidad"),
    @NamedQuery(name = "TDignidad.findByDignidad", query = "SELECT t FROM TDignidad t WHERE t.dignidad = :dignidad"),
    @NamedQuery(name = "TDignidad.findByAlias", query = "SELECT t FROM TDignidad t WHERE t.alias = :alias"),
    @NamedQuery(name = "TDignidad.findByNivel", query = "SELECT t FROM TDignidad t WHERE t.nivel = :nivel"),
    @NamedQuery(name = "TDignidad.findByCantidadPapel", query = "SELECT t FROM TDignidad t WHERE t.cantidadPapel = :cantidadPapel")})
public class TDignidad implements Serializable {
    @Column(name = "ORD_DIGNIDAD")
    private BigInteger ordDignidad;
    
    ///////// SE COMENTA RELACIONES ///////////////////////
//    @OneToMany(mappedBy = "codDignidad")
//    private List<TProduccionDisenio> tProduccionDisenioList;

    @Size(max = 20)
    @Column(name = "FORMATO")
    private String formato;
    
//    @OneToMany(mappedBy = "codDignidad")
//    private List<TProduccionPapeletas> tProduccionPapeletasList;
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "COD_DIGNIDAD", nullable = false)
    private Integer codDignidad;
    @Size(max = 100)
    @Column(name = "DIGNIDAD")
    private String dignidad;
    @Size(max = 50)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 100)
    @Column(name = "NIVEL")
    private String nivel;
    @Column(name = "CANTIDAD_PAPEL")
    private Integer cantidadPapel;
    @OneToMany(mappedBy = "idDignidad")
    private List<TDignidadXUsuario> tDignidadXUsuarioList;
    
    /*
    @OneToMany(mappedBy = "idDignidad")
    private List<TUsuarios> tUsuariosList;
    */


    public TDignidad() {
    }

    public TDignidad(Integer codDignidad) {
        this.codDignidad = codDignidad;
    }

    public Integer getCodDignidad() {
        return codDignidad;
    }

    public void setCodDignidad(Integer codDignidad) {
        this.codDignidad = codDignidad;
    }

    public String getDignidad() {
        return dignidad;
    }

    public void setDignidad(String dignidad) {
        this.dignidad = dignidad;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getCantidadPapel() {
        return cantidadPapel;
    }

    public void setCantidadPapel(Integer cantidadPapel) {
        this.cantidadPapel = cantidadPapel;
    }

    @XmlTransient
    public List<TDignidadXUsuario> getTDignidadXUsuarioList() {
        return tDignidadXUsuarioList;
    }

    public void setTDignidadXUsuarioList(List<TDignidadXUsuario> tDignidadXUsuarioList) {
        this.tDignidadXUsuarioList = tDignidadXUsuarioList;
    }
    
    /*
    
    @XmlTransient
    public List<TUsuarios> getTUsuariosList() {
        return tUsuariosList;
    }

    public void setTUsuariosList(List<TUsuarios> tUsuariosList) {
        this.tUsuariosList = tUsuariosList;
    }

    */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDignidad != null ? codDignidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDignidad)) {
            return false;
        }
        TDignidad other = (TDignidad) object;
        if ((this.codDignidad == null && other.codDignidad != null) || (this.codDignidad != null && !this.codDignidad.equals(other.codDignidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TDignidad[ codDignidad=" + codDignidad + " ]";
    }

//    @XmlTransient
//    public List<TProduccionPapeletas> getTProduccionPapeletasList() {
//        return tProduccionPapeletasList;
//    }
//
//    public void setTProduccionPapeletasList(List<TProduccionPapeletas> tProduccionPapeletasList) {
//        this.tProduccionPapeletasList = tProduccionPapeletasList;
//    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public BigInteger getOrdDignidad() {
        return ordDignidad;
    }

    public void setOrdDignidad(BigInteger ordDignidad) {
        this.ordDignidad = ordDignidad;
    }

//    @XmlTransient
//    public List<TProduccionDisenio> getTProduccionDisenioList() {
//        return tProduccionDisenioList;
//    }
//
//    public void setTProduccionDisenioList(List<TProduccionDisenio> tProduccionDisenioList) {
//        this.tProduccionDisenioList = tProduccionDisenioList;
//    }

}
