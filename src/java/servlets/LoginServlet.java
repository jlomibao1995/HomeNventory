package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import services.AccountService;

/**
 *
 * @author Jean
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String log = request.getParameter("log");

        if (log != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("message", "User successfully logged out.");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AccountService as = new AccountService();

        User user = as.login(email, password);

        if (user == null) {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("message", "Login invalid.");
            this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("email", email);

        if (user.getRole().getRoleId() == 2) {
            response.sendRedirect("inventory");
        } else if (user.getRole().getRoleId() == 1) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("companyadmin");
        }
    }

}
