package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import java.util.List;
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
        }
        catch (Exception ex) {
            return null;
        }
    }
     
     public Item getItem(String itemId) {
         int id = Integer.parseInt(itemId);
         ItemDB ib = new ItemDB();
         
         try {
             Item item = ib.getItem(id);
             return item;
         } catch (Exception e) {
             return null;
         }
     }
    
     public User addItem(User owner, String itemName, String price, String category) {
         if (itemName == null || itemName.equals("") || price == null || price.equals("")) {
             return null;
         }
         
         try {
             double itemPrice = Double.parseDouble(price);
             int categoryId = Integer.parseInt(category);
             Item item = new Item(0, itemName, itemPrice);
             item.setCategory(new Category(categoryId));
             item.setOwner(owner);
             
             ItemDB ib = new ItemDB();
             ib.insert(item);
             return owner;
         } catch (Exception e) {
             return null;
         }
     }
     
     public User updateItem(String itemId, String itemName, String price, String category) {
         if (itemName == null || itemName.equals("") || price == null || price.equals("")) {
             return null;
         }
         
         try {
             int id = Integer.parseInt(itemId);
             double itemPrice = Double.parseDouble(price);
             int categoryId = Integer.parseInt(category);
             
             ItemDB ib = new ItemDB();
             UserDB ub = new UserDB();
             
             Item item = ib.getItem(id);
             item.setCategory(new Category(categoryId));
             item.setItemName(itemName);
             item.setPrice(itemPrice);
             ib.update(item);
             
             User updatedUser = ub.getUser(item.getOwner().getEmail());
             
             return updatedUser;
         } catch(Exception e) {
             return null;
         }
     }
     
     public User deleteItem(String itemId, User user) {
         try {
             int id = Integer.parseInt(itemId);
             
             ItemDB ib = new ItemDB();
             UserDB ub = new UserDB();
             
             Item item = ib.getItem(id);
             User owner = item.getOwner();
             
             if (!owner.getEmail().equals(user.getEmail())) {
                 return null;
             }
             ib.delete(item);
             
             User updatedUser = ub.getUser(item.getOwner().getEmail());
             return updatedUser;
         } catch (Exception e) {
             return null;
         }
     }
}
