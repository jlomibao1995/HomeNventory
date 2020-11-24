package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Item;
import model.User;

/**
 *
 * @author Jean
 */
public class ItemDB {
    
    public Item getItem(int itemId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Item item = null;
        
        try {
            item = em.find(Item.class, itemId);            
        } finally {
            em.close();
        }
        return item;
    }
    
    public void insert(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        User owner = item.getOwner();
        owner.getItemList().add(item);
        
        try {
            trans.begin();
            em.persist(item);
            em.merge(owner);
            trans.commit();
        }catch (Exception e) {
            trans.rollback();
        }finally {
            em.close();
        }
    }
    
    public void update(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(item);
            //em.merge(item.getOwner());
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }finally {
            em.close();
        }
    }
    
    public void delete(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            User user = item.getOwner();
            user.getItemList().remove(item);
            trans.begin();
            em.remove(em.merge(item));
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        }finally {
            em.close();
        }
    }
}
