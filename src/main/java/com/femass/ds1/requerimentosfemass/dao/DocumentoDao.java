/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Documento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author GMC
 */
@Stateless
public class DocumentoDao{

    @PersistenceContext
    EntityManager em;
    
    public void incluir (Documento documento){
        em.persist(documento);
    }
    public void alterar (Documento documento){
        em.merge(documento);
    }
    public void excluir (Documento documento){
        em.remove(documento);
    }
    public List<Documento> getDocumento(){
        Query q = em.createQuery("select d from Documento d order by d.nome");
        return q.getResultList();
    }
    
}
