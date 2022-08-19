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
import ua.edu.znu.autoparkweb.service.UserDaoImpl;

import javax.persistence.NoResultException;
import java.io.IOException;

/**
 * Servlet that processes user login.
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {

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
        templateEngine.process("index", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDaoImpl userDao = new UserDaoImpl();
        try {
            if (userDao.isAuthenticated(username, password)) {
//        if (isAuthenticated(username, password)) {
                context.setVariable("message", "Hello " + username + "!");
                context.setVariable("servlet", "index.html");
                context.setVariable("linkText", "Index");
            } else {
                context.setVariable("message", "Authentication failed!");
                context.setVariable("servlet", "RegistrationServlet");
                context.setVariable("linkText", "Registration");

            }
        } catch (NoResultException ex) {
            context.setVariable("message", "No such username!");
            context.setVariable("servlet", "index.html");
            context.setVariable("linkText", "Index");
        }

        templateEngine.process("home", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }

    /**
     * Authenticates user.
     *
     * @param username username
     * @param password password
     * @return is user authenticated
     */
    private boolean isAuthenticated(String username, String password) {
        return "foo".equalsIgnoreCase(username) && "bar".equalsIgnoreCase(password);
    }

    @Override
    public String getServletInfo() {
        return "Servlet that processes user login.";
    }
}
