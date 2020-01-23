package pl.edu.pwr.twwo.repository;

import pl.edu.pwr.twwo.domain.EfektKsztalcenia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EfektKsztalcenia entity.
 */
@Repository
public interface EfektKsztalceniaRepository extends JpaRepository<EfektKsztalcenia, Long> {

    @Query(value = "select distinct efektKsztalcenia from EfektKsztalcenia efektKsztalcenia left join fetch efektKsztalcenia.przedmiots left join fetch efektKsztalcenia.efektMinisterialnies",
        countQuery = "select count(distinct efektKsztalcenia) from EfektKsztalcenia efektKsztalcenia")
    Page<EfektKsztalcenia> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct efektKsztalcenia from EfektKsztalcenia efektKsztalcenia left join fetch efektKsztalcenia.przedmiots left join fetch efektKsztalcenia.efektMinisterialnies")
    List<EfektKsztalcenia> findAllWithEagerRelationships();

    @Query("select efektKsztalcenia from EfektKsztalcenia efektKsztalcenia left join fetch efektKsztalcenia.przedmiots left join fetch efektKsztalcenia.efektMinisterialnies where efektKsztalcenia.id =:id")
    Optional<EfektKsztalcenia> findOneWithEagerRelationships(@Param("id") Long id);

}
