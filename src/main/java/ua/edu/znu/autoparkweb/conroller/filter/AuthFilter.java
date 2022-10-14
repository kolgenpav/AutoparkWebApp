package ua.edu.znu.autoparkweb.conroller.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.znu.autoparkweb.model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/HomeServlet", "/BusesServlet", "/BusAddServlet",
        "/RoutesServlet", "/RouteAddServlet", "/DriversServlet", "/DriverAddServlet"},
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath());
        } else {
            String sessionID = session.getId();
            System.out.println("AuthFilter: sessionID = " + sessionID);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                session.invalidate();
                resp.sendRedirect(req.getContextPath());
            } else {
                chain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
