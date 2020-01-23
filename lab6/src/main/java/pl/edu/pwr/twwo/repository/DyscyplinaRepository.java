package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.Dyscyplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dyscyplina entity.
 */
@Repository
public interface DyscyplinaRepository extends JpaRepository<Dyscyplina, Long> {

    @Query(value = "select distinct dyscyplina from Dyscyplina dyscyplina left join fetch dyscyplina.progamStudiows",
        countQuery = "select count(distinct dyscyplina) from Dyscyplina dyscyplina")
    Page<Dyscyplina> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dyscyplina from Dyscyplina dyscyplina left join fetch dyscyplina.progamStudiows")
    List<Dyscyplina> findAllWithEagerRelationships();

    @Query("select dyscyplina from Dyscyplina dyscyplina left join fetch dyscyplina.progamStudiows where dyscyplina.id =:id")
    Optional<Dyscyplina> findOneWithEagerRelationships(@Param("id") Long id);

}
