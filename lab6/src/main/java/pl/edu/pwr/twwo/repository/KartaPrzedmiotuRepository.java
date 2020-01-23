package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.KartaPrzedmiotu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KartaPrzedmiotu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KartaPrzedmiotuRepository extends JpaRepository<KartaPrzedmiotu, Long> {

}
