package pl.edu.pwr.twwo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.edu.pwr.twwo.domain.enumeration.TypEfektuMinisterialnego;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EfektMinisterialny.
 */
@Entity
@Table(name = "efekt_ministerialny")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EfektMinisterialny implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "kod_efektu", nullable = false)
    private String kodEfektu;

    @NotNull
    @Column(name = "poziom_efektu", nullable = false)
    private Long poziomEfektu;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "typ_efektu_ministerialnego", nullable = false)
    private TypEfektuMinisterialnego typEfektuMinisterialnego;

    @ManyToMany(mappedBy = "efektMinisterialnies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<EfektKsztalcenia> efektKsztalcenias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodEfektu() {
        return kodEfektu;
    }

    public void setKodEfektu(String kodEfektu) {
        this.kodEfektu = kodEfektu;
    }

    public EfektMinisterialny kodEfektu(String kodEfektu) {
        this.kodEfektu = kodEfektu;
        return this;
    }

    public Long getPoziomEfektu() {
        return poziomEfektu;
    }

    public EfektMinisterialny poziomEfektu(Long poziomEfektu) {
        this.poziomEfektu = poziomEfektu;
        return this;
    }

    public void setPoziomEfektu(Long poziomEfektu) {
        this.poziomEfektu = poziomEfektu;
    }

    public TypEfektuMinisterialnego getTypEfektuMinisterialnego() {
        return typEfektuMinisterialnego;
    }

    public EfektMinisterialny typEfektuMinisterialnego(TypEfektuMinisterialnego typEfektuMinisterialnego) {
        this.typEfektuMinisterialnego = typEfektuMinisterialnego;
        return this;
    }

    public void setTypEfektuMinisterialnego(TypEfektuMinisterialnego typEfektuMinisterialnego) {
        this.typEfektuMinisterialnego = typEfektuMinisterialnego;
    }

    public Set<EfektKsztalcenia> getEfektKsztalcenias() {
        return efektKsztalcenias;
    }

    public EfektMinisterialny efektKsztalcenias(Set<EfektKsztalcenia> efektKsztalcenias) {
        this.efektKsztalcenias = efektKsztalcenias;
        return this;
    }

    public EfektMinisterialny addEfektKsztalcenia(EfektKsztalcenia efektKsztalcenia) {
        this.efektKsztalcenias.add(efektKsztalcenia);
        efektKsztalcenia.getEfektMinisterialnies().add(this);
        return this;
    }

    public EfektMinisterialny removeEfektKsztalcenia(EfektKsztalcenia efektKsztalcenia) {
        this.efektKsztalcenias.remove(efektKsztalcenia);
        efektKsztalcenia.getEfektMinisterialnies().remove(this);
        return this;
    }

    public void setEfektKsztalcenias(Set<EfektKsztalcenia> efektKsztalcenias) {
        this.efektKsztalcenias = efektKsztalcenias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfektMinisterialny)) {
            return false;
        }
        return id != null && id.equals(((EfektMinisterialny) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EfektMinisterialny{" +
            "id=" + getId() +
            ", kodEfektu='" + getKodEfektu() + "'" +
            ", poziomEfektu=" + getPoziomEfektu() +
            ", typEfektuMinisterialnego='" + getTypEfektuMinisterialnego() + "'" +
            "}";
    }
}
