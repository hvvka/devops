package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.Dyscyplina;
import pl.edu.pwr.twwo.repository.DyscyplinaRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.Dyscyplina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DyscyplinaResource {

    private final Logger log = LoggerFactory.getLogger(DyscyplinaResource.class);

    private static final String ENTITY_NAME = "dyscyplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DyscyplinaRepository dyscyplinaRepository;

    public DyscyplinaResource(DyscyplinaRepository dyscyplinaRepository) {
        this.dyscyplinaRepository = dyscyplinaRepository;
    }

    /**
     * {@code POST  /dyscyplinas} : Create a new dyscyplina.
     *
     * @param dyscyplina the dyscyplina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dyscyplina, or with status {@code 400 (Bad Request)} if the dyscyplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dyscyplinas")
    public ResponseEntity<Dyscyplina> createDyscyplina(@Valid @RequestBody Dyscyplina dyscyplina) throws URISyntaxException {
        log.debug("REST request to save Dyscyplina : {}", dyscyplina);
        if (dyscyplina.getId() != null) {
            throw new BadRequestAlertException("A new dyscyplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dyscyplina result = dyscyplinaRepository.save(dyscyplina);
        return ResponseEntity.created(new URI("/api/dyscyplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dyscyplinas} : Updates an existing dyscyplina.
     *
     * @param dyscyplina the dyscyplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dyscyplina,
     * or with status {@code 400 (Bad Request)} if the dyscyplina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dyscyplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dyscyplinas")
    public ResponseEntity<Dyscyplina> updateDyscyplina(@Valid @RequestBody Dyscyplina dyscyplina) throws URISyntaxException {
        log.debug("REST request to update Dyscyplina : {}", dyscyplina);
        if (dyscyplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dyscyplina result = dyscyplinaRepository.save(dyscyplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dyscyplina.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dyscyplinas} : get all the dyscyplinas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dyscyplinas in body.
     */
    @GetMapping("/dyscyplinas")
    public List<Dyscyplina> getAllDyscyplinas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Dyscyplinas");
        return dyscyplinaRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /dyscyplinas/:id} : get the "id" dyscyplina.
     *
     * @param id the id of the dyscyplina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dyscyplina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dyscyplinas/{id}")
    public ResponseEntity<Dyscyplina> getDyscyplina(@PathVariable Long id) {
        log.debug("REST request to get Dyscyplina : {}", id);
        Optional<Dyscyplina> dyscyplina = dyscyplinaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dyscyplina);
    }

    /**
     * {@code DELETE  /dyscyplinas/:id} : delete the "id" dyscyplina.
     *
     * @param id the id of the dyscyplina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dyscyplinas/{id}")
    public ResponseEntity<Void> deleteDyscyplina(@PathVariable Long id) {
        log.debug("REST request to delete Dyscyplina : {}", id);
        dyscyplinaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
