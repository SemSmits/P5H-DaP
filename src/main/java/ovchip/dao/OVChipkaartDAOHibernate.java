package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private final SessionFactory sf;

    public OVChipkaartDAOHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public boolean save(OVChipkaart card) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(card);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(OVChipkaart card) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(card);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(OVChipkaart card) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(card);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }


    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) {
        try (Session s = sf.openSession()) {
            return s.createQuery(
                            "from OVChipkaart k where k.reiziger.id = :rid order by k.kaartNummer",
                            OVChipkaart.class
                    ).setParameter("rid", r.getId())
                    .list();
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<OVChipkaart> result = session
                    .createQuery("from OVChipkaart", OVChipkaart.class)
                    .getResultList();
            tx.commit();
            return result;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

}
