package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.autoparkweb.model.Route;
import ua.edu.znu.autoparkweb.service.RouteDaoImpl;

import java.io.IOException;

/**
 * Adds a new route.
 */
@WebServlet("/RouteAddServlet")
public class RouteAddServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        String nextUrl = "routeadd";
        request.setAttribute("nextUrl", nextUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        Route route = new Route();
        String routeName = request.getParameter("routeName");
        route.setName(routeName);
        int routeNumber = Integer.parseInt(request.getParameter("routeNumber"));
        route.setNumber(routeNumber);
        routeDao.create(route);
        String nextUrl = "routes";
        request.setAttribute("nextUrl", nextUrl);
        /*Make GET request*/
        response.sendRedirect(request.getContextPath() + "/RoutesServlet");
    }
}
