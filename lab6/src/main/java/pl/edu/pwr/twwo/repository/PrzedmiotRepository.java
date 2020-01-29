package pl.edu.pwr.twwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.twwo.domain.Przedmiot;


/**
 * Spring Data  repository for the Przedmiot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrzedmiotRepository extends JpaRepository<Przedmiot, Long> {

}
