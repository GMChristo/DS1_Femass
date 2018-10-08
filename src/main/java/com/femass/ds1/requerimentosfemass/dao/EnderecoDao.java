/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Endereco;
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
public class EnderecoDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void incluir (Endereco endereco){
        em.persist(endereco);
    }
    public void alterar (Endereco endereco){
        em.merge(endereco);
    }
    public void excluir (Endereco endereco){
        em.remove(endereco);
    }
    public List<Endereco> getCursos(){
        Query q = em.createQuery("select e from Endereco e order by e.logradouro");
        return q.getResultList();
    }
}