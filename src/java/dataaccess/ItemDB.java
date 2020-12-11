package dataaccess;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Item;
import model.User;

/**
 *
 * @author Jean
 */
public class ItemDB {

    public Item getItem(int itemId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Item item = null;

        try {
            item = em.find(Item.class, itemId);
        } finally {
            em.close();
        }
        return item;
    }

    public List<Item> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            return items;
        } finally {
            em.close();
        }
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
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ItemDB.class.getName()).log(Level.WARNING, "Could not insert item");
        } finally {
            em.close();
        }
    }

    public void update(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(item);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ItemDB.class.getName()).log(Level.WARNING, "Could not update item");
        } finally {
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
            Logger.getLogger(ItemDB.class.getName()).log(Level.WARNING, "Could not delete item");
        } finally {
            em.close();
        }
    }
}
