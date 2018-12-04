/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Aluno;
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
public class AlunoDao {
    
    @PersistenceContext
    EntityManager em;
    
    public void incluir (Aluno aluno){
        em.persist(aluno);
    }
    public void alterar (Aluno aluno){
        em.merge(aluno);
    }
    public void excluir (Aluno aluno){
        em.remove(aluno);
    }
    public List<Aluno> getAlunos(){
        Query q = em.createQuery("select a from Aluno a order by a.nome");
        return q.getResultList();
    }
    
    public Aluno porID(Long id){
        String q = "select a from Aluno a where a.id=:id";
         Aluno aluno = this.em.createQuery(q , Aluno.class)
        .setParameter("id", id)
        .getSingleResult(); 
        return aluno;
    }
}
