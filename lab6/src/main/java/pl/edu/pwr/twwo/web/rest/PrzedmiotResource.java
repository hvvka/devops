package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.Przedmiot;
import pl.edu.pwr.twwo.repository.PrzedmiotRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.Przedmiot}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrzedmiotResource {

    private final Logger log = LoggerFactory.getLogger(PrzedmiotResource.class);

    private static final String ENTITY_NAME = "przedmiot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrzedmiotRepository przedmiotRepository;

    public PrzedmiotResource(PrzedmiotRepository przedmiotRepository) {
        this.przedmiotRepository = przedmiotRepository;
    }

    /**
     * {@code POST  /przedmiots} : Create a new przedmiot.
     *
     * @param przedmiot the przedmiot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new przedmiot, or with status {@code 400 (Bad Request)} if the przedmiot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/przedmiots")
    public ResponseEntity<Przedmiot> createPrzedmiot(@Valid @RequestBody Przedmiot przedmiot) throws URISyntaxException {
        log.debug("REST request to save Przedmiot : {}", przedmiot);
        if (przedmiot.getId() != null) {
            throw new BadRequestAlertException("A new przedmiot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Przedmiot result = przedmiotRepository.save(przedmiot);
        return ResponseEntity.created(new URI("/api/przedmiots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /przedmiots} : Updates an existing przedmiot.
     *
     * @param przedmiot the przedmiot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated przedmiot,
     * or with status {@code 400 (Bad Request)} if the przedmiot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the przedmiot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/przedmiots")
    public ResponseEntity<Przedmiot> updatePrzedmiot(@Valid @RequestBody Przedmiot przedmiot) throws URISyntaxException {
        log.debug("REST request to update Przedmiot : {}", przedmiot);
        if (przedmiot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Przedmiot result = przedmiotRepository.save(przedmiot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, przedmiot.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /przedmiots} : get all the przedmiots.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of przedmiots in body.
     */
    @GetMapping("/przedmiots")
    public List<Przedmiot> getAllPrzedmiots(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Przedmiots");
        return przedmiotRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /przedmiots/:id} : get the "id" przedmiot.
     *
     * @param id the id of the przedmiot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the przedmiot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/przedmiots/{id}")
    public ResponseEntity<Przedmiot> getPrzedmiot(@PathVariable Long id) {
        log.debug("REST request to get Przedmiot : {}", id);
        Optional<Przedmiot> przedmiot = przedmiotRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(przedmiot);
    }

    /**
     * {@code DELETE  /przedmiots/:id} : delete the "id" przedmiot.
     *
     * @param id the id of the przedmiot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/przedmiots/{id}")
    public ResponseEntity<Void> deletePrzedmiot(@PathVariable Long id) {
        log.debug("REST request to delete Przedmiot : {}", id);
        przedmiotRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
