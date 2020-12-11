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

    public List<Company> getCompanies() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Company> companies = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return companies;
        } finally {
            em.close();
        }
    }

    public Company getCompany(int comapnyId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Company company = em.find(Company.class, comapnyId);
            return company;
        } finally {
            em.close();
        }
    }
}
