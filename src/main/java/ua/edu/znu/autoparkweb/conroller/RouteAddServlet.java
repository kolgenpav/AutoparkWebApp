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
import ua.edu.znu.autoparkweb.model.Route;
import ua.edu.znu.autoparkweb.service.RouteDaoImpl;

import java.io.IOException;

/**
 * Adds a new route.
 */
@WebServlet("/RouteAddServlet")
public class RouteAddServlet extends HttpServlet {
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
        templateEngine.process("routeadd", context, response.getWriter());
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
        /*Make GET request*/
        response.sendRedirect(request.getContextPath() + "/RoutesServlet");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
