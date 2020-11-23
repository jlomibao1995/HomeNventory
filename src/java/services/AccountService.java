package services;

import dataaccess.UserDB;
import model.Role;
import model.User;

/**
 *
 * @author Jean
 */
public class AccountService {
    
    public User login(String email, String password) {
        if (email == null || email.equals("") || password.equals("") || password == null)
        {
            return null;
        }
        UserDB ub = new UserDB();
        User user = ub.getUser(email);
        
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public boolean addUser(String email, String password, String firstName, String lastName) {
        
        if (email == null || email.equals("") || firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("") || password == null || password.equals("")) {
            return false;
        }
        
        User user = new User(email, true, firstName, lastName, password);
        Role r = new Role(2);
        user.setRole(r);
        UserDB db = new UserDB();
        
        try {
            db.insert(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
