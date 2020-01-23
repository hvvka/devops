package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.Zajecie;
import pl.edu.pwr.twwo.repository.ZajecieRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.Zajecie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ZajecieResource {

    private final Logger log = LoggerFactory.getLogger(ZajecieResource.class);

    private static final String ENTITY_NAME = "zajecie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZajecieRepository zajecieRepository;

    public ZajecieResource(ZajecieRepository zajecieRepository) {
        this.zajecieRepository = zajecieRepository;
    }

    /**
     * {@code POST  /zajecies} : Create a new zajecie.
     *
     * @param zajecie the zajecie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zajecie, or with status {@code 400 (Bad Request)} if the zajecie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zajecies")
    public ResponseEntity<Zajecie> createZajecie(@Valid @RequestBody Zajecie zajecie) throws URISyntaxException {
        log.debug("REST request to save Zajecie : {}", zajecie);
        if (zajecie.getId() != null) {
            throw new BadRequestAlertException("A new zajecie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Zajecie result = zajecieRepository.save(zajecie);
        return ResponseEntity.created(new URI("/api/zajecies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zajecies} : Updates an existing zajecie.
     *
     * @param zajecie the zajecie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zajecie,
     * or with status {@code 400 (Bad Request)} if the zajecie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zajecie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zajecies")
    public ResponseEntity<Zajecie> updateZajecie(@Valid @RequestBody Zajecie zajecie) throws URISyntaxException {
        log.debug("REST request to update Zajecie : {}", zajecie);
        if (zajecie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Zajecie result = zajecieRepository.save(zajecie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zajecie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zajecies} : get all the zajecies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zajecies in body.
     */
    @GetMapping("/zajecies")
    public List<Zajecie> getAllZajecies() {
        log.debug("REST request to get all Zajecies");
        return zajecieRepository.findAll();
    }

    /**
     * {@code GET  /zajecies/:id} : get the "id" zajecie.
     *
     * @param id the id of the zajecie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zajecie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zajecies/{id}")
    public ResponseEntity<Zajecie> getZajecie(@PathVariable Long id) {
        log.debug("REST request to get Zajecie : {}", id);
        Optional<Zajecie> zajecie = zajecieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zajecie);
    }

    /**
     * {@code DELETE  /zajecies/:id} : delete the "id" zajecie.
     *
     * @param id the id of the zajecie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zajecies/{id}")
    public ResponseEntity<Void> deleteZajecie(@PathVariable Long id) {
        log.debug("REST request to delete Zajecie : {}", id);
        zajecieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
