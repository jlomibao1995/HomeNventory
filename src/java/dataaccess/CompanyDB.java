package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Company;
import model.User;

/**
 *
 * @author Jean
 */
public class CompanyDB {
    public List<Company> getCompanies() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Company> companies = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return companies;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Company getCompany(int comapnyId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Company company = em.find(Company.class, comapnyId);
            return company;
        } catch (Exception e) {
            return null;
        }finally {
            em.close();
        }
    }
}
