package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BusDaoImpl extends AutoparkDaoImpl<Bus> {

    public BusDaoImpl() {
        setClazz(Bus.class);
    }

    public Bus findByNumber(final String number) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Bus> query = entityManager
                .createQuery("from Bus b where b.number=:number", Bus.class)
                .setParameter("number", number);
        return getSingleResult(query);
    }

    public List<Bus> findByRoute(final Route route) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Bus> query = entityManager
                .createQuery("from Bus b where b.route=:route", Bus.class)
                .setParameter("route", route);
        return getResultList(query);
    }
}
