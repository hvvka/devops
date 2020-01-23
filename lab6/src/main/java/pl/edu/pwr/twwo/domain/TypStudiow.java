package pl.edu.pwr.twwo.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TypStudiow.
 */
@Entity
@Table(name = "typ_studiow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypStudiow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    @Column(name = "stopien_studiow")
    private String stopienStudiow;

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

    public TypStudiow nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getStopienStudiow() {
        return stopienStudiow;
    }

    public TypStudiow stopienStudiow(String stopienStudiow) {
        this.stopienStudiow = stopienStudiow;
        return this;
    }

    public void setStopienStudiow(String stopienStudiow) {
        this.stopienStudiow = stopienStudiow;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypStudiow)) {
            return false;
        }
        return id != null && id.equals(((TypStudiow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypStudiow{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", stopienStudiow='" + getStopienStudiow() + "'" +
            "}";
    }
}
