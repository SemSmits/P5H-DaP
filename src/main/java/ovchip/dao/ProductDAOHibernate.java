package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private final SessionFactory sessionFactory;

    public ProductDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(Product p) {
        Session s = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.save(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    @Override
    public boolean update(Product p) {
        Session s = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.update(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    @Override
    public boolean delete(Product p) {
        Session s = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Product managed = s.get(Product.class, p.getProductNummer());
            if (managed != null) {
                managed.getKaarten().clear();
                s.delete(managed);
            }
            tx.commit();
            return managed != null;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart k) {
        Session s = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            List<Product> res = s.createQuery(
                            "select p from Product p join p.kaarten k where k.kaartNummer = :id",
                            Product.class
                    ).setParameter("id", k.getKaartNummer())
                    .getResultList();
            tx.commit();
            return res;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    @Override
    public List<Product> findAll() {
        Session s = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            List<Product> res = s.createQuery("from Product", Product.class).getResultList();
            tx.commit();
            return res;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
}
