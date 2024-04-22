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
import ua.edu.znu.autoparkweb.conroller.listener.ThymeleafConfigurationListener;
import ua.edu.znu.autoparkweb.model.User;
import ua.edu.znu.autoparkweb.service.UserDaoImpl;

import javax.persistence.NoResultException;
import java.io.IOException;

/**
 * Servlet that makes new user registration - now does not used.
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        String nextUrl = "registration";
        request.setAttribute("nextUrl", nextUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String message = "";
        String nextUrl = "login";
        UserDaoImpl userDao = new UserDaoImpl();

        User foundedUser = userDao.findByUsername(username);
        if (foundedUser != null) {
            message = "User with " + username + " already registered!";
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userDao.create(newUser);
            message = "User " + username + " just registered!";
        }
        request.setAttribute("nextUrl", nextUrl);
        request.setAttribute("message", message);
    }

    @Override
    public String getServletInfo() {
        return "Servlet that makes new user registration.";
    }
}
