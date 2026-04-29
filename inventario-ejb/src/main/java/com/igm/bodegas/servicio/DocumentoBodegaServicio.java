package com.igm.bodegas.servicio;

import com.igm.bodegas.dao.crudBodega;
import com.igm.bodegas.modelo.TDetOrdenCompra;
import com.igm.bodegas.modelo.TTipoDoc;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class DocumentoBodegaServicio extends crudBodega {
    
    
    public List<TTipoDoc> getAllTipoDocumento(){
    
        return super.em.createNamedQuery("TTipoDoc.findAll", TTipoDoc.class).getResultList();
    }
    
    
    public List<TDetOrdenCompra> listaOrdenesDeCompra() {
        List<TDetOrdenCompra> listadoAcc;
        Query query = super.em.createQuery("select o from TDetOrdenCompra as o "
                + "where o.tDetOrdenCompraPK.periodoCodigo = 22 "
                + "order by o.tDetOrdenCompraPK.noOrden, o.tDetOrdenCompraPK.noPedido, o.tDetOrdenCompraPK.noRegPedido ");

        listadoAcc = query.getResultList();

        return listadoAcc;
    }
    
    
    public List<TDetOrdenCompra> listaOrdenesDeCompra(BigDecimal idPeriodo, BigDecimal idNumOrden) {
        List<TDetOrdenCompra> listadoAcc;
        Query query = super.em.createQuery("select o from TDetOrdenCompra as o "
                + "where ((o.tDetOrdenCompraPK.periodoCodigo = ?1 ) AND (o.tDetOrdenCompraPK.noOrden = ?2 ))  "
                + "order by o.tDetOrdenCompraPK.noOrden, o.tDetOrdenCompraPK.noPedido, o.tDetOrdenCompraPK.noRegPedido ");
       
        
        query.setParameter(1, idPeriodo);
        query.setParameter(2, idNumOrden);
        
        em.getEntityManagerFactory().getCache().evictAll();
        
        listadoAcc = query.getResultList();

        return listadoAcc;
    }
    
    public List <BigDecimal> listaPeriodo(){
        StringBuilder consulta = null;
        long idSecuencia = 0;
        Object secuen;
        List<BigDecimal> vLista = new ArrayList<BigDecimal>();

        try {
            consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT PERIODO_CODIGO \n"
                    + "FROM T_DET_ORDEN_COMPRA \n"
                    + "WHERE PERIODO_CODIGO > 20 \n"
                    + "ORDER BY PERIODO_CODIGO ");
            Query query = em.createNativeQuery(consulta.toString());

            //secuen=query.getSingleResult();
            vLista = query.getResultList();
            //idSecuencia=Integer.parseInt(secuen.toString());
            return vLista;

        } catch (Exception e) {
            System.out.println("error al carga periodo  ->" + e.getMessage());
            return vLista;
        } finally {
            consulta = null;
        }
    }
          
    public List <BigDecimal> listaOrdenesProduccion(){
        StringBuilder consulta = null;
        long idSecuencia = 0;
        Object secuen;
        List<BigDecimal> vListaOrd = new ArrayList<>();

        try {
            consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT NO_ORDEN \n"
                    + "FROM T_DET_ORDEN_COMPRA \n"
                    + "WHERE PERIODO_CODIGO = 22 \n"
                    + "ORDER BY NO_ORDEN ");
            Query query = em.createNativeQuery(consulta.toString());

            //secuen=query.getSingleResult();
            vListaOrd = query.getResultList();
            //idSecuencia=Integer.parseInt(secuen.toString());
            return vListaOrd;

        } catch (Exception e) {
            System.out.println("error al carga periodo  ->" + e.getMessage());
            return vListaOrd;
        } finally {
            consulta = null;
        }
    }
    
    public List <BigDecimal> listaOrdenesProduccion(BigDecimal idPeriodo){
        StringBuilder consulta = null;
        long idSecuencia = 0;
        Object secuen;
        List<BigDecimal> vListaOrd = new ArrayList<>();

        try {
            consulta = new StringBuilder();
            consulta.append("SELECT DISTINCT NO_ORDEN \n"
                    + "FROM T_DET_ORDEN_COMPRA \n"
                    + "WHERE PERIODO_CODIGO = ?1  \n"
                    + "ORDER BY NO_ORDEN ");
            Query query = em.createNativeQuery(consulta.toString());
            
            query.setParameter(1, idPeriodo);
            
            vListaOrd = query.getResultList();
            return vListaOrd;

        } catch (Exception e) {
            System.out.println("error al carga periodo  ->" + e.getMessage());
            return vListaOrd;
        } finally {
            consulta = null;
        }
    }
    
    
    public int actualizarOrdenesCompra(BigDecimal idPeriodo, BigDecimal idNumOrden, BigDecimal idTipoDocumento, Date fecha, BigInteger numero){
        StringBuilder consulta = null;
        long idSecuencia = 0;
        Object secuen;
        int resultado = -1;
        try {
            
           Query query = em.createNativeQuery("UPDATE T_DET_ORDEN_COMPRA "
                          + " SET ID_TIPODOC = ?3 , NUM_DOCUMENTO = ?5, FECHA_DOC = ?4 "
                          + " WHERE PERIODO_CODIGO = ?1  AND  NO_ORDEN = ?2 ");
        
            query.setParameter(1, idPeriodo);
            query.setParameter(2, idNumOrden);
            query.setParameter(3, idTipoDocumento);
            query.setParameter(4, fecha);
            query.setParameter(5, numero);    
            
            resultado = query.executeUpdate();
            
            if(resultado > 0)                            
                return resultado;
            else 
                return resultado = -1;

        } catch (Exception e) {
            System.out.println("error al realizar el update  ->" + e.getMessage());
            return resultado;
        } 
    }
    
    
}
