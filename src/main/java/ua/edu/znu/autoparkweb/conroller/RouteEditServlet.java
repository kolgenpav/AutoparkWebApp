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
 * Edits existing route.
 */
@WebServlet("/RouteEditServlet")
public class RouteEditServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        long routeId = Long.parseLong(request.getParameter("routeId"));
        Route route = routeDao.findById(routeId);
        String nextUrl = "routeedit";
        request.setAttribute("route", route);
        request.setAttribute("nextUrl", nextUrl);
        response.setContentType("text/html;charset=UTF-8");
    }
}
