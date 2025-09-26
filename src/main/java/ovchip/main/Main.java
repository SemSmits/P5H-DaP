package ovchip.main;

import ovchip.dao.OVChipkaartDAOHibernate;
import ovchip.dao.ReizigerDAOHibernate;
import ovchip.domain.Adres;
import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;
import ovchip.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        OVChipkaartDAOHibernate ovDao = new OVChipkaartDAOHibernate(sf);
        ReizigerDAOHibernate rDao = new ReizigerDAOHibernate(sf, ovDao);

        try {
            System.out.println("---- TEST DAO's (Hibernate) ----");

            // 1. Maak Reiziger + Adres
            Reiziger r1 = new Reiziger(77, "S", null, "Student", new Date());
            Adres a1 = new Adres(77, "3511AB", "10", "Nijverheidsweg", "Utrecht");
            r1.setAdres(a1);

            // 2. Maak OVChipkaarten en koppel ze
            OVChipkaart k1 = new OVChipkaart(10077, new Date(), 2, 25.0);
            OVChipkaart k2 = new OVChipkaart(10078, new Date(), 1, 15.5);

            r1.addOVChipkaart(k1);
            r1.addOVChipkaart(k2);

            // 3. Save Reiziger + Adres
            System.out.println("Saving reiziger...");
            rDao.save(r1);

            // 4. Save kaarten via ovDao
            System.out.println("Saving kaarten...");
            ovDao.save(k1);
            ovDao.save(k2);

            // 5. Find all reizigers
            System.out.println("\nAlle reizigers:");
            List<Reiziger> all = rDao.findAll();
            all.forEach(System.out::println);

            // 6. Find kaarten bij reiziger
            System.out.println("\nKaarten van reiziger 77:");
            ovDao.findByReiziger(r1).forEach(System.out::println);

            // 7. Update saldo
            k1.setSaldo(99.9);
            ovDao.update(k1);
            System.out.println("\nKaart 10077 na update:");
            ovDao.findByReiziger(r1).forEach(System.out::println);

            // 8. Delete reiziger (cascade verwijdert adres, kaarten via ovDao.delete in ReizigerDAO)
            System.out.println("\nDeleting reiziger 77...");
            rDao.delete(r1);

            // Check na delete
            System.out.println("\nAlle reizigers na delete:");
            rDao.findAll().forEach(System.out::println);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}
