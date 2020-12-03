package servlets;

import java.io.IOException;
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
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        String email = (String) session.getAttribute("email");
        User user = as.getUser(email);
        request.setAttribute("user", user);

        String edit = request.getParameter("edit");

        if (edit != null) {
            request.setAttribute("edit", edit);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String active = request.getParameter("active");

        AccountService as = new AccountService();
        User user = as.updateAccount(email, firstName, lastName, password, confirmPassword, active);

        if (user == null) {
            HttpSession session = request.getSession();
            String editEmail = (String) session.getAttribute("email");
            user = as.getUser(editEmail);
            request.setAttribute("user", user);
            request.setAttribute("edit", "edit");
            request.setAttribute("result", "fail");
            getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
            return;
        }

        request.setAttribute("result", "success");
        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

}
