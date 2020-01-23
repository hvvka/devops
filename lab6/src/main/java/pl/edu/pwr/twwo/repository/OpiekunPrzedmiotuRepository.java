package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.OpiekunPrzedmiotu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OpiekunPrzedmiotu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpiekunPrzedmiotuRepository extends JpaRepository<OpiekunPrzedmiotu, Long> {

}
