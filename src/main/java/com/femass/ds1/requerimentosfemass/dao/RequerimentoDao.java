/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Requerimento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gabriel
 */
@Stateless
public class RequerimentoDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void incluir (Requerimento requerimento){
        em.persist(requerimento);
    }
    public void alterar (Requerimento requerimento){
        em.merge(requerimento);
    }
    public void excluir (Requerimento requerimento){
        if (!em.contains(requerimento)) {
            requerimento = em.merge(requerimento);
        }        
        em.remove(requerimento);
    }
    public List<Requerimento> getRequerimentos(){
        Query q = em.createQuery("select r from Requerimento r order by r.numeroProtocolo");
        return q.getResultList();
    }
}