package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.ProgramStudiow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProgramStudiow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramStudiowRepository extends JpaRepository<ProgramStudiow, Long> {

}
