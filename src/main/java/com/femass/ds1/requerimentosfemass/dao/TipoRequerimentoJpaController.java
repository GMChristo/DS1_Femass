/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentosfemass.dao;

import com.femass.ds1.requerimentofemass.dao
        .exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;

/**
 *
 * @author gustavo
 */
public class TipoRequerimentoJpaController implements Serializable {

    
    
    public TipoRequerimentoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("RequerimentoPU");
    }
    public TipoRequerimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRequerimento tiporeq) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tiporeq);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoRequerimento tiporeq) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tiporeq = em.merge(tiporeq);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tiporeq.getId();
                if (findTipoRequerimento(id) == null) {
                    throw new NonexistentEntityException("The candidato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoRequerimento tiporeq;
            try {
                tiporeq = em.getReference(TipoRequerimento.class, id);
                tiporeq.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The candidato with id " + id + " no longer exists.", enfe);
            }
            em.remove(tiporeq);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoRequerimento> findTipoRequerimentoEntities() {
        return findTipoRequerimentoEntities(true, -1, -1);
    }

    public List<TipoRequerimento> findTipoRequerimentoEntities(int maxResults, int firstResult) {
        return findTipoRequerimentoEntities(false, maxResults, firstResult);
    }

    private List<TipoRequerimento> findTipoRequerimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoRequerimento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoRequerimento findTipoRequerimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoRequerimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoRequerimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoRequerimento> rt = cq.from(TipoRequerimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
