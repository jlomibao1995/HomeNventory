package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Item;
import services.InventoryService;

/**
 *
 * @author Jean
 */
public class SearchInventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InventoryService is = new InventoryService();
        List<Item> items = is.getItems();
        request.setAttribute("items", items);
       getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("q");
        InventoryService is = new InventoryService();
        List<Item> items;
        
        if (key != null && !key.equals("")) {
           items = is.getItems(key);
        } else {
            items = is.getItems();
        }
        
        request.setAttribute("items", items);
        request.setAttribute("q", key);
        getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        
    }

}
