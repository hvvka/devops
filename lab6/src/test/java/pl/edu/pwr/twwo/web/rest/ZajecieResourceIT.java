package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.Zajecie;
import pl.edu.pwr.twwo.repository.ZajecieRepository;
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

import pl.edu.pwr.twwo.domain.enumeration.FormaPrzedmiotu;
import pl.edu.pwr.twwo.domain.enumeration.ModulKsztalcenia;
import pl.edu.pwr.twwo.domain.enumeration.PoziomJezyka;
/**
 * Integration tests for the {@link ZajecieResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class ZajecieResourceIT {

    private static final FormaPrzedmiotu DEFAULT_FORMA = FormaPrzedmiotu.WYKLAD;
    private static final FormaPrzedmiotu UPDATED_FORMA = FormaPrzedmiotu.SEMINARIUM;

    private static final Long DEFAULT_E_CTS = 1L;
    private static final Long UPDATED_E_CTS = 2L;

    private static final Long DEFAULT_Z_ZU = 1L;
    private static final Long UPDATED_Z_ZU = 2L;

    private static final Long DEFAULT_C_NPS = 1L;
    private static final Long UPDATED_C_NPS = 2L;

    private static final ModulKsztalcenia DEFAULT_MODUL_KSZTALCENIA = ModulKsztalcenia.PROFILOWY;
    private static final ModulKsztalcenia UPDATED_MODUL_KSZTALCENIA = ModulKsztalcenia.MATEMATYKA;

    private static final PoziomJezyka DEFAULT_POZIOM_JEZYKA = PoziomJezyka.NIE_DOTYCZY;
    private static final PoziomJezyka UPDATED_POZIOM_JEZYKA = PoziomJezyka.A1;

    @Autowired
    private ZajecieRepository zajecieRepository;

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

    private MockMvc restZajecieMockMvc;

    private Zajecie zajecie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZajecieResource zajecieResource = new ZajecieResource(zajecieRepository);
        this.restZajecieMockMvc = MockMvcBuilders.standaloneSetup(zajecieResource)
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
    public static Zajecie createEntity(EntityManager em) {
        Zajecie zajecie = new Zajecie()
            .forma(DEFAULT_FORMA)
            .eCTS(DEFAULT_E_CTS)
            .zZU(DEFAULT_Z_ZU)
            .cNPS(DEFAULT_C_NPS)
            .modulKsztalcenia(DEFAULT_MODUL_KSZTALCENIA)
            .poziomJezyka(DEFAULT_POZIOM_JEZYKA);
        return zajecie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zajecie createUpdatedEntity(EntityManager em) {
        Zajecie zajecie = new Zajecie()
            .forma(UPDATED_FORMA)
            .eCTS(UPDATED_E_CTS)
            .zZU(UPDATED_Z_ZU)
            .cNPS(UPDATED_C_NPS)
            .modulKsztalcenia(UPDATED_MODUL_KSZTALCENIA)
            .poziomJezyka(UPDATED_POZIOM_JEZYKA);
        return zajecie;
    }

    @BeforeEach
    public void initTest() {
        zajecie = createEntity(em);
    }

    @Test
    @Transactional
    public void createZajecie() throws Exception {
        int databaseSizeBeforeCreate = zajecieRepository.findAll().size();

        // Create the Zajecie
        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isCreated());

        // Validate the Zajecie in the database
        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeCreate + 1);
        Zajecie testZajecie = zajecieList.get(zajecieList.size() - 1);
        assertThat(testZajecie.getForma()).isEqualTo(DEFAULT_FORMA);
        assertThat(testZajecie.geteCTS()).isEqualTo(DEFAULT_E_CTS);
        assertThat(testZajecie.getzZU()).isEqualTo(DEFAULT_Z_ZU);
        assertThat(testZajecie.getcNPS()).isEqualTo(DEFAULT_C_NPS);
        assertThat(testZajecie.getModulKsztalcenia()).isEqualTo(DEFAULT_MODUL_KSZTALCENIA);
        assertThat(testZajecie.getPoziomJezyka()).isEqualTo(DEFAULT_POZIOM_JEZYKA);
    }

    @Test
    @Transactional
    public void createZajecieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zajecieRepository.findAll().size();

        // Create the Zajecie with an existing ID
        zajecie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        // Validate the Zajecie in the database
        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFormaIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.setForma(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkeCTSIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.seteCTS(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkzZUIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.setzZU(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkcNPSIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.setcNPS(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModulKsztalceniaIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.setModulKsztalcenia(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoziomJezykaIsRequired() throws Exception {
        int databaseSizeBeforeTest = zajecieRepository.findAll().size();
        // set the field null
        zajecie.setPoziomJezyka(null);

        // Create the Zajecie, which fails.

        restZajecieMockMvc.perform(post("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZajecies() throws Exception {
        // Initialize the database
        zajecieRepository.saveAndFlush(zajecie);

        // Get all the zajecieList
        restZajecieMockMvc.perform(get("/api/zajecies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zajecie.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA.toString())))
            .andExpect(jsonPath("$.[*].eCTS").value(hasItem(DEFAULT_E_CTS.intValue())))
            .andExpect(jsonPath("$.[*].zZU").value(hasItem(DEFAULT_Z_ZU.intValue())))
            .andExpect(jsonPath("$.[*].cNPS").value(hasItem(DEFAULT_C_NPS.intValue())))
            .andExpect(jsonPath("$.[*].modulKsztalcenia").value(hasItem(DEFAULT_MODUL_KSZTALCENIA.toString())))
            .andExpect(jsonPath("$.[*].poziomJezyka").value(hasItem(DEFAULT_POZIOM_JEZYKA.toString())));
    }
    
    @Test
    @Transactional
    public void getZajecie() throws Exception {
        // Initialize the database
        zajecieRepository.saveAndFlush(zajecie);

        // Get the zajecie
        restZajecieMockMvc.perform(get("/api/zajecies/{id}", zajecie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zajecie.getId().intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA.toString()))
            .andExpect(jsonPath("$.eCTS").value(DEFAULT_E_CTS.intValue()))
            .andExpect(jsonPath("$.zZU").value(DEFAULT_Z_ZU.intValue()))
            .andExpect(jsonPath("$.cNPS").value(DEFAULT_C_NPS.intValue()))
            .andExpect(jsonPath("$.modulKsztalcenia").value(DEFAULT_MODUL_KSZTALCENIA.toString()))
            .andExpect(jsonPath("$.poziomJezyka").value(DEFAULT_POZIOM_JEZYKA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZajecie() throws Exception {
        // Get the zajecie
        restZajecieMockMvc.perform(get("/api/zajecies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZajecie() throws Exception {
        // Initialize the database
        zajecieRepository.saveAndFlush(zajecie);

        int databaseSizeBeforeUpdate = zajecieRepository.findAll().size();

        // Update the zajecie
        Zajecie updatedZajecie = zajecieRepository.findById(zajecie.getId()).get();
        // Disconnect from session so that the updates on updatedZajecie are not directly saved in db
        em.detach(updatedZajecie);
        updatedZajecie
            .forma(UPDATED_FORMA)
            .eCTS(UPDATED_E_CTS)
            .zZU(UPDATED_Z_ZU)
            .cNPS(UPDATED_C_NPS)
            .modulKsztalcenia(UPDATED_MODUL_KSZTALCENIA)
            .poziomJezyka(UPDATED_POZIOM_JEZYKA);

        restZajecieMockMvc.perform(put("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZajecie)))
            .andExpect(status().isOk());

        // Validate the Zajecie in the database
        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeUpdate);
        Zajecie testZajecie = zajecieList.get(zajecieList.size() - 1);
        assertThat(testZajecie.getForma()).isEqualTo(UPDATED_FORMA);
        assertThat(testZajecie.geteCTS()).isEqualTo(UPDATED_E_CTS);
        assertThat(testZajecie.getzZU()).isEqualTo(UPDATED_Z_ZU);
        assertThat(testZajecie.getcNPS()).isEqualTo(UPDATED_C_NPS);
        assertThat(testZajecie.getModulKsztalcenia()).isEqualTo(UPDATED_MODUL_KSZTALCENIA);
        assertThat(testZajecie.getPoziomJezyka()).isEqualTo(UPDATED_POZIOM_JEZYKA);
    }

    @Test
    @Transactional
    public void updateNonExistingZajecie() throws Exception {
        int databaseSizeBeforeUpdate = zajecieRepository.findAll().size();

        // Create the Zajecie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZajecieMockMvc.perform(put("/api/zajecies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zajecie)))
            .andExpect(status().isBadRequest());

        // Validate the Zajecie in the database
        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZajecie() throws Exception {
        // Initialize the database
        zajecieRepository.saveAndFlush(zajecie);

        int databaseSizeBeforeDelete = zajecieRepository.findAll().size();

        // Delete the zajecie
        restZajecieMockMvc.perform(delete("/api/zajecies/{id}", zajecie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zajecie> zajecieList = zajecieRepository.findAll();
        assertThat(zajecieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
