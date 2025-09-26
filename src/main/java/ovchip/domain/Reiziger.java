package ovchip.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {

    @Id
    @Column(name = "reiziger_id")
    private int id;

    @Column(length = 10, nullable = false)
    private String voorletters;

    @Column(length = 10)
    private String tussenvoegsel;

    @Column(length = 255, nullable = false)
    private String achternaam;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date geboortedatum;

    @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Adres adres;

    @OneToMany(mappedBy = "reiziger", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OVChipkaart> kaarten = new ArrayList<>();

    public Reiziger() { }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getVoorletters() { return voorletters; }
    public void setVoorletters(String v) { this.voorletters = v; }
    public String getTussenvoegsel() { return tussenvoegsel; }
    public void setTussenvoegsel(String t) { this.tussenvoegsel = t; }
    public String getAchternaam() { return achternaam; }
    public void setAchternaam(String a) { this.achternaam = a; }
    public Date getGeboortedatum() { return geboortedatum; }
    public void setGeboortedatum(Date d) { this.geboortedatum = d; }

    public Adres getAdres() { return adres; }
    public void setAdres(Adres adres) {
        this.adres = adres;
        if (adres != null) adres.setReiziger(this);
    }

    public List<OVChipkaart> getKaarten() { return kaarten; }

    public void addOVChipkaart(OVChipkaart k) {
        if (k == null) return;
        if (!kaarten.contains(k)) kaarten.add(k);
        k.setReiziger(this);
    }

    public void removeOVChipkaart(OVChipkaart k) {
        if (k == null) return;
        kaarten.remove(k);
        if (k.getReiziger() == this) k.setReiziger(null);
    }

    @Override
    public String toString() {
        String naam = (tussenvoegsel == null || tussenvoegsel.isBlank())
                ? (voorletters + " " + achternaam)
                : (voorletters + " " + tussenvoegsel + " " + achternaam);

        String adresStr = (adres != null) ? ", " + adres.toString() : "";
        String kaartStr = kaarten.isEmpty() ? ", kaarten=[]"
                : ", kaarten=" + kaarten.stream().map(k -> "#" + k.getKaartNummer()).toList();

        return "Reiziger{#" + id + " " + naam + ", geb=" + geboortedatum + adresStr + kaartStr + "}";
    }
}
