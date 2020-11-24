package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
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
}