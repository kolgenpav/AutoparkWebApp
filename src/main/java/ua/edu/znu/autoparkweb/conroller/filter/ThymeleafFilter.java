package ua.edu.znu.autoparkweb.conroller.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.autoparkweb.conroller.listener.ThymeleafConfigurationListener;

import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = {"/LoginServlet", "/HomeServlet", "/BusAssignmentServlet",
        "/BusesServlet", "/BusAddServlet", "/BusEditServlet",
        "/RoutesServlet", "/RouteAddServlet", "/RouteEditServlet",
        "/DriversServlet", "/DriverAddServlet", "/DriverEditServlet"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class ThymeleafFilter implements Filter {

    private TemplateEngine templateEngine;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.context = filterConfig.getServletContext();
        this.templateEngine = (TemplateEngine) context
                .getAttribute(ThymeleafConfigurationListener.TEMPLATE_ENGINE_ATR);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //TODO Work twice for HomeServlet
        chain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        WebContext context = getWebContext(req, resp);
        Enumeration<String> params = req.getAttributeNames();
        /*For Debugging*/
//        while (params.hasMoreElements()) {
//            String param = params.nextElement();
//            if (!"nextUrl".equals(param))
//                context.setVariable(param, req.getAttribute(param));
//        }
        params.asIterator().forEachRemaining(param -> {
            if (!"nextUrl".equals(param))
                context.setVariable(param, req.getAttribute(param));
        });
        String nextUrl = (String) req.getAttribute("nextUrl");
        templateEngine.process(nextUrl, context, response.getWriter());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(context)
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
