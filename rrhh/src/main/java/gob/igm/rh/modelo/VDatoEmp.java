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
@Table(name = "V_DATO_EMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VDatoEmp.findAll", query = "SELECT v FROM VDatoEmp v")
    , @NamedQuery(name = "VDatoEmp.findByNoPersona", query = "SELECT v FROM VDatoEmp v WHERE v.noPersona = :noPersona")
    , @NamedQuery(name = "VDatoEmp.findByNombreC", query = "SELECT v FROM VDatoEmp v WHERE v.nombreC = :nombreC")
    , @NamedQuery(name = "VDatoEmp.findByCodigo", query = "SELECT v FROM VDatoEmp v WHERE v.codigo = :codigo")
    , @NamedQuery(name = "VDatoEmp.findByNoCedula", query = "SELECT v FROM VDatoEmp v WHERE v.noCedula = :noCedula")})
public class VDatoEmp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_PERSONA")
    private int noPersona;
    @Size(max = 81)
    @Column(name = "NOMBRE_C")
    private String nombreC;
    @Size(max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NO_CEDULA")
    private String noCedula;

    public VDatoEmp() {
    }

    public int getNoPersona() {
        return noPersona;
    }

    public void setNoPersona(int noPersona) {
        this.noPersona = noPersona;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNoCedula() {
        return noCedula;
    }

    public void setNoCedula(String noCedula) {
        this.noCedula = noCedula;
    }
    
}
