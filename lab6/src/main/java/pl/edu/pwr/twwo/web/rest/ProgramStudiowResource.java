package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.domain.ProgramStudiow;
import pl.edu.pwr.twwo.repository.ProgramStudiowRepository;
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
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.ProgramStudiow}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProgramStudiowResource {

    private final Logger log = LoggerFactory.getLogger(ProgramStudiowResource.class);

    private static final String ENTITY_NAME = "programStudiow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramStudiowRepository programStudiowRepository;

    public ProgramStudiowResource(ProgramStudiowRepository programStudiowRepository) {
        this.programStudiowRepository = programStudiowRepository;
    }

    /**
     * {@code POST  /program-studiows} : Create a new programStudiow.
     *
     * @param programStudiow the programStudiow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programStudiow, or with status {@code 400 (Bad Request)} if the programStudiow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/program-studiows")
    public ResponseEntity<ProgramStudiow> createProgramStudiow(@Valid @RequestBody ProgramStudiow programStudiow) throws URISyntaxException {
        log.debug("REST request to save ProgramStudiow : {}", programStudiow);
        if (programStudiow.getId() != null) {
            throw new BadRequestAlertException("A new programStudiow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramStudiow result = programStudiowRepository.save(programStudiow);
        return ResponseEntity.created(new URI("/api/program-studiows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /program-studiows} : Updates an existing programStudiow.
     *
     * @param programStudiow the programStudiow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programStudiow,
     * or with status {@code 400 (Bad Request)} if the programStudiow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programStudiow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/program-studiows")
    public ResponseEntity<ProgramStudiow> updateProgramStudiow(@Valid @RequestBody ProgramStudiow programStudiow) throws URISyntaxException {
        log.debug("REST request to update ProgramStudiow : {}", programStudiow);
        if (programStudiow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgramStudiow result = programStudiowRepository.save(programStudiow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programStudiow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /program-studiows} : get all the programStudiows.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programStudiows in body.
     */
    @GetMapping("/program-studiows")
    public List<ProgramStudiow> getAllProgramStudiows() {
        log.debug("REST request to get all ProgramStudiows");
        return programStudiowRepository.findAll();
    }

    /**
     * {@code GET  /program-studiows/:id} : get the "id" programStudiow.
     *
     * @param id the id of the programStudiow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programStudiow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-studiows/{id}")
    public ResponseEntity<ProgramStudiow> getProgramStudiow(@PathVariable Long id) {
        log.debug("REST request to get ProgramStudiow : {}", id);
        Optional<ProgramStudiow> programStudiow = programStudiowRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programStudiow);
    }

    /**
     * {@code DELETE  /program-studiows/:id} : delete the "id" programStudiow.
     *
     * @param id the id of the programStudiow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/program-studiows/{id}")
    public ResponseEntity<Void> deleteProgramStudiow(@PathVariable Long id) {
        log.debug("REST request to delete ProgramStudiow : {}", id);
        programStudiowRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
