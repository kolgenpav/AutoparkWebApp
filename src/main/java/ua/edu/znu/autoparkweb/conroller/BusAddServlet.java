package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
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
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
        templateEngine.process("busadd", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        BusDaoImpl busDao = new BusDaoImpl();
        Bus bus = new Bus();
        String busNumber = request.getParameter("busNumber");
        bus.setNumber(busNumber);
        RouteDaoImpl routeDao = new RouteDaoImpl();
        Route emptyRoute = routeDao.findByNumber(-1);
        if (emptyRoute == null) {
            emptyRoute = Route.getEmptyRoute();
            routeDao.create(emptyRoute);
        }
        bus.setRoute(emptyRoute);
        busDao.create(bus);
        bus = busDao.findByNumber(busNumber);
        request.setAttribute("busId", bus.getId());
        request.setAttribute("action","busAdd");
        request.getRequestDispatcher("/BusAssignmentServlet").forward(request, response);
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
