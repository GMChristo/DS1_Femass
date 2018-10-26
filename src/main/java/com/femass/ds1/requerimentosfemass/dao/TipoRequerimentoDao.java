/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;
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
public class TipoRequerimentoDao {

    @PersistenceContext
    EntityManager em;

    public void incluir(TipoRequerimento tipoRequerimento) {
        em.persist(tipoRequerimento);
    }

    public void alterar(TipoRequerimento tipoRequerimento) {
        em.merge(tipoRequerimento);
    }

    public void excluir(TipoRequerimento tipoRequerimento) {
        // resolve erro de: Entity must be managed to call remove: com.femass.ds1.requerimentosfemass.model.TipoRequerimento[ id=4 ], try merging the detached and try the remove again.
        if (!em.contains(tipoRequerimento)) {
            tipoRequerimento = em.merge(tipoRequerimento);
        }
        em.remove(tipoRequerimento);
    }

    public List<TipoRequerimento> getTipoRequerimentos() {
        Query q = em.createQuery("select t from TipoRequerimento t order by t.nome");
        return q.getResultList();
    }
}
