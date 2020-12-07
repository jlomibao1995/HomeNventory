package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Company;
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

        String roleUser = request.getParameter("updateRole");
//        if (roleUser != null) {
//            User user = as.getUser(roleUser);
//            request.setAttribute("setCompany", user);
//        }

        List<Company> companies = as.getCompanies();
        request.setAttribute("companies", companies);

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
        String roleUser = request.getParameter("roleUser");
        String companyId = request.getParameter("companyId");
        AccountService as = new AccountService();

        boolean success = false;

        switch (action) {
            case "delete":
                HttpSession session = request.getSession();
                String accountEmail = (String) session.getAttribute("email");
                success = as.delete(email, accountEmail);
                break;
            case "add":
                success = as.addUser(email, password, firstName, lastNmae, companyId);
                break;
            case "edit":
                String active = request.getParameter("active");
                User user = as.updateUser(email, firstName, lastNmae, password, active);

                if (user != null) {
                    success = true;
                }
                break;
            case "systemadmin":
                success = as.changeRole(roleUser, 1);
                break;
            case "companyadmin":
                success = as.changeRole(roleUser, 3);
                break;
            case "regularuser":
                success = as.changeRole(roleUser, 2);

        }

        if (!success) {
            action = "fail";
        }

        request.setAttribute("message", action);
        doGet(request, response);
    }

}
