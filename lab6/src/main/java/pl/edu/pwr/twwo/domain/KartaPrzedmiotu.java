package pl.edu.pwr.twwo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.edu.pwr.twwo.domain.enumeration.RodzajPrzedmiotu;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A KartaPrzedmiotu.
 */
@Entity
@Table(name = "karta_przedmiotu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KartaPrzedmiotu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "kod_przedmiotu", nullable = false)
    private String kodPrzedmiotu;

    @NotNull
    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    @NotNull
    @Column(name = "nazwa_ang", nullable = false)
    private String nazwaAng;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rodzaj_przedmiotu", nullable = false)
    private RodzajPrzedmiotu rodzajPrzedmiotu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodPrzedmiotu() {
        return kodPrzedmiotu;
    }

    public void setKodPrzedmiotu(String kodPrzedmiotu) {
        this.kodPrzedmiotu = kodPrzedmiotu;
    }

    public KartaPrzedmiotu kodPrzedmiotu(String kodPrzedmiotu) {
        this.kodPrzedmiotu = kodPrzedmiotu;
        return this;
    }

    public String getNazwa() {
        return nazwa;
    }

    public KartaPrzedmiotu nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwaAng() {
        return nazwaAng;
    }

    public void setNazwaAng(String nazwaAng) {
        this.nazwaAng = nazwaAng;
    }

    public KartaPrzedmiotu nazwaAng(String nazwaAng) {
        this.nazwaAng = nazwaAng;
        return this;
    }

    public RodzajPrzedmiotu getRodzajPrzedmiotu() {
        return rodzajPrzedmiotu;
    }

    public void setRodzajPrzedmiotu(RodzajPrzedmiotu rodzajPrzedmiotu) {
        this.rodzajPrzedmiotu = rodzajPrzedmiotu;
    }

    public KartaPrzedmiotu rodzajPrzedmiotu(RodzajPrzedmiotu rodzajPrzedmiotu) {
        this.rodzajPrzedmiotu = rodzajPrzedmiotu;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KartaPrzedmiotu)) {
            return false;
        }
        return id != null && id.equals(((KartaPrzedmiotu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "KartaPrzedmiotu{" +
            "id=" + getId() +
            ", kodPrzedmiotu='" + getKodPrzedmiotu() + "'" +
            ", nazwa='" + getNazwa() + "'" +
            ", nazwaAng='" + getNazwaAng() + "'" +
            ", rodzajPrzedmiotu='" + getRodzajPrzedmiotu() + "'" +
            "}";
    }
}
