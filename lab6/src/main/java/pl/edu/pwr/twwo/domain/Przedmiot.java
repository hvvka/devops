package pl.edu.pwr.twwo.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Przedmiot.
 */
@Entity
@Table(name = "przedmiot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Przedmiot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nr_semestru", nullable = false)
    private Long nrSemestru;

    @Column(name = "nazwa")
    private String nazwa;

    @ManyToOne
    @JsonIgnoreProperties("przedmiots")
    private KartaPrzedmiotu kartaPrzedmiotu;

    @ManyToOne
    @JsonIgnoreProperties("przedmiots")
    private OpiekunPrzedmiotu opiekunPrzedmiotu;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "przedmiot_program_studiow",
               joinColumns = @JoinColumn(name = "przedmiot_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "program_studiow_id", referencedColumnName = "id"))
    private Set<ProgramStudiow> programStudiows = new HashSet<>();

    @ManyToMany(mappedBy = "przedmiots")
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

    public Long getNrSemestru() {
        return nrSemestru;
    }

    public Przedmiot nrSemestru(Long nrSemestru) {
        this.nrSemestru = nrSemestru;
        return this;
    }

    public void setNrSemestru(Long nrSemestru) {
        this.nrSemestru = nrSemestru;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Przedmiot nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public KartaPrzedmiotu getKartaPrzedmiotu() {
        return kartaPrzedmiotu;
    }

    public Przedmiot kartaPrzedmiotu(KartaPrzedmiotu kartaPrzedmiotu) {
        this.kartaPrzedmiotu = kartaPrzedmiotu;
        return this;
    }

    public void setKartaPrzedmiotu(KartaPrzedmiotu kartaPrzedmiotu) {
        this.kartaPrzedmiotu = kartaPrzedmiotu;
    }

    public OpiekunPrzedmiotu getOpiekunPrzedmiotu() {
        return opiekunPrzedmiotu;
    }

    public Przedmiot opiekunPrzedmiotu(OpiekunPrzedmiotu opiekunPrzedmiotu) {
        this.opiekunPrzedmiotu = opiekunPrzedmiotu;
        return this;
    }

    public void setOpiekunPrzedmiotu(OpiekunPrzedmiotu opiekunPrzedmiotu) {
        this.opiekunPrzedmiotu = opiekunPrzedmiotu;
    }

    public Set<ProgramStudiow> getProgramStudiows() {
        return programStudiows;
    }

    public Przedmiot programStudiows(Set<ProgramStudiow> programStudiows) {
        this.programStudiows = programStudiows;
        return this;
    }

    public Przedmiot addProgramStudiow(ProgramStudiow programStudiow) {
        this.programStudiows.add(programStudiow);
        programStudiow.getPrzedmiots().add(this);
        return this;
    }

    public Przedmiot removeProgramStudiow(ProgramStudiow programStudiow) {
        this.programStudiows.remove(programStudiow);
        programStudiow.getPrzedmiots().remove(this);
        return this;
    }

    public void setProgramStudiows(Set<ProgramStudiow> programStudiows) {
        this.programStudiows = programStudiows;
    }

    public Set<EfektKsztalcenia> getEfektKsztalcenias() {
        return efektKsztalcenias;
    }

    public Przedmiot efektKsztalcenias(Set<EfektKsztalcenia> efektKsztalcenias) {
        this.efektKsztalcenias = efektKsztalcenias;
        return this;
    }

    public Przedmiot addEfektKsztalcenia(EfektKsztalcenia efektKsztalcenia) {
        this.efektKsztalcenias.add(efektKsztalcenia);
        efektKsztalcenia.getPrzedmiots().add(this);
        return this;
    }

    public Przedmiot removeEfektKsztalcenia(EfektKsztalcenia efektKsztalcenia) {
        this.efektKsztalcenias.remove(efektKsztalcenia);
        efektKsztalcenia.getPrzedmiots().remove(this);
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
        if (!(o instanceof Przedmiot)) {
            return false;
        }
        return id != null && id.equals(((Przedmiot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Przedmiot{" +
            "id=" + getId() +
            ", nrSemestru=" + getNrSemestru() +
            ", nazwa='" + getNazwa() + "'" +
            "}";
    }
}
