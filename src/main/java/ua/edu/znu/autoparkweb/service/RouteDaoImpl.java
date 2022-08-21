package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class RouteDaoImpl extends AutoparkDaoImpl<Route> {

    public Route findByNumber(final String number) {
        TypedQuery<Route> query = entityManager.createQuery("from Route r where r.number=:number", Route.class)
                .setParameter("number", number);
        return getSingleResult(query);
    }

    public List<Route> findByNamePattern(final String routeNamePattern) {
        TypedQuery<Route> query = entityManager
                .createQuery("from Route r where r.name=:routeName", Route.class)
                .setParameter("routeName", "%" + routeNamePattern + "%");
        return getResultList(query);
    }

    public Route findByBus(final Bus bus) {
        TypedQuery<Route> query = entityManager
                .createQuery("from Route r where :bus member of r.buses", Route.class)
                .setParameter("bus", bus);
        return getSingleResult(query);
    }
}
