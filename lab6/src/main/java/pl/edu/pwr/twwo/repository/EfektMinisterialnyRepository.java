package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.EfektMinisterialny;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EfektMinisterialny entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EfektMinisterialnyRepository extends JpaRepository<EfektMinisterialny, Long> {

}
