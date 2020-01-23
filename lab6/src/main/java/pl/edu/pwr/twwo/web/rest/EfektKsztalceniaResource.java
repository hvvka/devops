package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.EfektKsztalcenia;
import pl.edu.pwr.twwo.repository.EfektKsztalceniaRepository;
import pl.edu.pwr.twwo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.EfektKsztalcenia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EfektKsztalceniaResource {

    private final Logger log = LoggerFactory.getLogger(EfektKsztalceniaResource.class);

    private static final String ENTITY_NAME = "efektKsztalcenia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EfektKsztalceniaRepository efektKsztalceniaRepository;

    public EfektKsztalceniaResource(EfektKsztalceniaRepository efektKsztalceniaRepository) {
        this.efektKsztalceniaRepository = efektKsztalceniaRepository;
    }

    /**
     * {@code POST  /efekt-ksztalcenias} : Create a new efektKsztalcenia.
     *
     * @param efektKsztalcenia the efektKsztalcenia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new efektKsztalcenia, or with status {@code 400 (Bad Request)} if the efektKsztalcenia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/efekt-ksztalcenias")
    public ResponseEntity<EfektKsztalcenia> createEfektKsztalcenia(@Valid @RequestBody EfektKsztalcenia efektKsztalcenia) throws URISyntaxException {
        log.debug("REST request to save EfektKsztalcenia : {}", efektKsztalcenia);
        if (efektKsztalcenia.getId() != null) {
            throw new BadRequestAlertException("A new efektKsztalcenia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EfektKsztalcenia result = efektKsztalceniaRepository.save(efektKsztalcenia);
        return ResponseEntity.created(new URI("/api/efekt-ksztalcenias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /efekt-ksztalcenias} : Updates an existing efektKsztalcenia.
     *
     * @param efektKsztalcenia the efektKsztalcenia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated efektKsztalcenia,
     * or with status {@code 400 (Bad Request)} if the efektKsztalcenia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the efektKsztalcenia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/efekt-ksztalcenias")
    public ResponseEntity<EfektKsztalcenia> updateEfektKsztalcenia(@Valid @RequestBody EfektKsztalcenia efektKsztalcenia) throws URISyntaxException {
        log.debug("REST request to update EfektKsztalcenia : {}", efektKsztalcenia);
        if (efektKsztalcenia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EfektKsztalcenia result = efektKsztalceniaRepository.save(efektKsztalcenia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, efektKsztalcenia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /efekt-ksztalcenias} : get all the efektKsztalcenias.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of efektKsztalcenias in body.
     */
    @GetMapping("/efekt-ksztalcenias")
    public List<EfektKsztalcenia> getAllEfektKsztalcenias(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all EfektKsztalcenias");
        return efektKsztalceniaRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /efekt-ksztalcenias/:id} : get the "id" efektKsztalcenia.
     *
     * @param id the id of the efektKsztalcenia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the efektKsztalcenia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/efekt-ksztalcenias/{id}")
    public ResponseEntity<EfektKsztalcenia> getEfektKsztalcenia(@PathVariable Long id) {
        log.debug("REST request to get EfektKsztalcenia : {}", id);
        Optional<EfektKsztalcenia> efektKsztalcenia = efektKsztalceniaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(efektKsztalcenia);
    }

    /**
     * {@code DELETE  /efekt-ksztalcenias/:id} : delete the "id" efektKsztalcenia.
     *
     * @param id the id of the efektKsztalcenia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/efekt-ksztalcenias/{id}")
    public ResponseEntity<Void> deleteEfektKsztalcenia(@PathVariable Long id) {
        log.debug("REST request to delete EfektKsztalcenia : {}", id);
        efektKsztalceniaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
