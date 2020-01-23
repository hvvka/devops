package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.TypStudiow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypStudiow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypStudiowRepository extends JpaRepository<TypStudiow, Long> {

}
