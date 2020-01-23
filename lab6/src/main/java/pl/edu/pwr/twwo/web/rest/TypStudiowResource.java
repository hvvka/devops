package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.TypStudiow;
import pl.edu.pwr.twwo.repository.TypStudiowRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.TypStudiow}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypStudiowResource {

    private final Logger log = LoggerFactory.getLogger(TypStudiowResource.class);

    private static final String ENTITY_NAME = "typStudiow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypStudiowRepository typStudiowRepository;

    public TypStudiowResource(TypStudiowRepository typStudiowRepository) {
        this.typStudiowRepository = typStudiowRepository;
    }

    /**
     * {@code POST  /typ-studiows} : Create a new typStudiow.
     *
     * @param typStudiow the typStudiow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typStudiow, or with status {@code 400 (Bad Request)} if the typStudiow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/typ-studiows")
    public ResponseEntity<TypStudiow> createTypStudiow(@Valid @RequestBody TypStudiow typStudiow) throws URISyntaxException {
        log.debug("REST request to save TypStudiow : {}", typStudiow);
        if (typStudiow.getId() != null) {
            throw new BadRequestAlertException("A new typStudiow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypStudiow result = typStudiowRepository.save(typStudiow);
        return ResponseEntity.created(new URI("/api/typ-studiows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /typ-studiows} : Updates an existing typStudiow.
     *
     * @param typStudiow the typStudiow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typStudiow,
     * or with status {@code 400 (Bad Request)} if the typStudiow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typStudiow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/typ-studiows")
    public ResponseEntity<TypStudiow> updateTypStudiow(@Valid @RequestBody TypStudiow typStudiow) throws URISyntaxException {
        log.debug("REST request to update TypStudiow : {}", typStudiow);
        if (typStudiow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypStudiow result = typStudiowRepository.save(typStudiow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typStudiow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /typ-studiows} : get all the typStudiows.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typStudiows in body.
     */
    @GetMapping("/typ-studiows")
    public List<TypStudiow> getAllTypStudiows() {
        log.debug("REST request to get all TypStudiows");
        return typStudiowRepository.findAll();
    }

    /**
     * {@code GET  /typ-studiows/:id} : get the "id" typStudiow.
     *
     * @param id the id of the typStudiow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typStudiow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/typ-studiows/{id}")
    public ResponseEntity<TypStudiow> getTypStudiow(@PathVariable Long id) {
        log.debug("REST request to get TypStudiow : {}", id);
        Optional<TypStudiow> typStudiow = typStudiowRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typStudiow);
    }

    /**
     * {@code DELETE  /typ-studiows/:id} : delete the "id" typStudiow.
     *
     * @param id the id of the typStudiow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/typ-studiows/{id}")
    public ResponseEntity<Void> deleteTypStudiow(@PathVariable Long id) {
        log.debug("REST request to delete TypStudiow : {}", id);
        typStudiowRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
