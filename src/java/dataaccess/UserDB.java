package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.User;

/**
 *
 * @author Jean
 */
public class UserDB {
    
    public User getUser(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
            
        } finally {
            em.close();
        }
    }
    
    public void insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } finally {
            trans.rollback();
            em.close();
        }
    }
}
