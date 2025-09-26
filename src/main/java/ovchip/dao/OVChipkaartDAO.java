package ovchip.dao;

import ovchip.domain.OVChipkaart;
import ovchip.domain.Reiziger;
import java.util.List;

public interface OVChipkaartDAO{
    boolean save(OVChipkaart k);
    boolean update(OVChipkaart k);
    boolean delete(OVChipkaart k);
    List<OVChipkaart> findByReiziger(Reiziger r);
    List<OVChipkaart> findAll();
}
