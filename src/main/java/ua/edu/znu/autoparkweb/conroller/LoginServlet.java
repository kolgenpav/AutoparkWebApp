package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.autoparkweb.model.User;
import ua.edu.znu.autoparkweb.service.UserDaoImpl;

import java.io.IOException;

/**
 * Servlet that processes user login.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

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
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        WebContext context = getWebContext(request, response);
        HttpSession session = request.getSession();
        String sessionID = session.getId();
        System.out.println("LoginServlet: sessionID: " + sessionID);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        String messageText = null;
        if (action == null || action.equals("logout")) {
            session.invalidate();
        } else {
            try {
                UserDaoImpl userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
                User user = userDao.findByUsername(username);
                if (user != null && user.getUsername().equals(username)
                        && user.getPassword().equals(password)) {
                    session.setAttribute("user", user);
                    messageText = "Hello, " + username + "!";
                    request.setAttribute("message", messageText);
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                } else {
                    if (user == null) {
                        messageText = "No such username!";
                    } else {
                        messageText = "Authentication failed!";
                    }
                }
            } catch (ServletException e) {
                e.printStackTrace();
            }
            context.setVariable("message", messageText);
        }
        templateEngine.process("login", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
