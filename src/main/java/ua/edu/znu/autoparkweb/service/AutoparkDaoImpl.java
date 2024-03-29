package ua.edu.znu.autoparkweb.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Universal methods that working with all entities of Autopark database.
 *
 * @param <T> entity type
 */
public abstract class AutoparkDaoImpl<T> implements AutoparkDao<T> {

    protected static EntityManager getEntityManager(){
        return Persistence.createEntityManagerFactory("autoparkPU").createEntityManager();
    }

    private Class<T> clazz;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findById(final long id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
//        return entityManager.createQuery("from " + clazz.getName()).getResultList();
        CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T getSingleResult(TypedQuery<T> query) {
        EntityManager entityManager = getEntityManager();
        T entity = null;
        try {
            entity = query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        } finally {
            entityManager.close();
        }
        return entity;
    }

    @Override
    public List<T> getResultList(TypedQuery<T> query) {
        EntityManager entityManager = getEntityManager();
        List<T> entityList = null;
        try {
            entityList = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    public void create(final T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            /*To avoid org.hibernate.PersistentObjectException: detached entity passed to persist method*/
            entityManager.persist(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(final T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    public void delete(final T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            /*To avoid IllegalArgumentException: Removing a detached instance*/
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            transaction.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
}
