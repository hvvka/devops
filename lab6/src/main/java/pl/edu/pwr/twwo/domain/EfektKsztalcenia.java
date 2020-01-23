package pl.edu.pwr.twwo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EfektKsztalcenia.
 */
@Entity
@Table(name = "efekt_ksztalcenia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EfektKsztalcenia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "opis", nullable = false)
    private String opis;

    @ManyToOne
    @JsonIgnoreProperties("efektKsztalcenias")
    private ProgramStudiow programStudiow;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "efekt_ksztalcenia_przedmiot",
               joinColumns = @JoinColumn(name = "efekt_ksztalcenia_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "przedmiot_id", referencedColumnName = "id"))
    private Set<Przedmiot> przedmiots = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "efekt_ksztalcenia_efekt_ministerialny",
               joinColumns = @JoinColumn(name = "efekt_ksztalcenia_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "efekt_ministerialny_id", referencedColumnName = "id"))
    private Set<EfektMinisterialny> efektMinisterialnies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public EfektKsztalcenia opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public ProgramStudiow getProgramStudiow() {
        return programStudiow;
    }

    public EfektKsztalcenia programStudiow(ProgramStudiow programStudiow) {
        this.programStudiow = programStudiow;
        return this;
    }

    public void setProgramStudiow(ProgramStudiow programStudiow) {
        this.programStudiow = programStudiow;
    }

    public Set<Przedmiot> getPrzedmiots() {
        return przedmiots;
    }

    public EfektKsztalcenia przedmiots(Set<Przedmiot> przedmiots) {
        this.przedmiots = przedmiots;
        return this;
    }

    public EfektKsztalcenia addPrzedmiot(Przedmiot przedmiot) {
        this.przedmiots.add(przedmiot);
        przedmiot.getEfektKsztalcenias().add(this);
        return this;
    }

    public EfektKsztalcenia removePrzedmiot(Przedmiot przedmiot) {
        this.przedmiots.remove(przedmiot);
        przedmiot.getEfektKsztalcenias().remove(this);
        return this;
    }

    public void setPrzedmiots(Set<Przedmiot> przedmiots) {
        this.przedmiots = przedmiots;
    }

    public Set<EfektMinisterialny> getEfektMinisterialnies() {
        return efektMinisterialnies;
    }

    public EfektKsztalcenia efektMinisterialnies(Set<EfektMinisterialny> efektMinisterialnies) {
        this.efektMinisterialnies = efektMinisterialnies;
        return this;
    }

    public EfektKsztalcenia addEfektMinisterialny(EfektMinisterialny efektMinisterialny) {
        this.efektMinisterialnies.add(efektMinisterialny);
        efektMinisterialny.getEfektKsztalcenias().add(this);
        return this;
    }

    public EfektKsztalcenia removeEfektMinisterialny(EfektMinisterialny efektMinisterialny) {
        this.efektMinisterialnies.remove(efektMinisterialny);
        efektMinisterialny.getEfektKsztalcenias().remove(this);
        return this;
    }

    public void setEfektMinisterialnies(Set<EfektMinisterialny> efektMinisterialnies) {
        this.efektMinisterialnies = efektMinisterialnies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EfektKsztalcenia)) {
            return false;
        }
        return id != null && id.equals(((EfektKsztalcenia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EfektKsztalcenia{" +
            "id=" + getId() +
            ", opis='" + getOpis() + "'" +
            "}";
    }
}
