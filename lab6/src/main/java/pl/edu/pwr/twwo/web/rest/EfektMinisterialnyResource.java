package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.EfektMinisterialny;
import pl.edu.pwr.twwo.repository.EfektMinisterialnyRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.EfektMinisterialny}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EfektMinisterialnyResource {

    private final Logger log = LoggerFactory.getLogger(EfektMinisterialnyResource.class);

    private static final String ENTITY_NAME = "efektMinisterialny";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EfektMinisterialnyRepository efektMinisterialnyRepository;

    public EfektMinisterialnyResource(EfektMinisterialnyRepository efektMinisterialnyRepository) {
        this.efektMinisterialnyRepository = efektMinisterialnyRepository;
    }

    /**
     * {@code POST  /efekt-ministerialnies} : Create a new efektMinisterialny.
     *
     * @param efektMinisterialny the efektMinisterialny to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new efektMinisterialny, or with status {@code 400 (Bad Request)} if the efektMinisterialny has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/efekt-ministerialnies")
    public ResponseEntity<EfektMinisterialny> createEfektMinisterialny(@Valid @RequestBody EfektMinisterialny efektMinisterialny) throws URISyntaxException {
        log.debug("REST request to save EfektMinisterialny : {}", efektMinisterialny);
        if (efektMinisterialny.getId() != null) {
            throw new BadRequestAlertException("A new efektMinisterialny cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EfektMinisterialny result = efektMinisterialnyRepository.save(efektMinisterialny);
        return ResponseEntity.created(new URI("/api/efekt-ministerialnies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /efekt-ministerialnies} : Updates an existing efektMinisterialny.
     *
     * @param efektMinisterialny the efektMinisterialny to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated efektMinisterialny,
     * or with status {@code 400 (Bad Request)} if the efektMinisterialny is not valid,
     * or with status {@code 500 (Internal Server Error)} if the efektMinisterialny couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/efekt-ministerialnies")
    public ResponseEntity<EfektMinisterialny> updateEfektMinisterialny(@Valid @RequestBody EfektMinisterialny efektMinisterialny) throws URISyntaxException {
        log.debug("REST request to update EfektMinisterialny : {}", efektMinisterialny);
        if (efektMinisterialny.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EfektMinisterialny result = efektMinisterialnyRepository.save(efektMinisterialny);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, efektMinisterialny.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /efekt-ministerialnies} : get all the efektMinisterialnies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of efektMinisterialnies in body.
     */
    @GetMapping("/efekt-ministerialnies")
    public List<EfektMinisterialny> getAllEfektMinisterialnies() {
        log.debug("REST request to get all EfektMinisterialnies");
        return efektMinisterialnyRepository.findAll();
    }

    /**
     * {@code GET  /efekt-ministerialnies/:id} : get the "id" efektMinisterialny.
     *
     * @param id the id of the efektMinisterialny to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the efektMinisterialny, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/efekt-ministerialnies/{id}")
    public ResponseEntity<EfektMinisterialny> getEfektMinisterialny(@PathVariable Long id) {
        log.debug("REST request to get EfektMinisterialny : {}", id);
        Optional<EfektMinisterialny> efektMinisterialny = efektMinisterialnyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(efektMinisterialny);
    }

    /**
     * {@code DELETE  /efekt-ministerialnies/:id} : delete the "id" efektMinisterialny.
     *
     * @param id the id of the efektMinisterialny to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/efekt-ministerialnies/{id}")
    public ResponseEntity<Void> deleteEfektMinisterialny(@PathVariable Long id) {
        log.debug("REST request to delete EfektMinisterialny : {}", id);
        efektMinisterialnyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
