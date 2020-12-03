package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;

/**
 *
 * @author Jean
 */
public class AccountService {

    public User login(String email, String password) {
        if (email == null || email.equals("") || password.equals("") || password == null) {
            return null;
        }
        
        UserDB ub = new UserDB();
        User user = ub.getUser(email);

        if (user != null && user.getActive()) {
            try {
                String salt = user.getSalt();
                String hashPasswordAndSalt = PasswordUtil.hashAndSaltPassword(password, salt);
                
                if (user.getPassword().equals(hashPasswordAndSalt)) {
                    return user;
                }
                return null;
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public User getUser(String email) {
        UserDB ub = new UserDB();
        User user = ub.getUser(email);
        return user;
    }

    public List<User> getUsers() {
        UserDB ub = new UserDB();
        List<User> users = ub.getAll();
        return users;
    }

    public boolean addUser(String email, String password, String firstName, String lastName) {

        if (email == null || email.equals("") || firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("") || password == null || password.equals("")) {
            return false;
        }

        try {
            String salt = PasswordUtil.getSalt();
            String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);

            User user = new User(email, true, firstName, lastName, salt, hashPassword);
            Role r = new Role(2);
            user.setRole(r);
            UserDB db = new UserDB();

            db.insert(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registerUser(String email, String password, String firstName, String lastName, String path, String url) {
        if (email == null || email.equals("") || firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("") || password == null || password.equals("")) {
            return false;
        }

        try {
            String salt = PasswordUtil.getSalt();
            String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
            
            User user = new User(email, false, firstName, lastName, salt, hashPassword);
            Role r = new Role(2);
            user.setRole(r);

            String uuid = UUID.randomUUID().toString();
            user.setActivateUserUuid(uuid);
            UserDB db = new UserDB();

            String subject = "Home nVentory App Activate Account";
            String template = path + "/emailtemplates/activate.html";
            String link = url + "?uuid=" + uuid;

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);

            db.insert(user);
            GmailService.sendMail(email, subject, template, tags);
            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean activateUser(String uuid) {
        UserDB ub = new UserDB();
        User user = ub.getUserByActivateUUID(uuid);
        user.setActive(true);
        user.setActivateUserUuid(null);
        
        try {
            ub.update(user);
            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
        public boolean resetPassword(String email, String path, String url) {
        UserDB ub = new UserDB();
        try {
            User user = ub.getUser(email);
            String uuid = UUID.randomUUID().toString();
            user.setResetPasswordUuid(uuid);
            ub.update(user);
            
            String subject = "Home nVentory Password Reset";
            String template = path + "/emailtemplates/resetpassword.html";
            String link = url + "?uuid=" + uuid;
            
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
            
            GmailService.sendMail(email, subject, template, tags);
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean changePassword(String uuid, String password, String confirmPassword)
    {
        if (password == null || !password.equals(confirmPassword)) {
            return false;
        }
        
        UserDB ub = new UserDB();
        try {
            User user = ub.getUserByUUID(uuid);
            String salt = user.getSalt();
            String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
            user.setPassword(hashPassword);
            user.setResetPasswordUuid(null);
            ub.update(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User updateAccount(String email, String firstName, String lastName, String password, String confirmPassword, String active) {
        if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("")) {
            return null;
        }

        if (!password.equals(confirmPassword)) {
            return null;
        }
        try {

            UserDB ub = new UserDB();
            User user = ub.getUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            if (password != null) {
                String salt = user.getSalt();
                String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
                user.setPassword(hashPassword);
            }

            if (!active.equals("true")) {
                user.setActive(false);
            }

            ub.update(user);
            return user;

        } catch (Exception e) {
            return null;
        }
    }

    public User updateUser(String email, String firstName, String lastName, String password, String active, String roleId) {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("") || password == null || password.equals("")) {
            return null;
        }

        try {

            UserDB ub = new UserDB();
            User user = ub.getUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            String salt = user.getSalt();
            String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
            user.setPassword(hashPassword);

            if (active.equals("true")) {
                user.setActive(true);
            } else {
                user.setActive(false);
            }

            int id = Integer.parseInt(roleId);
            user.setRole(new Role(id));

            ub.update(user);
            return user;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(String email, String accountEmail) {
        if (email == null || email.equals("")) {
            return false;
        }

        try {
            UserDB ub = new UserDB();
            User user = ub.getUser(email);

            if (user.getEmail().equals(accountEmail)) {
                return false;
            }

            ub.delete(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Role> getRoles() {
        RoleDB rb = new RoleDB();
        List<Role> roles = rb.getAll();
        return roles;
    }
}
