package pl.edu.pwr.twwo.web.rest;

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
import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.EfektMinisterialny;
import pl.edu.pwr.twwo.domain.enumeration.TypEfektuMinisterialnego;
import pl.edu.pwr.twwo.repository.EfektMinisterialnyRepository;
import pl.edu.pwr.twwo.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.twwo.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link EfektMinisterialnyResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class EfektMinisterialnyResourceIT {

    private static final String DEFAULT_KOD_EFEKTU = "AAAAAAAAAA";
    private static final String UPDATED_KOD_EFEKTU = "BBBBBBBBBB";

    private static final Long DEFAULT_POZIOM_EFEKTU = 1L;
    private static final Long UPDATED_POZIOM_EFEKTU = 2L;

    private static final TypEfektuMinisterialnego DEFAULT_TYP_EFEKTU_MINISTERIALNEGO = TypEfektuMinisterialnego.OGOLNEGO_KSZTALCENIA;
    private static final TypEfektuMinisterialnego UPDATED_TYP_EFEKTU_MINISTERIALNEGO = TypEfektuMinisterialnego.INZYNIERSKIEGO;

    @Autowired
    private EfektMinisterialnyRepository efektMinisterialnyRepository;

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

    private MockMvc restEfektMinisterialnyMockMvc;

    private EfektMinisterialny efektMinisterialny;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EfektMinisterialnyResource efektMinisterialnyResource = new EfektMinisterialnyResource(efektMinisterialnyRepository);
        this.restEfektMinisterialnyMockMvc = MockMvcBuilders.standaloneSetup(efektMinisterialnyResource)
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
    public static EfektMinisterialny createEntity(EntityManager em) {
        EfektMinisterialny efektMinisterialny = new EfektMinisterialny()
            .kodEfektu(DEFAULT_KOD_EFEKTU)
            .poziomEfektu(DEFAULT_POZIOM_EFEKTU)
            .typEfektuMinisterialnego(DEFAULT_TYP_EFEKTU_MINISTERIALNEGO);
        return efektMinisterialny;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EfektMinisterialny createUpdatedEntity(EntityManager em) {
        EfektMinisterialny efektMinisterialny = new EfektMinisterialny()
            .kodEfektu(UPDATED_KOD_EFEKTU)
            .poziomEfektu(UPDATED_POZIOM_EFEKTU)
            .typEfektuMinisterialnego(UPDATED_TYP_EFEKTU_MINISTERIALNEGO);
        return efektMinisterialny;
    }

    @BeforeEach
    public void initTest() {
        efektMinisterialny = createEntity(em);
    }

    @Test
    @Transactional
    public void createEfektMinisterialny() throws Exception {
        int databaseSizeBeforeCreate = efektMinisterialnyRepository.findAll().size();

        // Create the EfektMinisterialny
        restEfektMinisterialnyMockMvc.perform(post("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isCreated());

        // Validate the EfektMinisterialny in the database
        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeCreate + 1);
        EfektMinisterialny testEfektMinisterialny = efektMinisterialnyList.get(efektMinisterialnyList.size() - 1);
        assertThat(testEfektMinisterialny.getKodEfektu()).isEqualTo(DEFAULT_KOD_EFEKTU);
        assertThat(testEfektMinisterialny.getPoziomEfektu()).isEqualTo(DEFAULT_POZIOM_EFEKTU);
        assertThat(testEfektMinisterialny.getTypEfektuMinisterialnego()).isEqualTo(DEFAULT_TYP_EFEKTU_MINISTERIALNEGO);
    }

    @Test
    @Transactional
    public void createEfektMinisterialnyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = efektMinisterialnyRepository.findAll().size();

        // Create the EfektMinisterialny with an existing ID
        efektMinisterialny.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEfektMinisterialnyMockMvc.perform(post("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isBadRequest());

        // Validate the EfektMinisterialny in the database
        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKodEfektuIsRequired() throws Exception {
        int databaseSizeBeforeTest = efektMinisterialnyRepository.findAll().size();
        // set the field null
        efektMinisterialny.setKodEfektu(null);

        // Create the EfektMinisterialny, which fails.

        restEfektMinisterialnyMockMvc.perform(post("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isBadRequest());

        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoziomEfektuIsRequired() throws Exception {
        int databaseSizeBeforeTest = efektMinisterialnyRepository.findAll().size();
        // set the field null
        efektMinisterialny.setPoziomEfektu(null);

        // Create the EfektMinisterialny, which fails.

        restEfektMinisterialnyMockMvc.perform(post("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isBadRequest());

        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypEfektuMinisterialnegoIsRequired() throws Exception {
        int databaseSizeBeforeTest = efektMinisterialnyRepository.findAll().size();
        // set the field null
        efektMinisterialny.setTypEfektuMinisterialnego(null);

        // Create the EfektMinisterialny, which fails.

        restEfektMinisterialnyMockMvc.perform(post("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isBadRequest());

        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEfektMinisterialnies() throws Exception {
        // Initialize the database
        efektMinisterialnyRepository.saveAndFlush(efektMinisterialny);

        // Get all the efektMinisterialnyList
        restEfektMinisterialnyMockMvc.perform(get("/api/efekt-ministerialnies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(efektMinisterialny.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodEfektu").value(hasItem(DEFAULT_KOD_EFEKTU)))
            .andExpect(jsonPath("$.[*].poziomEfektu").value(hasItem(DEFAULT_POZIOM_EFEKTU.intValue())))
            .andExpect(jsonPath("$.[*].typEfektuMinisterialnego").value(hasItem(DEFAULT_TYP_EFEKTU_MINISTERIALNEGO.toString())));
    }

    @Test
    @Transactional
    public void getEfektMinisterialny() throws Exception {
        // Initialize the database
        efektMinisterialnyRepository.saveAndFlush(efektMinisterialny);

        // Get the efektMinisterialny
        restEfektMinisterialnyMockMvc.perform(get("/api/efekt-ministerialnies/{id}", efektMinisterialny.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(efektMinisterialny.getId().intValue()))
            .andExpect(jsonPath("$.kodEfektu").value(DEFAULT_KOD_EFEKTU))
            .andExpect(jsonPath("$.poziomEfektu").value(DEFAULT_POZIOM_EFEKTU.intValue()))
            .andExpect(jsonPath("$.typEfektuMinisterialnego").value(DEFAULT_TYP_EFEKTU_MINISTERIALNEGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEfektMinisterialny() throws Exception {
        // Get the efektMinisterialny
        restEfektMinisterialnyMockMvc.perform(get("/api/efekt-ministerialnies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEfektMinisterialny() throws Exception {
        // Initialize the database
        efektMinisterialnyRepository.saveAndFlush(efektMinisterialny);

        int databaseSizeBeforeUpdate = efektMinisterialnyRepository.findAll().size();

        // Update the efektMinisterialny
        EfektMinisterialny updatedEfektMinisterialny = efektMinisterialnyRepository.findById(efektMinisterialny.getId()).get();
        // Disconnect from session so that the updates on updatedEfektMinisterialny are not directly saved in db
        em.detach(updatedEfektMinisterialny);
        updatedEfektMinisterialny
            .kodEfektu(UPDATED_KOD_EFEKTU)
            .poziomEfektu(UPDATED_POZIOM_EFEKTU)
            .typEfektuMinisterialnego(UPDATED_TYP_EFEKTU_MINISTERIALNEGO);

        restEfektMinisterialnyMockMvc.perform(put("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEfektMinisterialny)))
            .andExpect(status().isOk());

        // Validate the EfektMinisterialny in the database
        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeUpdate);
        EfektMinisterialny testEfektMinisterialny = efektMinisterialnyList.get(efektMinisterialnyList.size() - 1);
        assertThat(testEfektMinisterialny.getKodEfektu()).isEqualTo(UPDATED_KOD_EFEKTU);
        assertThat(testEfektMinisterialny.getPoziomEfektu()).isEqualTo(UPDATED_POZIOM_EFEKTU);
        assertThat(testEfektMinisterialny.getTypEfektuMinisterialnego()).isEqualTo(UPDATED_TYP_EFEKTU_MINISTERIALNEGO);
    }

    @Test
    @Transactional
    public void updateNonExistingEfektMinisterialny() throws Exception {
        int databaseSizeBeforeUpdate = efektMinisterialnyRepository.findAll().size();

        // Create the EfektMinisterialny

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEfektMinisterialnyMockMvc.perform(put("/api/efekt-ministerialnies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektMinisterialny)))
            .andExpect(status().isBadRequest());

        // Validate the EfektMinisterialny in the database
        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEfektMinisterialny() throws Exception {
        // Initialize the database
        efektMinisterialnyRepository.saveAndFlush(efektMinisterialny);

        int databaseSizeBeforeDelete = efektMinisterialnyRepository.findAll().size();

        // Delete the efektMinisterialny
        restEfektMinisterialnyMockMvc.perform(delete("/api/efekt-ministerialnies/{id}", efektMinisterialny.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EfektMinisterialny> efektMinisterialnyList = efektMinisterialnyRepository.findAll();
        assertThat(efektMinisterialnyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
