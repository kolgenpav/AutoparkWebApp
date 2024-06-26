package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Bus;
import ua.edu.znu.autoparkweb.model.Route;
import ua.edu.znu.autoparkweb.service.BusDaoImpl;
import ua.edu.znu.autoparkweb.service.RouteDaoImpl;

import java.io.IOException;
import java.util.List;

/**
 * Provides operations with Route directory.
 */
@WebServlet("/RoutesServlet")
public class RoutesServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        List<Route> routes = routeDao.findAll();
        String nextUrl = "routes";
        request.setAttribute("routes", routes);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        Long routeId = Long.valueOf(request.getParameter("routeId"));
        Route route = routeDao.findById(routeId);
        switch (action) {
            case "routeEdit" -> {
                String routeName = request.getParameter("routeName");
                route.setName(routeName);
                Integer routeNumber = Integer.valueOf(request.getParameter("routeNumber"));
                route.setNumber(routeNumber);
                routeDao.update(route);
            }
            case "routeRemove" -> {
                BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
                /*You need to set emptyRoute for the route's busses first*/
                Route emptyRoute = routeDao.findByNumber(-1);
                if (emptyRoute == null) {
                    emptyRoute = Route.getEmptyRoute();
                }
                for (Bus bus : route.getBuses()) {
                    bus.setRoute(emptyRoute);
                    busDao.update(bus);
                }
                routeDao.delete(route);
            }
        }
        String nextUrl = "routes";
        request.setAttribute("nextUrl", nextUrl);
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/RoutesServlet");
    }
}
