package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import model.User;
import services.AccountService;

/**
 *
 * @author Jean
 */
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        List<User> users = as.getUsers();
        request.setAttribute("users", users);

        String updateEmail = request.getParameter("updateEmail");

        if (updateEmail != null) {
            User user = as.getUser(updateEmail);
            request.setAttribute("updateUser", user);

            List<Role> roles = as.getRoles();
            request.setAttribute("roles", roles);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("userEmail");
        String firstName = request.getParameter("firstname");
        String lastNmae = request.getParameter("lastname");
        String password = request.getParameter("password");
        AccountService as = new AccountService();

        boolean success = false;

        switch (action) {
            case "delete":
                HttpSession session = request.getSession();
                String accountEmail = (String) session.getAttribute("email");
                success = as.delete(email, accountEmail);
                break;
            case "add":
                success = as.addUser(email, password, firstName, lastNmae);
                break;
            case "edit":
                String roleId = request.getParameter("roleId");
                String active = request.getParameter("active");
                User user = as.updateUser(email, firstName, lastNmae, password, active, roleId);

                if (user != null) {
                    success = true;
                }
                break;
        }

        if (!success) {
            action = "fail";
        }

        request.setAttribute("message", action);
        this.doGet(request, response);
    }

}
