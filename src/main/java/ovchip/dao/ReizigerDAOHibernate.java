package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final SessionFactory sf;
    private OVChipkaartDAO ovDao;

    public ReizigerDAOHibernate(SessionFactory sf) {
        this.sf = sf;
        this.ovDao = ovDao;
    }

    @Override
    public boolean save(Reiziger r) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();
            s.persist(r);
            tx.commit();
            return true;
        } catch (Exception e) { if (tx != null) tx.rollback(); e.printStackTrace(); return false; }
    }

    @Override
    public boolean update(Reiziger r) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();
            s.merge(r);
            tx.commit();
            return true;
        } catch (Exception e) { if (tx != null) tx.rollback(); e.printStackTrace(); return false; }
    }

    @Override
    public boolean delete(Reiziger r) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();

            List<OVChipkaart> kaarten = s.createQuery(
                            "from OVChipkaart k where k.reiziger.id = :rid", OVChipkaart.class)
                    .setParameter("rid", r.getId())
                    .list();
            for (OVChipkaart k : kaarten) ovDao.delete(k);

            Reiziger managed = s.get(Reiziger.class, r.getId());
            if (managed != null) s.remove(managed);

            tx.commit();
            return true;
        } catch (Exception e) { if (tx != null) tx.rollback(); e.printStackTrace(); return false; }
    }

    @Override
    public Reiziger findById(int id) {
        try (Session s = sf.openSession()) {
            Reiziger r = s.get(Reiziger.class, id);

            if (r != null) {
                r.getKaarten().size();
                if (r.getAdres() != null) r.getAdres().getId();
            }
            return r;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        try (Session s = sf.openSession()) {
            List<Reiziger> list = s.createQuery(
                            "from Reiziger r where r.geboortedatum = :d order by r.id", Reiziger.class)
                    .setParameter("d", date)
                    .list();
            list.forEach(r -> { r.getKaarten().size(); if (r.getAdres()!=null) r.getAdres().getId(); });
            return list;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try (Session s = sf.openSession()) {
            List<Reiziger> list = s.createQuery("from Reiziger r order by r.id", Reiziger.class).list();
            list.forEach(r -> { r.getKaarten().size(); if (r.getAdres()!=null) r.getAdres().getId(); });
            return list;
        }
    }
}
