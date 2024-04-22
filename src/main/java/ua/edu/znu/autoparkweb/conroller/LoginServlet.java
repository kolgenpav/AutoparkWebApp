package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.znu.autoparkweb.model.User;
import ua.edu.znu.autoparkweb.service.UserDaoImpl;

import java.io.IOException;

/**
 * Servlet that processes user login.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

//TODO Try @ServletSecurity servlet annotation instead of login form.

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        String nextUrl = "login";
        request.setAttribute("nextUrl", nextUrl);
//        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String sessionID = session.getId();
        System.out.println("LoginServlet: sessionID: " + sessionID);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        String message = null;
        String nextUrl = "login";
        if (action == null || action.equals("logout")) {
            session.invalidate();
            request.setAttribute("nextUrl", nextUrl);
        } else {
            try {
                UserDaoImpl userDao = (UserDaoImpl) getServletContext().getAttribute("userDao");
                User user = userDao.findByUsername(username);
                if (user != null && user.getUsername().equals(username)
                        && user.getPassword().equals(password)) {
                    session.setAttribute("user", user);
                    request.getRequestDispatcher("HomeServlet").forward(request, response);
                } else {
                    if (user == null) {
                        message = "No such username!";
                    } else {
                        message = "Authentication failed!";
                    }
                }
            } catch (ServletException e) {
                e.printStackTrace();
            }
            request.setAttribute("nextUrl", nextUrl);
            request.setAttribute("message", message);
        }
    }
}
