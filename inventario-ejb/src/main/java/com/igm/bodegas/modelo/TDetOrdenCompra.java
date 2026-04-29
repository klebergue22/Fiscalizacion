/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igm.bodegas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "T_DET_ORDEN_COMPRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDetOrdenCompra.findAll", query = "SELECT t FROM TDetOrdenCompra t")
    , @NamedQuery(name = "TDetOrdenCompra.findByPeriodoCodigo", query = "SELECT t FROM TDetOrdenCompra t WHERE t.tDetOrdenCompraPK.periodoCodigo = :periodoCodigo")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoOrden", query = "SELECT t FROM TDetOrdenCompra t WHERE t.tDetOrdenCompraPK.noOrden = :noOrden")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoPedido", query = "SELECT t FROM TDetOrdenCompra t WHERE t.tDetOrdenCompraPK.noPedido = :noPedido")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoRegPedido", query = "SELECT t FROM TDetOrdenCompra t WHERE t.tDetOrdenCompraPK.noRegPedido = :noRegPedido")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoArticulo", query = "SELECT t FROM TDetOrdenCompra t WHERE t.noArticulo = :noArticulo")
    , @NamedQuery(name = "TDetOrdenCompra.findByCantParaCompra", query = "SELECT t FROM TDetOrdenCompra t WHERE t.cantParaCompra = :cantParaCompra")
    , @NamedQuery(name = "TDetOrdenCompra.findByVUnitario", query = "SELECT t FROM TDetOrdenCompra t WHERE t.vUnitario = :vUnitario")
    , @NamedQuery(name = "TDetOrdenCompra.findByVIvaUnit", query = "SELECT t FROM TDetOrdenCompra t WHERE t.vIvaUnit = :vIvaUnit")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoProveedor", query = "SELECT t FROM TDetOrdenCompra t WHERE t.noProveedor = :noProveedor")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoFactura", query = "SELECT t FROM TDetOrdenCompra t WHERE t.noFactura = :noFactura")
    , @NamedQuery(name = "TDetOrdenCompra.findByFechaFactura", query = "SELECT t FROM TDetOrdenCompra t WHERE t.fechaFactura = :fechaFactura")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoSerialFactura", query = "SELECT t FROM TDetOrdenCompra t WHERE t.noSerialFactura = :noSerialFactura")
    , @NamedQuery(name = "TDetOrdenCompra.findByNoAutorizaFact", query = "SELECT t FROM TDetOrdenCompra t WHERE t.noAutorizaFact = :noAutorizaFact")
    , @NamedQuery(name = "TDetOrdenCompra.findByFechaCaducidad", query = "SELECT t FROM TDetOrdenCompra t WHERE t.fechaCaducidad = :fechaCaducidad")
    , @NamedQuery(name = "TDetOrdenCompra.findByPerModifica", query = "SELECT t FROM TDetOrdenCompra t WHERE t.perModifica = :perModifica")
    , @NamedQuery(name = "TDetOrdenCompra.findByFechaModifica", query = "SELECT t FROM TDetOrdenCompra t WHERE t.fechaModifica = :fechaModifica")
    , @NamedQuery(name = "TDetOrdenCompra.findByIdTipodoc", query = "SELECT t FROM TDetOrdenCompra t WHERE t.idTipodoc = :idTipodoc")
    , @NamedQuery(name = "TDetOrdenCompra.findByFechaDoc", query = "SELECT t FROM TDetOrdenCompra t WHERE t.fechaDoc = :fechaDoc")
    , @NamedQuery(name = "TDetOrdenCompra.findByNumDocumento", query = "SELECT t FROM TDetOrdenCompra t WHERE t.numDocumento = :numDocumento")
    , @NamedQuery(name = "TDetOrdenCompra.findByDescripDoc", query = "SELECT t FROM TDetOrdenCompra t WHERE t.descripDoc = :descripDoc")})
public class TDetOrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TDetOrdenCompraPK tDetOrdenCompraPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_ARTICULO")
    private int noArticulo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANT_PARA_COMPRA")
    private BigDecimal cantParaCompra;
    @Column(name = "V_UNITARIO")
    private BigDecimal vUnitario;
    @Column(name = "V_IVA_UNIT")
    private BigDecimal vIvaUnit;
    @Column(name = "NO_PROVEEDOR")
    private Integer noProveedor;
    @Column(name = "NO_FACTURA")
    private Integer noFactura;
    @Column(name = "FECHA_FACTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;
    @Size(max = 6)
    @Column(name = "NO_SERIAL_FACTURA")
    private String noSerialFactura;
    @Size(max = 37)
    @Column(name = "NO_AUTORIZA_FACT")
    private String noAutorizaFact;
    @Column(name = "FECHA_CADUCIDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidad;
    @Column(name = "PER_MODIFICA")
    private BigInteger perModifica;
    @Column(name = "FECHA_MODIFICA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModifica;
    @Column(name = "ID_TIPODOC")
    private BigInteger idTipodoc;
    @Column(name = "FECHA_DOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDoc;
    @Column(name = "NUM_DOCUMENTO")
    private BigInteger numDocumento;
    @Size(max = 500)
    @Column(name = "DESCRIP_DOC")
    private String descripDoc;

    public TDetOrdenCompra() {
    }

    public TDetOrdenCompra(TDetOrdenCompraPK tDetOrdenCompraPK) {
        this.tDetOrdenCompraPK = tDetOrdenCompraPK;
    }

    public TDetOrdenCompra(TDetOrdenCompraPK tDetOrdenCompraPK, int noArticulo, BigDecimal cantParaCompra) {
        this.tDetOrdenCompraPK = tDetOrdenCompraPK;
        this.noArticulo = noArticulo;
        this.cantParaCompra = cantParaCompra;
    }

    public TDetOrdenCompra(short periodoCodigo, int noOrden, int noPedido, short noRegPedido) {
        this.tDetOrdenCompraPK = new TDetOrdenCompraPK(periodoCodigo, noOrden, noPedido, noRegPedido);
    }

    public TDetOrdenCompraPK getTDetOrdenCompraPK() {
        return tDetOrdenCompraPK;
    }

    public void setTDetOrdenCompraPK(TDetOrdenCompraPK tDetOrdenCompraPK) {
        this.tDetOrdenCompraPK = tDetOrdenCompraPK;
    }

    public int getNoArticulo() {
        return noArticulo;
    }

    public void setNoArticulo(int noArticulo) {
        this.noArticulo = noArticulo;
    }

    public BigDecimal getCantParaCompra() {
        return cantParaCompra;
    }

    public void setCantParaCompra(BigDecimal cantParaCompra) {
        this.cantParaCompra = cantParaCompra;
    }

    public BigDecimal getVUnitario() {
        return vUnitario;
    }

    public void setVUnitario(BigDecimal vUnitario) {
        this.vUnitario = vUnitario;
    }

    public BigDecimal getVIvaUnit() {
        return vIvaUnit;
    }

    public void setVIvaUnit(BigDecimal vIvaUnit) {
        this.vIvaUnit = vIvaUnit;
    }

    public Integer getNoProveedor() {
        return noProveedor;
    }

    public void setNoProveedor(Integer noProveedor) {
        this.noProveedor = noProveedor;
    }

    public Integer getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(Integer noFactura) {
        this.noFactura = noFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNoSerialFactura() {
        return noSerialFactura;
    }

    public void setNoSerialFactura(String noSerialFactura) {
        this.noSerialFactura = noSerialFactura;
    }

    public String getNoAutorizaFact() {
        return noAutorizaFact;
    }

    public void setNoAutorizaFact(String noAutorizaFact) {
        this.noAutorizaFact = noAutorizaFact;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigInteger getPerModifica() {
        return perModifica;
    }

    public void setPerModifica(BigInteger perModifica) {
        this.perModifica = perModifica;
    }

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public BigInteger getIdTipodoc() {
        return idTipodoc;
    }

    public void setIdTipodoc(BigInteger idTipodoc) {
        this.idTipodoc = idTipodoc;
    }

    public Date getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(Date fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public BigInteger getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(BigInteger numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getDescripDoc() {
        return descripDoc;
    }

    public void setDescripDoc(String descripDoc) {
        this.descripDoc = descripDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tDetOrdenCompraPK != null ? tDetOrdenCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDetOrdenCompra)) {
            return false;
        }
        TDetOrdenCompra other = (TDetOrdenCompra) object;
        if ((this.tDetOrdenCompraPK == null && other.tDetOrdenCompraPK != null) || (this.tDetOrdenCompraPK != null && !this.tDetOrdenCompraPK.equals(other.tDetOrdenCompraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.igm.bodegas.modelo.TDetOrdenCompra[ tDetOrdenCompraPK=" + tDetOrdenCompraPK + " ]";
    }
    
}
