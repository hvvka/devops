package pl.edu.pwr.twwo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import pl.edu.pwr.twwo.domain.enumeration.FormaPrzedmiotu;

import pl.edu.pwr.twwo.domain.enumeration.ModulKsztalcenia;

import pl.edu.pwr.twwo.domain.enumeration.PoziomJezyka;

/**
 * A Zajecie.
 */
@Entity
@Table(name = "zajecie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zajecie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma", nullable = false)
    private FormaPrzedmiotu forma;

    @NotNull
    @Column(name = "e_cts", nullable = false)
    private Long eCTS;

    @NotNull
    @Column(name = "z_zu", nullable = false)
    private Long zZU;

    @NotNull
    @Column(name = "c_nps", nullable = false)
    private Long cNPS;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modul_ksztalcenia", nullable = false)
    private ModulKsztalcenia modulKsztalcenia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "poziom_jezyka", nullable = false)
    private PoziomJezyka poziomJezyka;

    @OneToOne
    @JoinColumn(unique = true)
    private Zajecie formaWiodaca;

    @ManyToOne
    @JsonIgnoreProperties("zajecies")
    private Zajecie grupaKursow;

    @ManyToOne
    @JsonIgnoreProperties("zajecies")
    private Przedmiot przedmiot;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaPrzedmiotu getForma() {
        return forma;
    }

    public Zajecie forma(FormaPrzedmiotu forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(FormaPrzedmiotu forma) {
        this.forma = forma;
    }

    public Long geteCTS() {
        return eCTS;
    }

    public Zajecie eCTS(Long eCTS) {
        this.eCTS = eCTS;
        return this;
    }

    public void seteCTS(Long eCTS) {
        this.eCTS = eCTS;
    }

    public Long getzZU() {
        return zZU;
    }

    public Zajecie zZU(Long zZU) {
        this.zZU = zZU;
        return this;
    }

    public void setzZU(Long zZU) {
        this.zZU = zZU;
    }

    public Long getcNPS() {
        return cNPS;
    }

    public Zajecie cNPS(Long cNPS) {
        this.cNPS = cNPS;
        return this;
    }

    public void setcNPS(Long cNPS) {
        this.cNPS = cNPS;
    }

    public ModulKsztalcenia getModulKsztalcenia() {
        return modulKsztalcenia;
    }

    public Zajecie modulKsztalcenia(ModulKsztalcenia modulKsztalcenia) {
        this.modulKsztalcenia = modulKsztalcenia;
        return this;
    }

    public void setModulKsztalcenia(ModulKsztalcenia modulKsztalcenia) {
        this.modulKsztalcenia = modulKsztalcenia;
    }

    public PoziomJezyka getPoziomJezyka() {
        return poziomJezyka;
    }

    public Zajecie poziomJezyka(PoziomJezyka poziomJezyka) {
        this.poziomJezyka = poziomJezyka;
        return this;
    }

    public void setPoziomJezyka(PoziomJezyka poziomJezyka) {
        this.poziomJezyka = poziomJezyka;
    }

    public Zajecie getFormaWiodaca() {
        return formaWiodaca;
    }

    public Zajecie formaWiodaca(Zajecie zajecie) {
        this.formaWiodaca = zajecie;
        return this;
    }

    public void setFormaWiodaca(Zajecie zajecie) {
        this.formaWiodaca = zajecie;
    }

    public Zajecie getGrupaKursow() {
        return grupaKursow;
    }

    public Zajecie grupaKursow(Zajecie zajecie) {
        this.grupaKursow = zajecie;
        return this;
    }

    public void setGrupaKursow(Zajecie zajecie) {
        this.grupaKursow = zajecie;
    }

    public Przedmiot getPrzedmiot() {
        return przedmiot;
    }

    public Zajecie przedmiot(Przedmiot przedmiot) {
        this.przedmiot = przedmiot;
        return this;
    }

    public void setPrzedmiot(Przedmiot przedmiot) {
        this.przedmiot = przedmiot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zajecie)) {
            return false;
        }
        return id != null && id.equals(((Zajecie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Zajecie{" +
            "id=" + getId() +
            ", forma='" + getForma() + "'" +
            ", eCTS=" + geteCTS() +
            ", zZU=" + getzZU() +
            ", cNPS=" + getcNPS() +
            ", modulKsztalcenia='" + getModulKsztalcenia() + "'" +
            ", poziomJezyka='" + getPoziomJezyka() + "'" +
            "}";
    }
}
