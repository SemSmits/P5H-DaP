package ovchip.domain;

import java.math.BigDecimal;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_nummer", nullable = false)
    private int productNummer;

    @Column(name = "naam", length = 30, nullable = false)
    private String naam;

    @Column(name = "beschrijving", length = 512)
    private String beschrijving;

    @Column(name = "prijs", precision = 16, scale = 2, nullable = false)
    private BigDecimal prijs;

    @ManyToMany(mappedBy = "products")
    private List<OVChipkaart> kaarten = new ArrayList<>();

    protected Product() {
    }

    public Product(int productNummer, String naam, String beschrijving, BigDecimal prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public List<OVChipkaart> getKaarten() {
        return kaarten;
    }

    public void addKaart(OVChipkaart k) {
        if (k == null || kaarten.contains(k)) return;
        kaarten.add(k);
        k.getProducts().add(this);
    }

    public void removeKaart(OVChipkaart k) {
        if (k == null) return;
        if (kaarten.remove(k)) k.getProducts().remove(this);
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return "Product{#" + productNummer +
                ", naam='" + naam + '\'' +
                ", prijs=" + prijs +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return productNummer == ((Product) o).productNummer;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productNummer);
    }
}
