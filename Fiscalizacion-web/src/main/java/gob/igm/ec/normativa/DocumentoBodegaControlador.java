package gob.igm.ec.normativa;

import com.igm.bodegas.modelo.TDetOrdenCompra;
import com.igm.bodegas.modelo.TTipoDoc;
import com.igm.bodegas.servicio.DocumentoBodegaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DocumentoBodegaControlador implements Serializable{
    
    private List<TTipoDoc> listaTipoDoc;
    private List<TDetOrdenCompra> ListaOrdenesCompra;
    private List<BigDecimal> listaPeriodo;
    private List<BigDecimal> listaOrdenesCombo;
    private BigDecimal idPeriodo;
    private BigDecimal idOrden;
    private BigDecimal idTipoDoc;
    private int resultado;
    private BigInteger numeroDocumento;
    private Date fechaRegistro;
    
    @EJB
    private DocumentoBodegaServicio tipoDocFacade;
    
    
    @PostConstruct
    public void init(){
        
        listaTipoDoc=tipoDocFacade.getAllTipoDocumento();
        
        listaPeriodo = tipoDocFacade.listaPeriodo();
       // listaOrdenesCombo = tipoDocFacade.listaOrdenesProduccion();
         
    }
    
    public void listadoOrdenesDeProduccion() {
        try {
            listaOrdenesCombo = tipoDocFacade.listaOrdenesProduccion(idPeriodo);
            
//            for (BigDecimal i : listaOrdenesCombo){
//            System.out.println("LISTA PERIODO >>>>" + i);
//        }
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
    }
    
    public void listadoOrdenes() {
        try {
            ListaOrdenesCompra=tipoDocFacade.listaOrdenesDeCompra(idPeriodo, idOrden);
            
//            for (BigDecimal i : listaOrdenesCombo){
//            System.out.println("LISTA PERIODO >>>>" + i);
//        }
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
    }
    
    
    public void ingresarPago(){
      try{
          
//          System.out.println("CODIGO PERIODO >>>>" + idPeriodo);
//          System.out.println("CODIGO CODIGO ORDEN >>>>" + idOrden);
//          System.out.println("CODIGO TIPO DOCUMENTO >>>>" + idTipoDoc);
////       long idSecuencial = 0L;
////       long idMesDuplicado = 0L;
                  
        if (idPeriodo == null || idPeriodo.longValue() == 0) {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "SELECCIONE PERIODO"));
        }else if (idOrden == null || idOrden.longValue() == 0){         
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "SELECCIONE ORDEN DE COMPRA"));
        }else if (idTipoDoc == null || idTipoDoc.longValue() == 0){         
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "SELECCIONE TIPO DE DOCUMENTO"));
        } else if (numeroDocumento == null || numeroDocumento.longValue() == 0){         
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "INGRESE NÚMERO DOCUMENTO"));
        } else if (fechaRegistro == null){         
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "INGRESE UNA FECHA"));
        } else {
////              idSecuencial = tipoPagosFacade.buscarSecuencial();
////              idSecuencial += 1;
////
                  resultado =   tipoDocFacade.actualizarOrdenesCompra(idPeriodo, idOrden, idTipoDoc, fechaRegistro, numeroDocumento);
                  if (resultado > 0 ){
                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "REGISTROS ACTUALIZADOS EXITOSAMENTE"));
                      ListaOrdenesCompra = tipoDocFacade.listaOrdenesDeCompra(idPeriodo, idOrden);
//                      for (TDetOrdenCompra i : ListaOrdenesCompra) {
//                          System.out.println("id documento >>>>" + i.getIdTipodoc());
//                          System.out.println("id orden >>>>" + i.getTDetOrdenCompraPK().getNoOrden());
//                          System.out.println("id numero documento documento >>>>" + i.getNumDocumento());
//                      }
////        
                  }else{
                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR :", "NO SE ACTUALIZÓ NINGUN REGISTRO"));
                  }
                  numeroDocumento = BigInteger.ZERO;
                  fechaRegistro = null;
                  //factIngreso = new Factura();
////              idMes = BigDecimal.ZERO;
////              idTipoPago = BigDecimal.ZERO;
////              idAnio = BigDecimal.ZERO;
          }
      } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
       
    }

    public List<TTipoDoc> getListaTipoDoc() {
        return listaTipoDoc;
    }

    public void setListaTipoDoc(List<TTipoDoc> listaTipoDoc) {
        this.listaTipoDoc = listaTipoDoc;
    }

    public List<TDetOrdenCompra> getListaOrdenesCompra() {
        return ListaOrdenesCompra;
    }

    public void setListaOrdenesCompra(List<TDetOrdenCompra> ListaOrdenesCompra) {
        this.ListaOrdenesCompra = ListaOrdenesCompra;
    }

    public BigDecimal getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(BigDecimal idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }
    
    public List<BigDecimal> getListaPeriodo() {
        return listaPeriodo;
    }

    public void setListaPeriodo(List<BigDecimal> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    public List<BigDecimal> getListaOrdenesCombo() {
        return listaOrdenesCombo;
    }

    public void setListaOrdenesCombo(List<BigDecimal> listaOrdenesCombo) {
        this.listaOrdenesCombo = listaOrdenesCombo;
    }

    public BigDecimal getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(BigDecimal idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public BigDecimal getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigDecimal idOrden) {
        this.idOrden = idOrden;
    }

    public BigInteger getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(BigInteger numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    
    
    
}
