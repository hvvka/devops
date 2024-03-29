package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.Zajecie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the Zajecie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZajecieRepository extends JpaRepository<Zajecie, Long> {
    Set<Zajecie> findAllByPrzedmiotId(Long przedmiotId);
}
