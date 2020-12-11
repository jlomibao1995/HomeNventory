package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Item;
import model.User;

/**
 *
 * @author Jean
 */
public class InventoryService {

    public List<Category> getCategories() {
        CategoryDB cb = new CategoryDB();

        try {
            List<Category> list = cb.getAll();
            return list;
        } catch (Exception ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem getting categories encountered.");
            return null;
        }
    }

    public Category getCategory(String id) {
        CategoryDB cb = new CategoryDB();
        try {
            int categoryId = Integer.parseInt(id);
            Category category = cb.getCategory(categoryId);
            return category;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem getting a category.");
            return null;
        }
    }

    public boolean addCategory(String categoryName) {
        if (categoryName == null || categoryName.equals("")) {
            return false;
        }

        CategoryDB cb = new CategoryDB();

        try {
            Category category = new Category(0, categoryName);
            cb.insert(category);
            return true;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem adding category {0}", categoryName);
            return false;
        }
    }

    public boolean updateCategory(String categoryId, String categoryName) {
        if (categoryName == null || categoryName.equals("")) {
            return false;
        }

        try {
            int id = Integer.parseInt(categoryId);
            CategoryDB cb = new CategoryDB();
            cb.update(new Category(id, categoryName));
            return true;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with updating category {0}", categoryId);
            return false;
        }
    }

    public Item getItem(String itemId) {
        int id = Integer.parseInt(itemId);
        ItemDB ib = new ItemDB();

        try {
            Item item = ib.getItem(id);
            return item;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with getting item {0}", itemId);
            return null;
        }
    }

    public List<Item> getItems() {
        ItemDB ib = new ItemDB();

        try {
            List<Item> items = ib.getAll();
            return items;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with getting all items");
            return null;
        }
    }

    public List<Item> getItems(String key) {
        ItemDB ib = new ItemDB();

        try {
            List<Item> items = ib.getAll();
            List<Item> searchItems = new ArrayList<>();

            for (Item item : items) {
                if (item.getItemName().startsWith(key) || item.getItemName().contains(key)) {
                    searchItems.add(item);
                }
            }

            return searchItems;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with getting all items with key:{0}", key);
            return null;
        }
    }

    public boolean addItem(String email, String itemName, String price, String category) {
        if (email == null || email.equals("") || itemName == null || itemName.equals("")
                || price == null || price.equals("")) {
            return false;
        }

        try {
            double itemPrice = Double.parseDouble(price);
            int categoryId = Integer.parseInt(category);
            Item item = new Item(0, itemName, itemPrice);
            item.setCategory(new Category(categoryId));

            UserDB ub = new UserDB();
            User owner = ub.getUser(email);
            item.setOwner(owner);

            ItemDB ib = new ItemDB();
            ib.insert(item);
            return true;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with adding item {0}", itemName);
            return false;
        }
    }

    public boolean updateItem(String email, String itemId, String itemName, String price, String category) {
        if (email == null || email.equals("") || itemName == null || itemName.equals("")
                || price == null || price.equals("")) {
            return false;
        }

        try {
            int id = Integer.parseInt(itemId);
            double itemPrice = Double.parseDouble(price);
            int categoryId = Integer.parseInt(category);

            ItemDB ib = new ItemDB();
            Item item = ib.getItem(id);

            if (!email.equals(item.getOwner().getEmail())) {
                return false;
            }

            item.setCategory(new Category(categoryId));
            item.setItemName(itemName);
            item.setPrice(itemPrice);
            ib.update(item);
            return true;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with updating item {0}", itemId);
            return false;
        }
    }

    public boolean deleteItem(String itemId, String email) {
        try {
            int id = Integer.parseInt(itemId);

            ItemDB ib = new ItemDB();

            Item item = ib.getItem(id);
            User owner = item.getOwner();

            if (!owner.getEmail().equals(email)) {
                return false;
            }

            ib.delete(item);
            return true;
        } catch (Exception e) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.WARNING, "Problem with deleting item {0}", itemId);
            return false;
        }
    }
}
