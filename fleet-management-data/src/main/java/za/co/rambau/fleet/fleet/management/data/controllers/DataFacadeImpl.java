package za.co.rambau.fleet.fleet.management.data.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author fhatu
 * @param <T>
 */
public  class DataFacadeImpl<T> implements DataFacade<T> {

    private Class<T> entityClass;

    public DataFacadeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext(unitName = "fleetManPU")
    private EntityManager em;

    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public void edit(T entity) {
        em.merge(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public T find(Object id) {
       CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
       // cq.where(cq.)
        Query q = em.createQuery(cq);
        
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findRange(int from,int to) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(to - from + 1);
        q.setFirstResult(from);
        return q.getResultList();
    }

    @Override
    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
