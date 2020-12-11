package services;

import dataaccess.CompanyDB;
import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Company;
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

        try {
            User user = ub.getUser(email);
            String salt = user.getSalt();
            String hashPasswordAndSalt = PasswordUtil.hashAndSaltPassword(password, salt);

            if (user.getPassword().equals(hashPasswordAndSalt) && user.getActive()) {
                return user;
            }
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem occured while logging in for user {0}", email);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not return result for {0}", email);
        }

        return null;
    }

    public User getUser(String email) {
        try {
            UserDB ub = new UserDB();
            User user = ub.getUser(email);
            return user;
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not find user {0}", email);
            return null;
        }
    }

    public List<User> getUsers() {
        try {
            UserDB ub = new UserDB();
            List<User> users = ub.getAll();
            return users;
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not get users");
            return null;
        }
    }

    public boolean addUser(String email, String password, String firstName, String lastName, String companyId) {

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

            if (!companyId.equals("0")) {
                CompanyDB cd = new CompanyDB();
                Company company = cd.getCompany(Integer.parseInt(companyId));
                user.setCompany(company);
            }

            UserDB db = new UserDB();

            db.insert(user);
            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not add user");
            return false;
        }
    }

    public boolean addCompanyUser(String email, String password, String firstName, String lastName, Company company) {
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
            UserDB ud = new UserDB();
            user.setCompany(company);

            ud.insert(user);
            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not add company user");
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
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Could not register user");
            return false;
        }
    }

    public boolean activateUser(String uuid, String path, String url) {
        try {
            UserDB ub = new UserDB();
            User user = ub.getUserByActivateUUID(uuid);
            user.setActive(true);
            user.setActivateUserUuid(null);
            ub.update(user);
            String subject = "Home nVentory App Activate Account";
            String template = path + "/emailtemplates/activate.html";
            String link = url + "?login=login";

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
            GmailService.sendMail(user.getEmail(), subject, template, tags);
            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem activating user with uuid: {0}", uuid);
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
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem with resetting password for user: {0}", email);
            return false;
        }
    }

    public boolean changePassword(String uuid, String password, String confirmPassword) {
        if (password == null || password.equals("") || !password.equals(confirmPassword)) {
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
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem changing password for user with uuid: {0}", uuid);
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

            if (password != null && !password.equals("")) {
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
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem updating user account: {0}", email);
            return null;
        }
    }

    public User updateUser(String email, String firstName, String lastName, String password, String active, String companyId) {
        if (firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("")) {
            return null;
        }

        try {

            UserDB ub = new UserDB();
            User user = ub.getUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            if (password != null && !password.equals("")) {
                String salt = user.getSalt();
                String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
                user.setPassword(hashPassword);
            }

            if (active.equals("true")) {
                user.setActive(true);
            } else {
                user.setActive(false);
            }

            Company oldCompany = user.getCompany();

            if (companyId.equals("0")) {
                user.setCompany(null);
                ub.update(user, oldCompany);
            } else {
                CompanyDB cd = new CompanyDB();
                Company newCompany = cd.getCompany(Integer.parseInt(companyId));
                user.setCompany(newCompany);
                ub.update(user, oldCompany);
            }

            return user;

        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem updating user account: {0}", email);
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
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem deleting user account: {0}", email);
            return false;
        }
        return true;
    }

    public boolean changeRole(String email, int roleId) {

        try {
            UserDB ub = new UserDB();
            User user = ub.getUser(email);
            if (user.getCompany() == null && roleId == 3) {
                return false;
            }
            user.setRole(new Role(roleId));
            ub.update(user);

            return true;
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem changing role for user account: {0}", email);
            return false;
        }
    }

    public List<Company> getCompanies() {
        try {
            CompanyDB cb = new CompanyDB();
            List<Company> companies = cb.getCompanies();
            return companies;
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem getting companies");
            return null;
        }
    }

    public List<Role> getRoles() {
        try {
            RoleDB rb = new RoleDB();
            List<Role> roles = rb.getAll();
            return roles;
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Problem getting roles");
            return null;
        }
    }
}
