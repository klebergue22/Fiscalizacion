/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igm.bodegas.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
@Table(name = "V_GRUPO_ARTICULO")
public class TGruposArticulos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column (name = "GRUPO" , nullable = false)
    private short idGrupo;
        
    @Column (name = "DESC_GRUPO" , nullable = false)
    private String descGrupo;
     
    public short getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(short idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getDescGrupo() {
        return descGrupo;
    }

    public void setDescGrupo(String descGrupo) {
        this.descGrupo = descGrupo;
    }
    
}
