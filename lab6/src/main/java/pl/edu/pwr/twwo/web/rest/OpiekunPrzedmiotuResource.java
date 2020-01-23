package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.OpiekunPrzedmiotu;
import pl.edu.pwr.twwo.repository.OpiekunPrzedmiotuRepository;
import pl.edu.pwr.twwo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.OpiekunPrzedmiotu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OpiekunPrzedmiotuResource {

    private final Logger log = LoggerFactory.getLogger(OpiekunPrzedmiotuResource.class);

    private static final String ENTITY_NAME = "opiekunPrzedmiotu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpiekunPrzedmiotuRepository opiekunPrzedmiotuRepository;

    public OpiekunPrzedmiotuResource(OpiekunPrzedmiotuRepository opiekunPrzedmiotuRepository) {
        this.opiekunPrzedmiotuRepository = opiekunPrzedmiotuRepository;
    }

    /**
     * {@code POST  /opiekun-przedmiotus} : Create a new opiekunPrzedmiotu.
     *
     * @param opiekunPrzedmiotu the opiekunPrzedmiotu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opiekunPrzedmiotu, or with status {@code 400 (Bad Request)} if the opiekunPrzedmiotu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opiekun-przedmiotus")
    public ResponseEntity<OpiekunPrzedmiotu> createOpiekunPrzedmiotu(@RequestBody OpiekunPrzedmiotu opiekunPrzedmiotu) throws URISyntaxException {
        log.debug("REST request to save OpiekunPrzedmiotu : {}", opiekunPrzedmiotu);
        if (opiekunPrzedmiotu.getId() != null) {
            throw new BadRequestAlertException("A new opiekunPrzedmiotu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpiekunPrzedmiotu result = opiekunPrzedmiotuRepository.save(opiekunPrzedmiotu);
        return ResponseEntity.created(new URI("/api/opiekun-przedmiotus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opiekun-przedmiotus} : Updates an existing opiekunPrzedmiotu.
     *
     * @param opiekunPrzedmiotu the opiekunPrzedmiotu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opiekunPrzedmiotu,
     * or with status {@code 400 (Bad Request)} if the opiekunPrzedmiotu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opiekunPrzedmiotu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opiekun-przedmiotus")
    public ResponseEntity<OpiekunPrzedmiotu> updateOpiekunPrzedmiotu(@RequestBody OpiekunPrzedmiotu opiekunPrzedmiotu) throws URISyntaxException {
        log.debug("REST request to update OpiekunPrzedmiotu : {}", opiekunPrzedmiotu);
        if (opiekunPrzedmiotu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpiekunPrzedmiotu result = opiekunPrzedmiotuRepository.save(opiekunPrzedmiotu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opiekunPrzedmiotu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opiekun-przedmiotus} : get all the opiekunPrzedmiotus.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opiekunPrzedmiotus in body.
     */
    @GetMapping("/opiekun-przedmiotus")
    public List<OpiekunPrzedmiotu> getAllOpiekunPrzedmiotus() {
        log.debug("REST request to get all OpiekunPrzedmiotus");
        return opiekunPrzedmiotuRepository.findAll();
    }

    /**
     * {@code GET  /opiekun-przedmiotus/:id} : get the "id" opiekunPrzedmiotu.
     *
     * @param id the id of the opiekunPrzedmiotu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opiekunPrzedmiotu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opiekun-przedmiotus/{id}")
    public ResponseEntity<OpiekunPrzedmiotu> getOpiekunPrzedmiotu(@PathVariable Long id) {
        log.debug("REST request to get OpiekunPrzedmiotu : {}", id);
        Optional<OpiekunPrzedmiotu> opiekunPrzedmiotu = opiekunPrzedmiotuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opiekunPrzedmiotu);
    }

    /**
     * {@code DELETE  /opiekun-przedmiotus/:id} : delete the "id" opiekunPrzedmiotu.
     *
     * @param id the id of the opiekunPrzedmiotu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opiekun-przedmiotus/{id}")
    public ResponseEntity<Void> deleteOpiekunPrzedmiotu(@PathVariable Long id) {
        log.debug("REST request to delete OpiekunPrzedmiotu : {}", id);
        opiekunPrzedmiotuRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
