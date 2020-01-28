package pl.edu.pwr.twwo.web.rest;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pl.edu.pwr.twwo.domain.KartaPrzedmiotu;
import pl.edu.pwr.twwo.repository.KartaPrzedmiotuRepository;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.twwo.domain.KartaPrzedmiotu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KartaPrzedmiotuResource {

    private final Logger log = LoggerFactory.getLogger(KartaPrzedmiotuResource.class);

    private static final String ENTITY_NAME = "kartaPrzedmiotu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KartaPrzedmiotuRepository kartaPrzedmiotuRepository;

    public KartaPrzedmiotuResource(KartaPrzedmiotuRepository kartaPrzedmiotuRepository) {
        this.kartaPrzedmiotuRepository = kartaPrzedmiotuRepository;
    }

    /**
     * {@code POST  /karta-przedmiotus} : Create a new kartaPrzedmiotu.
     *
     * @param kartaPrzedmiotu the kartaPrzedmiotu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kartaPrzedmiotu, or with status {@code 400 (Bad Request)} if the kartaPrzedmiotu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/karta-przedmiotus")
    public ResponseEntity<KartaPrzedmiotu> createKartaPrzedmiotu(@Valid @RequestBody KartaPrzedmiotu kartaPrzedmiotu) throws URISyntaxException {
        log.debug("REST request to save KartaPrzedmiotu : {}", kartaPrzedmiotu);
        if (kartaPrzedmiotu.getId() != null) {
            throw new BadRequestAlertException("A new kartaPrzedmiotu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KartaPrzedmiotu result = kartaPrzedmiotuRepository.save(kartaPrzedmiotu);
        return ResponseEntity.created(new URI("/api/karta-przedmiotus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /karta-przedmiotus} : Updates an existing kartaPrzedmiotu.
     *
     * @param kartaPrzedmiotu the kartaPrzedmiotu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kartaPrzedmiotu,
     * or with status {@code 400 (Bad Request)} if the kartaPrzedmiotu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kartaPrzedmiotu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/karta-przedmiotus")
    public ResponseEntity<KartaPrzedmiotu> updateKartaPrzedmiotu(@Valid @RequestBody KartaPrzedmiotu kartaPrzedmiotu) throws URISyntaxException {
        log.debug("REST request to update KartaPrzedmiotu : {}", kartaPrzedmiotu);
        if (kartaPrzedmiotu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KartaPrzedmiotu result = kartaPrzedmiotuRepository.save(kartaPrzedmiotu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kartaPrzedmiotu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /karta-przedmiotus} : get all the kartaPrzedmiotus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kartaPrzedmiotus in body.
     */
    @GetMapping("/karta-przedmiotus")
    public List<KartaPrzedmiotu> getAllKartaPrzedmiotus() {
        log.debug("REST request to get all KartaPrzedmiotus");
        return kartaPrzedmiotuRepository.findAll();
    }

    /**
     * {@code GET  /karta-przedmiotus/:id} : get the "id" kartaPrzedmiotu.
     *
     * @param id the id of the kartaPrzedmiotu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kartaPrzedmiotu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/karta-przedmiotus/{id}")
    public ResponseEntity<KartaPrzedmiotu> getKartaPrzedmiotu(@PathVariable Long id) {
        log.debug("REST request to get KartaPrzedmiotu : {}", id);
        Optional<KartaPrzedmiotu> kartaPrzedmiotu = kartaPrzedmiotuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kartaPrzedmiotu);
    }

    @GetMapping("/karta-przedmiotu/downloads/{id}")
    public ResponseEntity<InputStreamResource> getKartaPrzedmiotuPdf(@PathVariable Long id) {
        log.debug("REST request to get PDF of KartaPrzedmiotu : {}", id);
        try {
            File file = new File("C:\\Users\\Zofia\\Downloads\\wyklad_01.pdf");
            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.APPLICATION_PDF);
            respHeaders.setContentDispositionFormData("attachment", "fileNameIwant.pdf");

            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        } catch (Exception ex) {
            log.info("Error writing PDF of KartaPrzedmiotu id '{}' to output stream", id, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    /**
     * {@code DELETE  /karta-przedmiotus/:id} : delete the "id" kartaPrzedmiotu.
     *
     * @param id the id of the kartaPrzedmiotu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/karta-przedmiotus/{id}")
    public ResponseEntity<Void> deleteKartaPrzedmiotu(@PathVariable Long id) {
        log.debug("REST request to delete KartaPrzedmiotu : {}", id);
        kartaPrzedmiotuRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
