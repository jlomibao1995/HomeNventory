package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 * @author Jean
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String login = request.getParameter("login");

        if (login != null) {
            response.sendRedirect("login");
            return;
        }

        if (uuid != null) {
            AccountService as = new AccountService();
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();
            boolean activated = as.activateUser(uuid, path, url);
            request.setAttribute("activated", activated);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();

        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");

        String path = getServletContext().getRealPath("/WEB-INF");
        String url = request.getRequestURL().toString();

        boolean success = as.registerUser(email, password, firstname, lastname, path, url);

        if (!success) {
            request.setAttribute("email", email);
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("password", password);
        }

        request.setAttribute("success", success);

        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }

}
