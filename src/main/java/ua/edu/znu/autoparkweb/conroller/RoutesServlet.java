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
import java.util.List;

/**
 * Populates routes.html with current list of routes in the doGet.
 * Performs route edit and route remove in the doPost.
 */
@WebServlet("/RoutesServlet")
public class RoutesServlet extends HttpServlet {
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
        RouteDaoImpl routeDao = new RouteDaoImpl();
        List<Route> routes = routeDao.findAll();

        context.setVariable("routes", routes);
        templateEngine.process("routes", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String action = request.getParameter("action");
        RouteDaoImpl routeDao = new RouteDaoImpl();
        long routeId = Long.parseLong(request.getParameter("routeId"));
        Route route = routeDao.findById(routeId);
        switch (action) {
            case "routeEdit" -> {
                String routeName = request.getParameter("routeName");
                route.setName(routeName);
                int routeNumber = Integer.parseInt(request.getParameter("routeNumber"));
                route.setNumber(routeNumber);
                routeDao.update(route);
            }
            case "routeRemove" -> {
                BusDaoImpl busDao = new BusDaoImpl();
                /*You need to set emptyRoute for the route's busses first*/
                for (Bus bus : route.getBuses()) {
                    bus.setRoute(Route.getEmptyRoute());
                    busDao.update(bus);
                }
                routeDao.delete(route);
            }
        }
        /*Make GET request to this servlet*/
        response.sendRedirect(request.getContextPath() + "/RoutesServlet");
}

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }

    @Override
    public String getServletInfo() {
        return "The start point for the authenticated user.";
    }
}
