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

/**
 * Adds a new bus.
 */
@WebServlet("/BusAddServlet")
public class BusAddServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        String nextUrl = "busadd";
        request.setAttribute("nextUrl", nextUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        BusDaoImpl busDao = (BusDaoImpl) getServletContext().getAttribute("busDao");
        Bus bus = new Bus();
        String busNumber = request.getParameter("busNumber");
        bus.setNumber(busNumber);
        RouteDaoImpl routeDao = (RouteDaoImpl) getServletContext().getAttribute("routeDao");
        Route emptyRoute = routeDao.findByNumber(-1);
        if (emptyRoute == null) {
            emptyRoute = Route.getEmptyRoute();
            routeDao.create(emptyRoute);
        }
        bus.setRoute(emptyRoute);
        busDao.create(bus);
        /*Get new bus info with busId*/
        bus = busDao.findByNumber(busNumber);
        request.setAttribute("busId", bus.getId());
        request.setAttribute("action", "busAdd");
        request.getRequestDispatcher("/BusAssignmentServlet").forward(request, response);
    }
}
