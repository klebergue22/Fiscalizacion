
package com.igm.bodegas.servicio;

import com.igm.bodegas.dao.crudBodega;
import com.igm.bodegas.modelo.TGruposArticulos;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class GrupoArticulosServicio extends crudBodega{
    
        
    public List<TGruposArticulos> listadoGruposArticulos() {
        List<TGruposArticulos> listadoAcc;
        Query query = super.em.createQuery("select o from TGruposArticulos as o "
                                           + "ORDER BY o.descGrupo");

        listadoAcc = query.getResultList();

        return listadoAcc;
    }
    
}
