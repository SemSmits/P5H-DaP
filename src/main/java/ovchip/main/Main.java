package ovchip.main;

import ovchip.dao.*;
import ovchip.domain.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        ProductDAO pdao = new ProductDAOHibernate(factory);
        OVChipkaartDAOHibernate kdao = new OVChipkaartDAOHibernate(factory);
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(factory);

        try {
            // 1) Maak een reiziger aan
            Reiziger r = new Reiziger(1000, "S.", null, "Tester", Date.valueOf(LocalDate.of(2002, 3, 15)));
            rdao.save(r);

            // 2) Maak een kaart en sla op
            OVChipkaart k = new OVChipkaart(88880001, Date.valueOf("2028-12-31"), 2, 50.00);
            k.setReiziger(r);
            kdao.save(k);

            // 3) Maak producten en sla op
            Product p1 = new Product(901, "Altijd Vrij", "Vrij reizen", new BigDecimal("299.95"));
            Product p2 = new Product(902, "Dal Voordeel", "40% korting daluren", new BigDecimal("5.10"));
            pdao.save(p1);
            pdao.save(p2);

            // 4) Koppel kaart ↔ producten en update
            k.addProduct(p1);
            k.addProduct(p2);
            kdao.update(k);

            printProducts("Na koppelen (p1+p2)", pdao.findByOVChipkaart(k));

            // 5) Update links: verwijder p2, voeg p3 toe
            Product p3 = new Product(903, "Weekend Vrij", "Vrij reizen in weekend", new BigDecimal("31.00"));
            pdao.save(p3);
            k.removeProduct(p2);
            k.addProduct(p3);
            kdao.update(k);

            printProducts("Na update (p1+p3)", pdao.findByOVChipkaart(k));

            // 6) Delete p1 → koppelingen weg
            pdao.delete(p1);
            printProducts("Na delete p1", pdao.findByOVChipkaart(k));

            // 7) Cleanup
            kdao.delete(k);
            pdao.delete(p2);
            pdao.delete(p3);
            rdao.delete(r);

        } finally {
            factory.close();
        }
    }

    private static void printProducts(String title, List<Product> producten) {
        System.out.println("-- " + title + " (" + producten.size() + ") --");
        for (Product p : producten) {
            System.out.println(p);
        }
    }
}
