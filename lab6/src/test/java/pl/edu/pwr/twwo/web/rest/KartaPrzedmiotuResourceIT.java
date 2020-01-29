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
import pl.edu.pwr.twwo.domain.KartaPrzedmiotu;
import pl.edu.pwr.twwo.domain.enumeration.RodzajPrzedmiotu;
import pl.edu.pwr.twwo.repository.KartaPrzedmiotuRepository;
import pl.edu.pwr.twwo.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.twwo.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link KartaPrzedmiotuResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class KartaPrzedmiotuResourceIT {

    private static final String DEFAULT_KOD_PRZEDMIOTU = "AAAAAAAAAA";
    private static final String UPDATED_KOD_PRZEDMIOTU = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWA_ANG = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA_ANG = "BBBBBBBBBB";

    private static final RodzajPrzedmiotu DEFAULT_RODZAJ_PRZEDMIOTU = RodzajPrzedmiotu.OBOWIAZKOWY;
    private static final RodzajPrzedmiotu UPDATED_RODZAJ_PRZEDMIOTU = RodzajPrzedmiotu.WYBIERALNY;

    @Autowired
    private KartaPrzedmiotuRepository kartaPrzedmiotuRepository;

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

    private MockMvc restKartaPrzedmiotuMockMvc;

    private KartaPrzedmiotu kartaPrzedmiotu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KartaPrzedmiotuResource kartaPrzedmiotuResource = new KartaPrzedmiotuResource(kartaPrzedmiotuRepository);
        this.restKartaPrzedmiotuMockMvc = MockMvcBuilders.standaloneSetup(kartaPrzedmiotuResource)
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
    public static KartaPrzedmiotu createEntity(EntityManager em) {
        KartaPrzedmiotu kartaPrzedmiotu = new KartaPrzedmiotu()
            .kodPrzedmiotu(DEFAULT_KOD_PRZEDMIOTU)
            .nazwa(DEFAULT_NAZWA)
            .nazwaAng(DEFAULT_NAZWA_ANG)
            .rodzajPrzedmiotu(DEFAULT_RODZAJ_PRZEDMIOTU);
        return kartaPrzedmiotu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KartaPrzedmiotu createUpdatedEntity(EntityManager em) {
        KartaPrzedmiotu kartaPrzedmiotu = new KartaPrzedmiotu()
            .kodPrzedmiotu(UPDATED_KOD_PRZEDMIOTU)
            .nazwa(UPDATED_NAZWA)
            .nazwaAng(UPDATED_NAZWA_ANG)
            .rodzajPrzedmiotu(UPDATED_RODZAJ_PRZEDMIOTU);
        return kartaPrzedmiotu;
    }

    @BeforeEach
    public void initTest() {
        kartaPrzedmiotu = createEntity(em);
    }

    @Test
    @Transactional
    public void createKartaPrzedmiotu() throws Exception {
        int databaseSizeBeforeCreate = kartaPrzedmiotuRepository.findAll().size();

        // Create the KartaPrzedmiotu
        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isCreated());

        // Validate the KartaPrzedmiotu in the database
        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeCreate + 1);
        KartaPrzedmiotu testKartaPrzedmiotu = kartaPrzedmiotuList.get(kartaPrzedmiotuList.size() - 1);
        assertThat(testKartaPrzedmiotu.getKodPrzedmiotu()).isEqualTo(DEFAULT_KOD_PRZEDMIOTU);
        assertThat(testKartaPrzedmiotu.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testKartaPrzedmiotu.getNazwaAng()).isEqualTo(DEFAULT_NAZWA_ANG);
        assertThat(testKartaPrzedmiotu.getRodzajPrzedmiotu()).isEqualTo(DEFAULT_RODZAJ_PRZEDMIOTU);
    }

    @Test
    @Transactional
    public void createKartaPrzedmiotuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kartaPrzedmiotuRepository.findAll().size();

        // Create the KartaPrzedmiotu with an existing ID
        kartaPrzedmiotu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        // Validate the KartaPrzedmiotu in the database
        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKodPrzedmiotuIsRequired() throws Exception {
        int databaseSizeBeforeTest = kartaPrzedmiotuRepository.findAll().size();
        // set the field null
        kartaPrzedmiotu.setKodPrzedmiotu(null);

        // Create the KartaPrzedmiotu, which fails.

        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazwaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kartaPrzedmiotuRepository.findAll().size();
        // set the field null
        kartaPrzedmiotu.setNazwa(null);

        // Create the KartaPrzedmiotu, which fails.

        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazwaAngIsRequired() throws Exception {
        int databaseSizeBeforeTest = kartaPrzedmiotuRepository.findAll().size();
        // set the field null
        kartaPrzedmiotu.setNazwaAng(null);

        // Create the KartaPrzedmiotu, which fails.

        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRodzajPrzedmiotuIsRequired() throws Exception {
        int databaseSizeBeforeTest = kartaPrzedmiotuRepository.findAll().size();
        // set the field null
        kartaPrzedmiotu.setRodzajPrzedmiotu(null);

        // Create the KartaPrzedmiotu, which fails.

        restKartaPrzedmiotuMockMvc.perform(post("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKartaPrzedmiotus() throws Exception {
        // Initialize the database
        kartaPrzedmiotuRepository.saveAndFlush(kartaPrzedmiotu);

        // Get all the kartaPrzedmiotuList
        restKartaPrzedmiotuMockMvc.perform(get("/api/karta-przedmiotus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kartaPrzedmiotu.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodPrzedmiotu").value(hasItem(DEFAULT_KOD_PRZEDMIOTU)))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].nazwaAng").value(hasItem(DEFAULT_NAZWA_ANG)))
            .andExpect(jsonPath("$.[*].rodzajPrzedmiotu").value(hasItem(DEFAULT_RODZAJ_PRZEDMIOTU.toString())));
    }

    @Test
    @Transactional
    public void getKartaPrzedmiotu() throws Exception {
        // Initialize the database
        kartaPrzedmiotuRepository.saveAndFlush(kartaPrzedmiotu);

        // Get the kartaPrzedmiotu
        restKartaPrzedmiotuMockMvc.perform(get("/api/karta-przedmiotus/{id}", kartaPrzedmiotu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kartaPrzedmiotu.getId().intValue()))
            .andExpect(jsonPath("$.kodPrzedmiotu").value(DEFAULT_KOD_PRZEDMIOTU))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.nazwaAng").value(DEFAULT_NAZWA_ANG))
            .andExpect(jsonPath("$.rodzajPrzedmiotu").value(DEFAULT_RODZAJ_PRZEDMIOTU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKartaPrzedmiotu() throws Exception {
        // Get the kartaPrzedmiotu
        restKartaPrzedmiotuMockMvc.perform(get("/api/karta-przedmiotus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKartaPrzedmiotu() throws Exception {
        // Initialize the database
        kartaPrzedmiotuRepository.saveAndFlush(kartaPrzedmiotu);

        int databaseSizeBeforeUpdate = kartaPrzedmiotuRepository.findAll().size();

        // Update the kartaPrzedmiotu
        KartaPrzedmiotu updatedKartaPrzedmiotu = kartaPrzedmiotuRepository.findById(kartaPrzedmiotu.getId()).get();
        // Disconnect from session so that the updates on updatedKartaPrzedmiotu are not directly saved in db
        em.detach(updatedKartaPrzedmiotu);
        updatedKartaPrzedmiotu
            .kodPrzedmiotu(UPDATED_KOD_PRZEDMIOTU)
            .nazwa(UPDATED_NAZWA)
            .nazwaAng(UPDATED_NAZWA_ANG)
            .rodzajPrzedmiotu(UPDATED_RODZAJ_PRZEDMIOTU);

        restKartaPrzedmiotuMockMvc.perform(put("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKartaPrzedmiotu)))
            .andExpect(status().isOk());

        // Validate the KartaPrzedmiotu in the database
        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeUpdate);
        KartaPrzedmiotu testKartaPrzedmiotu = kartaPrzedmiotuList.get(kartaPrzedmiotuList.size() - 1);
        assertThat(testKartaPrzedmiotu.getKodPrzedmiotu()).isEqualTo(UPDATED_KOD_PRZEDMIOTU);
        assertThat(testKartaPrzedmiotu.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testKartaPrzedmiotu.getNazwaAng()).isEqualTo(UPDATED_NAZWA_ANG);
        assertThat(testKartaPrzedmiotu.getRodzajPrzedmiotu()).isEqualTo(UPDATED_RODZAJ_PRZEDMIOTU);
    }

    @Test
    @Transactional
    public void updateNonExistingKartaPrzedmiotu() throws Exception {
        int databaseSizeBeforeUpdate = kartaPrzedmiotuRepository.findAll().size();

        // Create the KartaPrzedmiotu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKartaPrzedmiotuMockMvc.perform(put("/api/karta-przedmiotus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPrzedmiotu)))
            .andExpect(status().isBadRequest());

        // Validate the KartaPrzedmiotu in the database
        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKartaPrzedmiotu() throws Exception {
        // Initialize the database
        kartaPrzedmiotuRepository.saveAndFlush(kartaPrzedmiotu);

        int databaseSizeBeforeDelete = kartaPrzedmiotuRepository.findAll().size();

        // Delete the kartaPrzedmiotu
        restKartaPrzedmiotuMockMvc.perform(delete("/api/karta-przedmiotus/{id}", kartaPrzedmiotu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KartaPrzedmiotu> kartaPrzedmiotuList = kartaPrzedmiotuRepository.findAll();
        assertThat(kartaPrzedmiotuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
