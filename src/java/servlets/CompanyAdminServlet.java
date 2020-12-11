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
public class CompanyAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.getUser(email);
        request.setAttribute("company", user.getCompany());

        List<User> companyUsers = user.getCompany().getUserList();
        request.setAttribute("users", companyUsers);

        String updateEmail = request.getParameter("updateEmail");

        if (updateEmail != null) {
            User updateUser = as.getUser(updateEmail);
            request.setAttribute("updateUser", updateUser);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/companyadmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("userEmail");
        String firstName = request.getParameter("firstname");
        String lastNmae = request.getParameter("lastname");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        String adminEmail = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.getUser(adminEmail);
        Company company = user.getCompany();

        boolean success = false;

        switch (action) {
            case "delete":
                success = as.delete(email, adminEmail);
                break;
            case "add":
                success = as.addCompanyUser(email, password, firstName, lastNmae, company);
                break;
            case "edit":
                String active = request.getParameter("active");
                User updateUser = as.updateUser(email, firstName, lastNmae, password, active, null);

                if (updateUser != null) {
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
