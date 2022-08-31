package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RouteDaoImpl extends AutoparkDaoImpl<Route> {

    public RouteDaoImpl() {
        setClazz(Route.class);
    }

    public Route findByNumber(final int number) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Route> query = entityManager.createQuery("from Route r where r.number=:number", Route.class)
                .setParameter("number", number);
        return getSingleResult(query);
    }

    public List<Route> findByNamePattern(final String routeNamePattern) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Route> query = entityManager
                .createQuery("from Route r where r.name=:routeName", Route.class)
                .setParameter("routeName", "%" + routeNamePattern + "%");
        return getResultList(query);
    }

    public Route findByBus(final Bus bus) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Route> query = entityManager
                .createQuery("from Route r where :bus member of r.buses", Route.class)
                .setParameter("bus", bus);
        return getSingleResult(query);
    }
}
