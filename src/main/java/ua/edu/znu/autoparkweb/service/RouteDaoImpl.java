package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Specific Route methods.
 */
public class RouteDaoImpl extends AutoparkDaoImpl<Route> {

    public RouteDaoImpl() {
        setClazz(Route.class);
    }

    public Route findByNumber(final Integer number) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Route> query = entityManager.createQuery("from Route r where r.number=:number", Route.class)
                .setParameter("number", number);
        return getSingleResult(query);
    }

    public Route findByBus(final Bus bus) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Route> query = entityManager
                .createQuery("from Route r where :bus member of r.buses", Route.class)
                .setParameter("bus", bus);
        return getSingleResult(query);
    }
}
