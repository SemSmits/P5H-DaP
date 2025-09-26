package ovchip.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ovchip.dao.AdresDAO;
import ovchip.domain.Adres;
import ovchip.domain.Reiziger;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private final SessionFactory factory;

    public AdresDAOHibernate(SessionFactory factory) { this.factory = factory; }

    @Override
    public boolean save(Adres adres) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Adres.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger r) {
        try (Session s = factory.openSession()) {
            return s.createQuery(
                            "from Adres a where a.reiziger.id = :rid", Adres.class)
                    .setParameter("rid", r.getId())
                    .uniqueResult();
        }
    }

    @Override public List<Adres> findAll() {
        try (Session s = factory.openSession()) {
            return s.createQuery("from Adres", Adres.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
