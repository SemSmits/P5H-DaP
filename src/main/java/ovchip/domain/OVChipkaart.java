package ovchip.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    @Column(name = "kaart_nummer")
    private int kaartNummer;

    @Temporal(TemporalType.DATE)
    @Column(name = "geldig_tot", nullable = false)
    private Date geldigTot;

    @Column(nullable = false)
    private int klasse;

    @Column(nullable = false)
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    public OVChipkaart() { }

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public int getKaartNummer() { return kaartNummer; }
    public void setKaartNummer(int n) { this.kaartNummer = n; }
    public Date getGeldigTot() { return geldigTot; }
    public void setGeldigTot(Date d) { this.geldigTot = d; }
    public int getKlasse() { return klasse; }
    public void setKlasse(int k) { this.klasse = k; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double s) { this.saldo = s; }
    public Reiziger getReiziger() { return reiziger; }
    public void setReiziger(Reiziger r) { this.reiziger = r; }

    @Override
    public String toString() {
        Integer rid = (reiziger != null) ? reiziger.getId() : null;
        return "OVChipkaart{#" + kaartNummer + ", klasse=" + klasse + ", saldo=" + saldo + ", rId=" + rid + "}";
    }
}
