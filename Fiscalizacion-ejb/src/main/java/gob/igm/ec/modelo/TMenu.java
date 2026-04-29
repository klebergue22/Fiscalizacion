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
@Table(name = "T_MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMenu.findAll", query = "SELECT t FROM TMenu t"),
    @NamedQuery(name = "TMenu.findByIdMenu", query = "SELECT t FROM TMenu t WHERE t.idMenu = :idMenu"),
    @NamedQuery(name = "TMenu.findByNombre", query = "SELECT t FROM TMenu t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TMenu.findByOrden", query = "SELECT t FROM TMenu t WHERE t.orden = :orden"),
    @NamedQuery(name = "TMenu.findByVersion", query = "SELECT t FROM TMenu t WHERE t.version = :version")})
public class TMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MENU")
    private Long idMenu;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Size(max = 10)
    @Column(name = "VERSION")
    private String version;
    @OneToMany(mappedBy = "idMenuPadre")
    private List<TMenu> tMenuList;
    @JoinColumn(name = "ID_MENU_PADRE", referencedColumnName = "ID_MENU")
    @ManyToOne
    private TMenu idMenuPadre;
    @JoinColumn(name = "ID_PAGINA", referencedColumnName = "ID_PAGINA")
    
    @ManyToOne
    private TPagina idPagina;

    public TMenu() {
    }

    public TMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public TMenu(Long idMenu, BigInteger orden) {
        this.idMenu = idMenu;
        this.orden = orden;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlTransient
    public List<TMenu> getTMenuList() {
        return tMenuList;
    }

    public void setTMenuList(List<TMenu> tMenuList) {
        this.tMenuList = tMenuList;
    }

    public TMenu getIdMenuPadre() {
        return idMenuPadre;
    }

    public void setIdMenuPadre(TMenu idMenuPadre) {
        this.idMenuPadre = idMenuPadre;
    }

    public TPagina getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(TPagina idPagina) {
        this.idPagina = idPagina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMenu)) {
            return false;
        }
        TMenu other = (TMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.igm.ec.modelo.TMenu[ idMenu=" + idMenu + " ]";
    }
    
}
