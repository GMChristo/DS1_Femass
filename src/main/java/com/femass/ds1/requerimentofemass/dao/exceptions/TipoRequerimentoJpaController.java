/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femass.ds1.requerimentofemass.dao.exceptions;

import com.femass.ds1.requerimentofemass.dao.exceptions.exceptions.NonexistentEntityException;
import com.femass.ds1.requerimentofemass.dao.exceptions.exceptions.RollbackFailureException;
import com.femass.ds1.requerimentosfemass.model.TipoRequerimento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author gustavo
 */
public class TipoRequerimentoJpaController implements Serializable {

    public TipoRequerimentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRequerimento tipoRequerimento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tipoRequerimento);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoRequerimento tipoRequerimento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tipoRequerimento = em.merge(tipoRequerimento);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoRequerimento.getId();
                if (findTipoRequerimento(id) == null) {
                    throw new NonexistentEntityException("The tipoRequerimento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoRequerimento tipoRequerimento;
            try {
                tipoRequerimento = em.getReference(TipoRequerimento.class, id);
                tipoRequerimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoRequerimento with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoRequerimento);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
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
