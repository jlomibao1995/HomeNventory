package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Item;
import model.User;
import services.AccountService;
import services.InventoryService;

/**
 *
 * @author Jean
 */
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        String email = (String) session.getAttribute("email");
        User user = as.getUser(email);
        request.setAttribute("user", user);
        
        InventoryService is = new InventoryService();
        List<Category> categories = is.getCategories();
        request.setAttribute("categories", categories);
        
        String itemId = request.getParameter("itemedit");
        
        if (itemId != null) {
            Item item = is.getItem(itemId);
            request.setAttribute("itemedit", item);
        }        
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        InventoryService is = new InventoryService();
        
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        String email = (String) session.getAttribute("email");
        User user = as.getUser(email);
        
        String itemName = request.getParameter("itemname");
        String price = request.getParameter("price");
        String category = request.getParameter("categoryId");
        String itemId = request.getParameter("itemid");
        User updatedUser = null; 
        
        switch(action) {
            case "add":
                updatedUser = is.addItem(user, itemName, price, category);
                break;
            case "save": 
                updatedUser = is.updateItem(itemId, itemName, price, category);
                break;
            case "delete":
                updatedUser = is.deleteItem(itemId, user);
        }
        
        if (updatedUser != null)
        {
            request.setAttribute("user", updatedUser);
            request.setAttribute("message", action);
        } else {
            request.setAttribute("message", "fail");
        }
        
        this.doGet(request, response);
    }

}
