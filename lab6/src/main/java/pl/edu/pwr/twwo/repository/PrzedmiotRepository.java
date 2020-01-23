package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.Przedmiot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Przedmiot entity.
 */
@Repository
public interface PrzedmiotRepository extends JpaRepository<Przedmiot, Long> {

    @Query(value = "select distinct przedmiot from Przedmiot przedmiot left join fetch przedmiot.programStudiows",
        countQuery = "select count(distinct przedmiot) from Przedmiot przedmiot")
    Page<Przedmiot> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct przedmiot from Przedmiot przedmiot left join fetch przedmiot.programStudiows")
    List<Przedmiot> findAllWithEagerRelationships();

    @Query("select przedmiot from Przedmiot przedmiot left join fetch przedmiot.programStudiows where przedmiot.id =:id")
    Optional<Przedmiot> findOneWithEagerRelationships(@Param("id") Long id);

}
