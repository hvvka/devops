package pl.edu.pwr.twwo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.edu.pwr.twwo.domain.enumeration.FormaStudiow;
import pl.edu.pwr.twwo.domain.enumeration.JezykProwadzeniaStudiow;
import pl.edu.pwr.twwo.domain.enumeration.ProfilKsztalcenia;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProgramStudiow.
 */
@Entity
@Table(name = "program_studiow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProgramStudiow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "profil_ksztalcenia", nullable = false)
    private ProfilKsztalcenia profilKsztalcenia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_studiow", nullable = false)
    private FormaStudiow formaStudiow;

    @Column(name = "kierunek")
    private String kierunek;

    @Column(name = "specjalnosc")
    private String specjalnosc;

    @NotNull
    @Column(name = "wydzial", nullable = false)
    private String wydzial;

    @Enumerated(EnumType.STRING)
    @Column(name = "jezyk_prowadzenia_studiow")
    private JezykProwadzeniaStudiow jezykProwadzeniaStudiow;

    @NotNull
    @Column(name = "liczba_semestrow", nullable = false)
    private Long liczbaSemestrow;

    @NotNull
    @Column(name = "cykl_ksztalcenia", nullable = false)
    private String cyklKsztalcenia;

    @ManyToOne
    @JsonIgnoreProperties("programStudiows")
    private TypStudiow typStudiow;

    @ManyToMany(mappedBy = "programStudiows")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Przedmiot> przedmiots = new HashSet<>();

    @ManyToMany(mappedBy = "progamStudiows")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Dyscyplina> dyscyplinas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfilKsztalcenia getProfilKsztalcenia() {
        return profilKsztalcenia;
    }

    public ProgramStudiow profilKsztalcenia(ProfilKsztalcenia profilKsztalcenia) {
        this.profilKsztalcenia = profilKsztalcenia;
        return this;
    }

    public void setProfilKsztalcenia(ProfilKsztalcenia profilKsztalcenia) {
        this.profilKsztalcenia = profilKsztalcenia;
    }

    public FormaStudiow getFormaStudiow() {
        return formaStudiow;
    }

    public ProgramStudiow formaStudiow(FormaStudiow formaStudiow) {
        this.formaStudiow = formaStudiow;
        return this;
    }

    public void setFormaStudiow(FormaStudiow formaStudiow) {
        this.formaStudiow = formaStudiow;
    }

    public String getKierunek() {
        return kierunek;
    }

    public ProgramStudiow kierunek(String kierunek) {
        this.kierunek = kierunek;
        return this;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public String getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(String specjalnosc) {
        this.specjalnosc = specjalnosc;
    }

    public ProgramStudiow specjalnosc(String specjalnosc) {
        this.specjalnosc = specjalnosc;
        return this;
    }

    public String getWydzial() {
        return wydzial;
    }

    public ProgramStudiow wydzial(String wydzial) {
        this.wydzial = wydzial;
        return this;
    }

    public void setWydzial(String wydzial) {
        this.wydzial = wydzial;
    }

    public JezykProwadzeniaStudiow getJezykProwadzeniaStudiow() {
        return jezykProwadzeniaStudiow;
    }

    public ProgramStudiow jezykProwadzeniaStudiow(JezykProwadzeniaStudiow jezykProwadzeniaStudiow) {
        this.jezykProwadzeniaStudiow = jezykProwadzeniaStudiow;
        return this;
    }

    public void setJezykProwadzeniaStudiow(JezykProwadzeniaStudiow jezykProwadzeniaStudiow) {
        this.jezykProwadzeniaStudiow = jezykProwadzeniaStudiow;
    }

    public Long getLiczbaSemestrow() {
        return liczbaSemestrow;
    }

    public ProgramStudiow liczbaSemestrow(Long liczbaSemestrow) {
        this.liczbaSemestrow = liczbaSemestrow;
        return this;
    }

    public void setLiczbaSemestrow(Long liczbaSemestrow) {
        this.liczbaSemestrow = liczbaSemestrow;
    }

    public String getCyklKsztalcenia() {
        return cyklKsztalcenia;
    }

    public ProgramStudiow cyklKsztalcenia(String cyklKsztalcenia) {
        this.cyklKsztalcenia = cyklKsztalcenia;
        return this;
    }

    public void setCyklKsztalcenia(String cyklKsztalcenia) {
        this.cyklKsztalcenia = cyklKsztalcenia;
    }

    public TypStudiow getTypStudiow() {
        return typStudiow;
    }

    public ProgramStudiow typStudiow(TypStudiow typStudiow) {
        this.typStudiow = typStudiow;
        return this;
    }

    public void setTypStudiow(TypStudiow typStudiow) {
        this.typStudiow = typStudiow;
    }

    public Set<Przedmiot> getPrzedmiots() {
        return przedmiots;
    }

    public ProgramStudiow przedmiots(Set<Przedmiot> przedmiots) {
        this.przedmiots = przedmiots;
        return this;
    }

    public ProgramStudiow addPrzedmiot(Przedmiot przedmiot) {
        this.przedmiots.add(przedmiot);
        przedmiot.getProgramStudiows().add(this);
        return this;
    }

    public ProgramStudiow removePrzedmiot(Przedmiot przedmiot) {
        this.przedmiots.remove(przedmiot);
        przedmiot.getProgramStudiows().remove(this);
        return this;
    }

    public void setPrzedmiots(Set<Przedmiot> przedmiots) {
        this.przedmiots = przedmiots;
    }

    public Set<Dyscyplina> getDyscyplinas() {
        return dyscyplinas;
    }

    public ProgramStudiow dyscyplinas(Set<Dyscyplina> dyscyplinas) {
        this.dyscyplinas = dyscyplinas;
        return this;
    }

    public ProgramStudiow addDyscyplina(Dyscyplina dyscyplina) {
        this.dyscyplinas.add(dyscyplina);
        dyscyplina.getProgamStudiows().add(this);
        return this;
    }

    public ProgramStudiow removeDyscyplina(Dyscyplina dyscyplina) {
        this.dyscyplinas.remove(dyscyplina);
        dyscyplina.getProgamStudiows().remove(this);
        return this;
    }

    public void setDyscyplinas(Set<Dyscyplina> dyscyplinas) {
        this.dyscyplinas = dyscyplinas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramStudiow)) {
            return false;
        }
        return id != null && id.equals(((ProgramStudiow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProgramStudiow{" +
            "id=" + getId() +
            ", profilKsztalcenia='" + getProfilKsztalcenia() + "'" +
            ", formaStudiow='" + getFormaStudiow() + "'" +
            ", kierunek='" + getKierunek() + "'" +
            ", specjalnosc='" + getSpecjalnosc() + "'" +
            ", wydzial='" + getWydzial() + "'" +
            ", jezykProwadzeniaStudiow='" + getJezykProwadzeniaStudiow() + "'" +
            ", liczbaSemestrow=" + getLiczbaSemestrow() +
            ", cyklKsztalcenia='" + getCyklKsztalcenia() + "'" +
            "}";
    }
}
