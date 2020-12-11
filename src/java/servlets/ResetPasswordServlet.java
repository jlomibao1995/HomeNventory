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
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");

        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            this.getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AccountService as = new AccountService();
        boolean success = false;

        switch (action) {
            case "requestreset":
                String email = request.getParameter("email");
                String path = this.getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                success = as.resetPassword(email, path, url);

                if (!success) {
                    request.setAttribute("action", "fail");
                } else {
                    request.setAttribute("action", action);
                }

                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                return;
            case "setpassword":
                String newPassword = request.getParameter("newpassword");
                String confirmPassword = request.getParameter("confirmpassword");
                String uuid = request.getParameter("uuid");
                success = as.changePassword(uuid, newPassword, confirmPassword);

                if (!success) {
                    request.setAttribute("action", "fail");
                } else {
                    request.setAttribute("action", action);
                }

                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }

    }
}
