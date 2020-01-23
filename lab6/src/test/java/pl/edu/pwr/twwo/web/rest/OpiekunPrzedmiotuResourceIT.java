package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.OpiekunPrzedmiotu;
import pl.edu.pwr.twwo.repository.OpiekunPrzedmiotuRepository;
import pl.edu.pwr.twwo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static pl.edu.pwr.twwo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OpiekunPrzedmiotuResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class OpiekunPrzedmiotuResourceIT {

    @Autowired
    private OpiekunPrzedmiotuRepository opiekunPrzedmiotuRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOpiekunPrzedmiotuMockMvc;

    private OpiekunPrzedmiotu opiekunPrzedmiotu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpiekunPrzedmiotuResource opiekunPrzedmiotuResource = new OpiekunPrzedmiotuResource(opiekunPrzedmiotuRepository);
        this.restOpiekunPrzedmiotuMockMvc = MockMvcBuilders.standaloneSetup(opiekunPrzedmiotuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpiekunPrzedmiotu createEntity(EntityManager em) {
        OpiekunPrzedmiotu opiekunPrzedmiotu = new OpiekunPrzedmiotu();
        return opiekunPrzedmiotu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpiekunPrzedmiotu createUpdatedEntity(EntityManager em) {
        OpiekunPrzedmiotu opiekunPrzedmiotu = new OpiekunPrzedmiotu();
        return opiekunPrzedmiotu;
    }

    @BeforeEach
    public void initTest() {
        opiekunPrzedmiotu = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpiekunPrzedmiotu() throws Exception {
        int databaseSizeBeforeCreate = opiekunPrzedmiotuRepository.findAll().size();

        // Create the OpiekunPrzedmiotu
        restOpiekunPrzedmiotuMockMvc.perform(post("/api/opiekun-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opiekunPrzedmiotu)))
            .andExpect(status().isCreated());

        // Validate the OpiekunPrzedmiotu in the database
        List<OpiekunPrzedmiotu> opiekunPrzedmiotuList = opiekunPrzedmiotuRepository.findAll();
        assertThat(opiekunPrzedmiotuList).hasSize(databaseSizeBeforeCreate + 1);
        OpiekunPrzedmiotu testOpiekunPrzedmiotu = opiekunPrzedmiotuList.get(opiekunPrzedmiotuList.size() - 1);
    }

    @Test
    @Transactional
    public void createOpiekunPrzedmiotuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opiekunPrzedmiotuRepository.findAll().size();

        // Create the OpiekunPrzedmiotu with an existing ID
        opiekunPrzedmiotu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpiekunPrzedmiotuMockMvc.perform(post("/api/opiekun-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opiekunPrzedmiotu)))
            .andExpect(status().isBadRequest());

        // Validate the OpiekunPrzedmiotu in the database
        List<OpiekunPrzedmiotu> opiekunPrzedmiotuList = opiekunPrzedmiotuRepository.findAll();
        assertThat(opiekunPrzedmiotuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOpiekunPrzedmiotus() throws Exception {
        // Initialize the database
        opiekunPrzedmiotuRepository.saveAndFlush(opiekunPrzedmiotu);

        // Get all the opiekunPrzedmiotuList
        restOpiekunPrzedmiotuMockMvc.perform(get("/api/opiekun-przedmiotus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opiekunPrzedmiotu.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOpiekunPrzedmiotu() throws Exception {
        // Initialize the database
        opiekunPrzedmiotuRepository.saveAndFlush(opiekunPrzedmiotu);

        // Get the opiekunPrzedmiotu
        restOpiekunPrzedmiotuMockMvc.perform(get("/api/opiekun-przedmiotus/{id}", opiekunPrzedmiotu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(opiekunPrzedmiotu.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOpiekunPrzedmiotu() throws Exception {
        // Get the opiekunPrzedmiotu
        restOpiekunPrzedmiotuMockMvc.perform(get("/api/opiekun-przedmiotus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpiekunPrzedmiotu() throws Exception {
        // Initialize the database
        opiekunPrzedmiotuRepository.saveAndFlush(opiekunPrzedmiotu);

        int databaseSizeBeforeUpdate = opiekunPrzedmiotuRepository.findAll().size();

        // Update the opiekunPrzedmiotu
        OpiekunPrzedmiotu updatedOpiekunPrzedmiotu = opiekunPrzedmiotuRepository.findById(opiekunPrzedmiotu.getId()).get();
        // Disconnect from session so that the updates on updatedOpiekunPrzedmiotu are not directly saved in db
        em.detach(updatedOpiekunPrzedmiotu);

        restOpiekunPrzedmiotuMockMvc.perform(put("/api/opiekun-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOpiekunPrzedmiotu)))
            .andExpect(status().isOk());

        // Validate the OpiekunPrzedmiotu in the database
        List<OpiekunPrzedmiotu> opiekunPrzedmiotuList = opiekunPrzedmiotuRepository.findAll();
        assertThat(opiekunPrzedmiotuList).hasSize(databaseSizeBeforeUpdate);
        OpiekunPrzedmiotu testOpiekunPrzedmiotu = opiekunPrzedmiotuList.get(opiekunPrzedmiotuList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOpiekunPrzedmiotu() throws Exception {
        int databaseSizeBeforeUpdate = opiekunPrzedmiotuRepository.findAll().size();

        // Create the OpiekunPrzedmiotu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpiekunPrzedmiotuMockMvc.perform(put("/api/opiekun-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opiekunPrzedmiotu)))
            .andExpect(status().isBadRequest());

        // Validate the OpiekunPrzedmiotu in the database
        List<OpiekunPrzedmiotu> opiekunPrzedmiotuList = opiekunPrzedmiotuRepository.findAll();
        assertThat(opiekunPrzedmiotuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOpiekunPrzedmiotu() throws Exception {
        // Initialize the database
        opiekunPrzedmiotuRepository.saveAndFlush(opiekunPrzedmiotu);

        int databaseSizeBeforeDelete = opiekunPrzedmiotuRepository.findAll().size();

        // Delete the opiekunPrzedmiotu
        restOpiekunPrzedmiotuMockMvc.perform(delete("/api/opiekun-przedmiotus/{id}", opiekunPrzedmiotu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpiekunPrzedmiotu> opiekunPrzedmiotuList = opiekunPrzedmiotuRepository.findAll();
        assertThat(opiekunPrzedmiotuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
