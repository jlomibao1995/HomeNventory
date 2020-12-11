package dataaccess;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import model.Role;

/**
 *
 * @author Jean
 */
public class RoleDB {

    public List<Role> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Role> roles = null;

        try {
            roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
        } catch (Exception e) {
            Logger.getLogger(RoleDB.class.getName()).log(Level.WARNING, "Could not return result for {0}", Role.class.getName());
        } finally {
            em.close();
        }
        return roles;
    }

}
