package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Product;

import java.util.List;

public interface ProductDAO {
    boolean save(Product p);
    boolean update(Product p);
    boolean delete(Product p);
    List<Product> findByOVChipkaart(OVChipkaart k);
    List<Product> findAll();
}

