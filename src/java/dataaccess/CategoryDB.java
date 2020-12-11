package dataaccess;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Category;

/**
 *
 * @author Jean
 */
public class CategoryDB {

    public List<Category> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Category> list = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    public Category getCategory(int categoryId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Category category = em.find(Category.class, categoryId);
            return category;
        } finally {
            em.close();
        }
    }

    public void insert(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(CategoryDB.class.getName()).log(Level.WARNING, "Problem inserting category encountered.");
        } finally {
            em.close();
        }
    }

    public void update(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(category);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(CategoryDB.class.getName()).log(Level.WARNING, "Problem updating category encountered.");
        } finally {
            em.close();
        }
    }
}
