/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.igm.ec.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "T_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUsuarios.findAll", query = "SELECT t FROM TUsuarios t order by t.nombreCompleto"),
    @NamedQuery(name = "TUsuarios.findByIdUsuario", query = "SELECT t FROM TUsuarios t WHERE t.idUsuario = :idUsuario"),
    @NamedQuery(name = "TUsuarios.findByUsuario", query = "SELECT t FROM TUsuarios t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "TUsuarios.findByPassword", query = "SELECT t FROM TUsuarios t WHERE t.password = :password"),
    @NamedQuery(name = "TUsuarios.findByNombreCompleto", query = "SELECT t FROM TUsuarios t WHERE t.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "TUsuarios.findByDignidad", query = "SELECT t FROM TUsuarios t WHERE t.dignidad = :dignidad"),
    @NamedQuery(name = "TUsuarios.findByBodega", query = "SELECT t FROM TUsuarios t WHERE t.bodega = :bodega"),
    @NamedQuery(name = "TUsuarios.findByPerfil", query = "SELECT t FROM TUsuarios t WHERE t.perfil = :perfil")})
public class TUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "ID_USUARIO", nullable = false)
    private BigDecimal idUsuario;
    
    @Size(max = 20)
    @Column(name = "USUARIO")
    private String usuario;
    
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 250)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    @Size(max = 100)
    @Column(name = "DIGNIDAD")
    private String dignidad;
    @Column(name = "BODEGA")
    private BigInteger bodega;
    
    @Column(name = "ID_DIGNIDAD")
    private BigInteger idDignidad;
    @Column(name = "ESTADO")
    private BigInteger estado;
        
    @Size(max = 20)
    @Column(name = "PERFIL")
    private String perfil;
    
    
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.PERSIST)
    private List<TDignidadXUsuario> tDignidadXUsuarioList;
    
    /*
    @JoinColumn(name = "ID_DIGNIDAD", referencedColumnName = "COD_DIGNIDAD")
    @ManyToOne
    private TDignidad idDignidad;
    */

    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "IDPERFIL")
    @ManyToOne
    private TPerfil idPerfil;

    public TUsuarios() {
    }

    public TUsuarios(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TUsuarios(BigDecimal idUsuario, String password) {
        this.idUsuario = idUsuario;
        this.password = password;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDignidad() {
        return dignidad;
    }

    public void setDignidad(String dignidad) {
        this.dignidad = dignidad;
    }

    public BigInteger getBodega() {
        return bodega;
    }

    public void setBodega(BigInteger bodega) {
        this.bodega = bodega;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @XmlTransient
    public List<TDignidadXUsuario> getTDignidadXUsuarioList() {
        return tDignidadXUsuarioList;
    }

    public void setTDignidadXUsuarioList(List<TDignidadXUsuario> tDignidadXUsuarioList) {
        this.tDignidadXUsuarioList = tDignidadXUsuarioList;
    }



    public BigInteger getIdDignidad() {
        return idDignidad;
    }

    /*
    public TDignidad getIdDignidad() {
    return idDignidad;
    }
    public void setIdDignidad(TDignidad idDignidad) {
    this.idDignidad = idDignidad;
    }
     */
    public void setIdDignidad(BigInteger idDignidad) {
        this.idDignidad = idDignidad;
    }

    public TPerfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(TPerfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TUsuarios)) {
            return false;
        }
        TUsuarios other = (TUsuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TUsuarios[ idUsuario=" + idUsuario + " ]";
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }


    
    

}
