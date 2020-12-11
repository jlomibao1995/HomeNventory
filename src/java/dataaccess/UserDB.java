package dataaccess;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Company;
import model.User;

/**
 *
 * @author Jean
 */
public class UserDB {

    public User getUser(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, email);

        } finally {
            em.close();
        }

        return user;
    }

    public User getUserByActivateUUID(String uuid) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByActivateUserUuid", User.class).setParameter("activateUserUuid", uuid).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }

    public User getUserByUUID(String uuid) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = null;
        try {
            user = em.createNamedQuery("User.findByResetPasswordUuid", User.class).setParameter("resetPasswordUuid", uuid).getSingleResult();
        } finally {
            em.close();
        }
        return user;
    }

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<User> users = null;
        try {
            users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public void insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(user);
            Company company = user.getCompany();

            if (company != null) {
                company.getUserList().add(user);
                em.merge(company);
            }

            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.WARNING, "Could not insert user");
        } finally {
            em.close();
        }
    }

    public void update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.WARNING, "Could not update user");
        } finally {
            em.close();
        }
    }

    public void update(User user, Company oldCompany) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);

            Company company = user.getCompany();

            if (company != null) {
                company.getUserList().add(user);
                em.merge(company);
            }

            if (oldCompany != null) {
                oldCompany.getUserList().remove(user);
                em.merge(oldCompany);
            }

            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.WARNING, "Could not update user");
        } finally {
            em.close();
        }
    }

    public void delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(user));
            Company company = user.getCompany();

            if (company != null) {
                company.getUserList().remove(user);
                em.merge(company);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.WARNING, "Could not delete user");
        } finally {
            em.close();
        }
    }
}
