/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Curso;
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
public class CursoDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void incluir (Curso curso){
        em.persist(curso);
    }
    public void alterar (Curso curso){
        em.merge(curso);
    }
    public void excluir (Curso curso){
        em.remove(curso);
    }
    public List<Curso> getCursos(){
        Query q = em.createQuery("select c from Curso c order by c.nome");
        return q.getResultList();
    }
}
