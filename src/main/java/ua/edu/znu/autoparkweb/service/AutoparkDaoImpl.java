package ua.edu.znu.autoparkweb.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Universal methods that working with entities' instances of Autopark database.
 *
 * @param <T> entity type
 */
public abstract class AutoparkDaoImpl<T> implements AutoparkDao<T> {

    public AutoparkDaoImpl() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("autoparkPU");
        this.entityManager = managerFactory.createEntityManager();
    }

    private Class<T> clazz;

    protected EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findById(final long id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public T getSingleResult(TypedQuery<T> query) {
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
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void update(final T entity) {
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
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    public void deleteById(final long entityId) {
        final T entity = findById(entityId);
        delete(entity);
    }
}
