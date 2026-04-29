/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igm.bodegas.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alexander Jimenez
 */
@Embeddable
public class TDetOrdenCompraPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PERIODO_CODIGO")
    private short periodoCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_ORDEN")
    private int noOrden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_PEDIDO")
    private int noPedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_REG_PEDIDO")
    private short noRegPedido;

    public TDetOrdenCompraPK() {
    }

    public TDetOrdenCompraPK(short periodoCodigo, int noOrden, int noPedido, short noRegPedido) {
        this.periodoCodigo = periodoCodigo;
        this.noOrden = noOrden;
        this.noPedido = noPedido;
        this.noRegPedido = noRegPedido;
    }

    public short getPeriodoCodigo() {
        return periodoCodigo;
    }

    public void setPeriodoCodigo(short periodoCodigo) {
        this.periodoCodigo = periodoCodigo;
    }

    public int getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(int noOrden) {
        this.noOrden = noOrden;
    }

    public int getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(int noPedido) {
        this.noPedido = noPedido;
    }

    public short getNoRegPedido() {
        return noRegPedido;
    }

    public void setNoRegPedido(short noRegPedido) {
        this.noRegPedido = noRegPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) periodoCodigo;
        hash += (int) noOrden;
        hash += (int) noPedido;
        hash += (int) noRegPedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDetOrdenCompraPK)) {
            return false;
        }
        TDetOrdenCompraPK other = (TDetOrdenCompraPK) object;
        if (this.periodoCodigo != other.periodoCodigo) {
            return false;
        }
        if (this.noOrden != other.noOrden) {
            return false;
        }
        if (this.noPedido != other.noPedido) {
            return false;
        }
        if (this.noRegPedido != other.noRegPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.igm.bodegas.modelo.TDetOrdenCompraPK[ periodoCodigo=" + periodoCodigo + ", noOrden=" + noOrden + ", noPedido=" + noPedido + ", noRegPedido=" + noRegPedido + " ]";
    }
    
}
