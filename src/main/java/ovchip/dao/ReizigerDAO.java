package ovchip.dao;

import ovchip.domain.Reiziger;
import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger r);
    boolean update(Reiziger r);
    boolean delete(Reiziger r);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(Date date);
    List<Reiziger> findAll();
}
