package ovchip.dao;


import ovchip.domain.Adres;
import ovchip.domain.Reiziger;

import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
    Adres findById(int id);
    Adres findByReiziger(Reiziger r);
    List<Adres> findAll();
}
