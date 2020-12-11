package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import services.InventoryService;

/**
 *
 * @author Jean
 */
public class CategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InventoryService is = new InventoryService();
        List<Category> categories = is.getCategories();
        request.setAttribute("categories", categories);

        String categoryId = request.getParameter("editCategory");
        if (categoryId != null) {
            Category category = is.getCategory(categoryId);
            request.setAttribute("category", category);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/categories.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String categoryName = request.getParameter("categoryName");
        InventoryService is = new InventoryService();
        boolean success = false;

        switch (action) {
            case "add":
                success = is.addCategory(categoryName);
                break;
            case "edit":
                String categoryId = request.getParameter("categoryId");
                success = is.updateCategory(categoryId, categoryName);
        }

        if (!success) {
            action = "fail";
        }

        request.setAttribute("message", action);
        this.doGet(request, response);
    }

}
