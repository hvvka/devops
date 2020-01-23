package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.TypStudiow;
import pl.edu.pwr.twwo.repository.TypStudiowRepository;
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
 * Integration tests for the {@link TypStudiowResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class TypStudiowResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final String DEFAULT_STOPIEN_STUDIOW = "AAAAAAAAAA";
    private static final String UPDATED_STOPIEN_STUDIOW = "BBBBBBBBBB";

    @Autowired
    private TypStudiowRepository typStudiowRepository;

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

    private MockMvc restTypStudiowMockMvc;

    private TypStudiow typStudiow;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypStudiowResource typStudiowResource = new TypStudiowResource(typStudiowRepository);
        this.restTypStudiowMockMvc = MockMvcBuilders.standaloneSetup(typStudiowResource)
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
    public static TypStudiow createEntity(EntityManager em) {
        TypStudiow typStudiow = new TypStudiow()
            .nazwa(DEFAULT_NAZWA)
            .stopienStudiow(DEFAULT_STOPIEN_STUDIOW);
        return typStudiow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypStudiow createUpdatedEntity(EntityManager em) {
        TypStudiow typStudiow = new TypStudiow()
            .nazwa(UPDATED_NAZWA)
            .stopienStudiow(UPDATED_STOPIEN_STUDIOW);
        return typStudiow;
    }

    @BeforeEach
    public void initTest() {
        typStudiow = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypStudiow() throws Exception {
        int databaseSizeBeforeCreate = typStudiowRepository.findAll().size();

        // Create the TypStudiow
        restTypStudiowMockMvc.perform(post("/api/typ-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typStudiow)))
            .andExpect(status().isCreated());

        // Validate the TypStudiow in the database
        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeCreate + 1);
        TypStudiow testTypStudiow = typStudiowList.get(typStudiowList.size() - 1);
        assertThat(testTypStudiow.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testTypStudiow.getStopienStudiow()).isEqualTo(DEFAULT_STOPIEN_STUDIOW);
    }

    @Test
    @Transactional
    public void createTypStudiowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typStudiowRepository.findAll().size();

        // Create the TypStudiow with an existing ID
        typStudiow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypStudiowMockMvc.perform(post("/api/typ-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typStudiow)))
            .andExpect(status().isBadRequest());

        // Validate the TypStudiow in the database
        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNazwaIsRequired() throws Exception {
        int databaseSizeBeforeTest = typStudiowRepository.findAll().size();
        // set the field null
        typStudiow.setNazwa(null);

        // Create the TypStudiow, which fails.

        restTypStudiowMockMvc.perform(post("/api/typ-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typStudiow)))
            .andExpect(status().isBadRequest());

        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypStudiows() throws Exception {
        // Initialize the database
        typStudiowRepository.saveAndFlush(typStudiow);

        // Get all the typStudiowList
        restTypStudiowMockMvc.perform(get("/api/typ-studiows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typStudiow.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].stopienStudiow").value(hasItem(DEFAULT_STOPIEN_STUDIOW)));
    }
    
    @Test
    @Transactional
    public void getTypStudiow() throws Exception {
        // Initialize the database
        typStudiowRepository.saveAndFlush(typStudiow);

        // Get the typStudiow
        restTypStudiowMockMvc.perform(get("/api/typ-studiows/{id}", typStudiow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typStudiow.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.stopienStudiow").value(DEFAULT_STOPIEN_STUDIOW));
    }

    @Test
    @Transactional
    public void getNonExistingTypStudiow() throws Exception {
        // Get the typStudiow
        restTypStudiowMockMvc.perform(get("/api/typ-studiows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypStudiow() throws Exception {
        // Initialize the database
        typStudiowRepository.saveAndFlush(typStudiow);

        int databaseSizeBeforeUpdate = typStudiowRepository.findAll().size();

        // Update the typStudiow
        TypStudiow updatedTypStudiow = typStudiowRepository.findById(typStudiow.getId()).get();
        // Disconnect from session so that the updates on updatedTypStudiow are not directly saved in db
        em.detach(updatedTypStudiow);
        updatedTypStudiow
            .nazwa(UPDATED_NAZWA)
            .stopienStudiow(UPDATED_STOPIEN_STUDIOW);

        restTypStudiowMockMvc.perform(put("/api/typ-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypStudiow)))
            .andExpect(status().isOk());

        // Validate the TypStudiow in the database
        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeUpdate);
        TypStudiow testTypStudiow = typStudiowList.get(typStudiowList.size() - 1);
        assertThat(testTypStudiow.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testTypStudiow.getStopienStudiow()).isEqualTo(UPDATED_STOPIEN_STUDIOW);
    }

    @Test
    @Transactional
    public void updateNonExistingTypStudiow() throws Exception {
        int databaseSizeBeforeUpdate = typStudiowRepository.findAll().size();

        // Create the TypStudiow

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypStudiowMockMvc.perform(put("/api/typ-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typStudiow)))
            .andExpect(status().isBadRequest());

        // Validate the TypStudiow in the database
        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypStudiow() throws Exception {
        // Initialize the database
        typStudiowRepository.saveAndFlush(typStudiow);

        int databaseSizeBeforeDelete = typStudiowRepository.findAll().size();

        // Delete the typStudiow
        restTypStudiowMockMvc.perform(delete("/api/typ-studiows/{id}", typStudiow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypStudiow> typStudiowList = typStudiowRepository.findAll();
        assertThat(typStudiowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
