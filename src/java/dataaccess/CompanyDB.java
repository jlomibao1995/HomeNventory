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
    
    public void updateUser(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        Company company = user.getCompany();
        company.getUserList().add(user);
        
        try {
            trans.begin();
            em.merge(user);
            em.merge(company);
            trans.commit();
        } catch (Exception e) {

        } finally {
            em.close();
        }       
    }
    
    public void delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        Company company = user.getCompany();
        company.getUserList().remove(user);
        
        try {
            trans.begin();
            em.remove(em.merge(user));
            em.merge(company);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        } 
    }
}