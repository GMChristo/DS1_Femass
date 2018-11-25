/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.Responsavel;
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
public class ResponsavelDao {

    @PersistenceContext
    EntityManager em;

    public void incluir(Responsavel responsavel) {
        em.persist(responsavel);
    }

    public void alterar(Responsavel responsavel) {
        em.merge(responsavel);
    }
    public void excluir (Responsavel responsavel){
        // resolve erro de: Entity must be managed to call remove: try merging the detached and try the remove again.
        if (!em.contains(responsavel)) {
            responsavel = em.merge(responsavel);
        }
    }
    
    public List<Responsavel> getResponsaveis(){
        Query q = em.createQuery("select r from Responsavel r order by r.nome");
        return q.getResultList();
    }

    public Responsavel autenticar(String cpf, String senha) {
        String q = "select r FROM Responsavel r WHERE r.cpf=:cpf and r.senha=:senha";
        Responsavel resp = this.em.createQuery(q , Responsavel.class)
        .setParameter("cpf", cpf)
        .setParameter("senha", senha)
        .getSingleResult(); 
        
        System.out.println("Responsavel = "+resp.getId());
         
        return resp;
    }
    
    public Responsavel buscarPorCPF(String cpf){
        String q = "select r FROM Responsavel r WHERE r.cpf=:cpf";
        Responsavel resp = this.em.createQuery(q , Responsavel.class)
        .setParameter("cpf", cpf)
        .getSingleResult(); 
        
        System.out.println("Responsavel = "+resp.getId()+" - " + resp.getNome());
        return resp != null ?resp :null;
    }
}
