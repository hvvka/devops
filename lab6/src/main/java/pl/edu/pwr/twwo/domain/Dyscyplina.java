package pl.edu.pwr.twwo.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dyscyplina.
 */
@Entity
@Table(name = "dyscyplina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dyscyplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa")
    private String nazwa;

    @NotNull
    @Column(name = "czy_wiodaca", nullable = false)
    private Boolean czyWiodaca;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dyscyplina_progam_studiow",
               joinColumns = @JoinColumn(name = "dyscyplina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "progam_studiow_id", referencedColumnName = "id"))
    private Set<ProgramStudiow> progamStudiows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Dyscyplina nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Boolean isCzyWiodaca() {
        return czyWiodaca;
    }

    public Dyscyplina czyWiodaca(Boolean czyWiodaca) {
        this.czyWiodaca = czyWiodaca;
        return this;
    }

    public void setCzyWiodaca(Boolean czyWiodaca) {
        this.czyWiodaca = czyWiodaca;
    }

    public Set<ProgramStudiow> getProgamStudiows() {
        return progamStudiows;
    }

    public Dyscyplina progamStudiows(Set<ProgramStudiow> programStudiows) {
        this.progamStudiows = programStudiows;
        return this;
    }

    public Dyscyplina addProgamStudiow(ProgramStudiow programStudiow) {
        this.progamStudiows.add(programStudiow);
        programStudiow.getDyscyplinas().add(this);
        return this;
    }

    public Dyscyplina removeProgamStudiow(ProgramStudiow programStudiow) {
        this.progamStudiows.remove(programStudiow);
        programStudiow.getDyscyplinas().remove(this);
        return this;
    }

    public void setProgamStudiows(Set<ProgramStudiow> programStudiows) {
        this.progamStudiows = programStudiows;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dyscyplina)) {
            return false;
        }
        return id != null && id.equals(((Dyscyplina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dyscyplina{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", czyWiodaca='" + isCzyWiodaca() + "'" +
            "}";
    }
}
