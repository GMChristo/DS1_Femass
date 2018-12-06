/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Movimentacao;
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
public class MovimentacaoDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void incluir (Movimentacao movimentacao){
        em.persist(movimentacao);
    }
    public void alterar (Movimentacao movimentacao){
        em.merge(movimentacao);
    }
    public void excluir (Movimentacao movimentacao){
        em.remove(movimentacao);
    }
    public List<Movimentacao> getMovimentacoes(){
        Query q = em.createQuery("select m from Movimentacao m order by m.id");
        return q.getResultList();
    }
    
    public List<Movimentacao> getMovAbertas(){
        Query q = em.createQuery("select m from Movimentacao m where m.dataDevolucao is NULL order by m.id");
        return q.getResultList(); 
    }
}