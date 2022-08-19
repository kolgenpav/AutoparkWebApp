package ua.edu.znu.autoparkweb.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public T findOne(final long id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void create(final T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public T update(final T entity) {
        entityManager.getTransaction().begin();
        T mergedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return mergedEntity;
    }

    public void delete(final T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }
}
