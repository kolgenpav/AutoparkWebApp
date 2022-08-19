package ua.edu.znu.autoparkweb.service;

import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;

import javax.persistence.Query;
import java.util.List;

public class RouteDaoImpl extends AutoparkDaoImpl<Route> {

    public Route findByNumber(final String number) {
        Query query = entityManager.createQuery("from Route r where r.number=:number")
                .setParameter("number", number);
        return (Route) query.getSingleResult();
    }

    public List<Route> findByNamePattern(final String routeNamePattern) {
        Query query = entityManager.createQuery("from Route r where r.name=:routeName")
                .setParameter("routeName", "%" + routeNamePattern + "%");
        return (List<Route>) query.getResultList();
    }

    public Route findByBus(final Bus bus) {
        Query query = entityManager.createQuery("from Route r where :bus member of r.buses")
                .setParameter("bus", bus);
        return (Route) query.getSingleResult();
    }
}
