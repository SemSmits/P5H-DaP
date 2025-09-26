package ovchip.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "adres")
public class Adres {

    @Id
    @Column(name = "adres_id")
    private int id;

    @Column(length = 10, nullable = false)
    private String postcode;

    @Column(name = "huisnummer", length = 10, nullable = false)
    private String huisnummer;

    @Column(length = 255, nullable = false)
    private String straat;

    @Column(length = 255, nullable = false)
    private String woonplaats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id", unique = true)
    private Reiziger reiziger;

    public Adres() { }

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.id = id; this.postcode = postcode; this.huisnummer = huisnummer;
        this.straat = straat; this.woonplaats = woonplaats;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String p) { this.postcode = p; }
    public String getHuisnummer() { return huisnummer; }
    public void setHuisnummer(String h) { this.huisnummer = h; }
    public String getStraat() { return straat; }
    public void setStraat(String s) { this.straat = s; }
    public String getWoonplaats() { return woonplaats; }
    public void setWoonplaats(String w) { this.woonplaats = w; }
    public Reiziger getReiziger() { return reiziger; }
    public void setReiziger(Reiziger r) { this.reiziger = r; }

    @Override
    public String toString() {
        Integer rid = (reiziger != null) ? reiziger.getId() : null;
        return "Adres{#" + id + ", reiziger_id=" + rid + "}";
    }
}
