package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.ProgramStudiow;
import pl.edu.pwr.twwo.repository.ProgramStudiowRepository;
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

import pl.edu.pwr.twwo.domain.enumeration.ProfilKsztalcenia;
import pl.edu.pwr.twwo.domain.enumeration.FormaStudiow;
import pl.edu.pwr.twwo.domain.enumeration.JezykProwadzeniaStudiow;
/**
 * Integration tests for the {@link ProgramStudiowResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class ProgramStudiowResourceIT {

    private static final ProfilKsztalcenia DEFAULT_PROFIL_KSZTALCENIA = ProfilKsztalcenia.OGOLNOAKADEMICKI;
    private static final ProfilKsztalcenia UPDATED_PROFIL_KSZTALCENIA = ProfilKsztalcenia.PRAKTYCZNY;

    private static final FormaStudiow DEFAULT_FORMA_STUDIOW = FormaStudiow.STACJONARNE;
    private static final FormaStudiow UPDATED_FORMA_STUDIOW = FormaStudiow.NIESTACJONARNE;

    private static final String DEFAULT_KIERUNEK = "AAAAAAAAAA";
    private static final String UPDATED_KIERUNEK = "BBBBBBBBBB";

    private static final String DEFAULT_WYDZIAL = "AAAAAAAAAA";
    private static final String UPDATED_WYDZIAL = "BBBBBBBBBB";

    private static final JezykProwadzeniaStudiow DEFAULT_JEZYK_PROWADZENIA_STUDIOW = JezykProwadzeniaStudiow.POLSKI;
    private static final JezykProwadzeniaStudiow UPDATED_JEZYK_PROWADZENIA_STUDIOW = JezykProwadzeniaStudiow.ANGIELSKI;

    private static final Long DEFAULT_LICZBA_SEMESTROW = 1L;
    private static final Long UPDATED_LICZBA_SEMESTROW = 2L;

    private static final String DEFAULT_CYKL_KSZTALCENIA = "AAAAAAAAAA";
    private static final String UPDATED_CYKL_KSZTALCENIA = "BBBBBBBBBB";

    @Autowired
    private ProgramStudiowRepository programStudiowRepository;

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

    private MockMvc restProgramStudiowMockMvc;

    private ProgramStudiow programStudiow;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgramStudiowResource programStudiowResource = new ProgramStudiowResource(programStudiowRepository);
        this.restProgramStudiowMockMvc = MockMvcBuilders.standaloneSetup(programStudiowResource)
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
    public static ProgramStudiow createEntity(EntityManager em) {
        ProgramStudiow programStudiow = new ProgramStudiow()
            .profilKsztalcenia(DEFAULT_PROFIL_KSZTALCENIA)
            .formaStudiow(DEFAULT_FORMA_STUDIOW)
            .kierunek(DEFAULT_KIERUNEK)
            .wydzial(DEFAULT_WYDZIAL)
            .jezykProwadzeniaStudiow(DEFAULT_JEZYK_PROWADZENIA_STUDIOW)
            .liczbaSemestrow(DEFAULT_LICZBA_SEMESTROW)
            .cyklKsztalcenia(DEFAULT_CYKL_KSZTALCENIA);
        return programStudiow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramStudiow createUpdatedEntity(EntityManager em) {
        ProgramStudiow programStudiow = new ProgramStudiow()
            .profilKsztalcenia(UPDATED_PROFIL_KSZTALCENIA)
            .formaStudiow(UPDATED_FORMA_STUDIOW)
            .kierunek(UPDATED_KIERUNEK)
            .wydzial(UPDATED_WYDZIAL)
            .jezykProwadzeniaStudiow(UPDATED_JEZYK_PROWADZENIA_STUDIOW)
            .liczbaSemestrow(UPDATED_LICZBA_SEMESTROW)
            .cyklKsztalcenia(UPDATED_CYKL_KSZTALCENIA);
        return programStudiow;
    }

    @BeforeEach
    public void initTest() {
        programStudiow = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgramStudiow() throws Exception {
        int databaseSizeBeforeCreate = programStudiowRepository.findAll().size();

        // Create the ProgramStudiow
        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isCreated());

        // Validate the ProgramStudiow in the database
        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramStudiow testProgramStudiow = programStudiowList.get(programStudiowList.size() - 1);
        assertThat(testProgramStudiow.getProfilKsztalcenia()).isEqualTo(DEFAULT_PROFIL_KSZTALCENIA);
        assertThat(testProgramStudiow.getFormaStudiow()).isEqualTo(DEFAULT_FORMA_STUDIOW);
        assertThat(testProgramStudiow.getKierunek()).isEqualTo(DEFAULT_KIERUNEK);
        assertThat(testProgramStudiow.getWydzial()).isEqualTo(DEFAULT_WYDZIAL);
        assertThat(testProgramStudiow.getJezykProwadzeniaStudiow()).isEqualTo(DEFAULT_JEZYK_PROWADZENIA_STUDIOW);
        assertThat(testProgramStudiow.getLiczbaSemestrow()).isEqualTo(DEFAULT_LICZBA_SEMESTROW);
        assertThat(testProgramStudiow.getCyklKsztalcenia()).isEqualTo(DEFAULT_CYKL_KSZTALCENIA);
    }

    @Test
    @Transactional
    public void createProgramStudiowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programStudiowRepository.findAll().size();

        // Create the ProgramStudiow with an existing ID
        programStudiow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramStudiow in the database
        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProfilKsztalceniaIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setProfilKsztalcenia(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormaStudiowIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setFormaStudiow(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKierunekIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setKierunek(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWydzialIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setWydzial(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLiczbaSemestrowIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setLiczbaSemestrow(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCyklKsztalceniaIsRequired() throws Exception {
        int databaseSizeBeforeTest = programStudiowRepository.findAll().size();
        // set the field null
        programStudiow.setCyklKsztalcenia(null);

        // Create the ProgramStudiow, which fails.

        restProgramStudiowMockMvc.perform(post("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgramStudiows() throws Exception {
        // Initialize the database
        programStudiowRepository.saveAndFlush(programStudiow);

        // Get all the programStudiowList
        restProgramStudiowMockMvc.perform(get("/api/program-studiows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programStudiow.getId().intValue())))
            .andExpect(jsonPath("$.[*].profilKsztalcenia").value(hasItem(DEFAULT_PROFIL_KSZTALCENIA.toString())))
            .andExpect(jsonPath("$.[*].formaStudiow").value(hasItem(DEFAULT_FORMA_STUDIOW.toString())))
            .andExpect(jsonPath("$.[*].kierunek").value(hasItem(DEFAULT_KIERUNEK)))
            .andExpect(jsonPath("$.[*].wydzial").value(hasItem(DEFAULT_WYDZIAL)))
            .andExpect(jsonPath("$.[*].jezykProwadzeniaStudiow").value(hasItem(DEFAULT_JEZYK_PROWADZENIA_STUDIOW.toString())))
            .andExpect(jsonPath("$.[*].liczbaSemestrow").value(hasItem(DEFAULT_LICZBA_SEMESTROW.intValue())))
            .andExpect(jsonPath("$.[*].cyklKsztalcenia").value(hasItem(DEFAULT_CYKL_KSZTALCENIA)));
    }
    
    @Test
    @Transactional
    public void getProgramStudiow() throws Exception {
        // Initialize the database
        programStudiowRepository.saveAndFlush(programStudiow);

        // Get the programStudiow
        restProgramStudiowMockMvc.perform(get("/api/program-studiows/{id}", programStudiow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programStudiow.getId().intValue()))
            .andExpect(jsonPath("$.profilKsztalcenia").value(DEFAULT_PROFIL_KSZTALCENIA.toString()))
            .andExpect(jsonPath("$.formaStudiow").value(DEFAULT_FORMA_STUDIOW.toString()))
            .andExpect(jsonPath("$.kierunek").value(DEFAULT_KIERUNEK))
            .andExpect(jsonPath("$.wydzial").value(DEFAULT_WYDZIAL))
            .andExpect(jsonPath("$.jezykProwadzeniaStudiow").value(DEFAULT_JEZYK_PROWADZENIA_STUDIOW.toString()))
            .andExpect(jsonPath("$.liczbaSemestrow").value(DEFAULT_LICZBA_SEMESTROW.intValue()))
            .andExpect(jsonPath("$.cyklKsztalcenia").value(DEFAULT_CYKL_KSZTALCENIA));
    }

    @Test
    @Transactional
    public void getNonExistingProgramStudiow() throws Exception {
        // Get the programStudiow
        restProgramStudiowMockMvc.perform(get("/api/program-studiows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramStudiow() throws Exception {
        // Initialize the database
        programStudiowRepository.saveAndFlush(programStudiow);

        int databaseSizeBeforeUpdate = programStudiowRepository.findAll().size();

        // Update the programStudiow
        ProgramStudiow updatedProgramStudiow = programStudiowRepository.findById(programStudiow.getId()).get();
        // Disconnect from session so that the updates on updatedProgramStudiow are not directly saved in db
        em.detach(updatedProgramStudiow);
        updatedProgramStudiow
            .profilKsztalcenia(UPDATED_PROFIL_KSZTALCENIA)
            .formaStudiow(UPDATED_FORMA_STUDIOW)
            .kierunek(UPDATED_KIERUNEK)
            .wydzial(UPDATED_WYDZIAL)
            .jezykProwadzeniaStudiow(UPDATED_JEZYK_PROWADZENIA_STUDIOW)
            .liczbaSemestrow(UPDATED_LICZBA_SEMESTROW)
            .cyklKsztalcenia(UPDATED_CYKL_KSZTALCENIA);

        restProgramStudiowMockMvc.perform(put("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProgramStudiow)))
            .andExpect(status().isOk());

        // Validate the ProgramStudiow in the database
        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeUpdate);
        ProgramStudiow testProgramStudiow = programStudiowList.get(programStudiowList.size() - 1);
        assertThat(testProgramStudiow.getProfilKsztalcenia()).isEqualTo(UPDATED_PROFIL_KSZTALCENIA);
        assertThat(testProgramStudiow.getFormaStudiow()).isEqualTo(UPDATED_FORMA_STUDIOW);
        assertThat(testProgramStudiow.getKierunek()).isEqualTo(UPDATED_KIERUNEK);
        assertThat(testProgramStudiow.getWydzial()).isEqualTo(UPDATED_WYDZIAL);
        assertThat(testProgramStudiow.getJezykProwadzeniaStudiow()).isEqualTo(UPDATED_JEZYK_PROWADZENIA_STUDIOW);
        assertThat(testProgramStudiow.getLiczbaSemestrow()).isEqualTo(UPDATED_LICZBA_SEMESTROW);
        assertThat(testProgramStudiow.getCyklKsztalcenia()).isEqualTo(UPDATED_CYKL_KSZTALCENIA);
    }

    @Test
    @Transactional
    public void updateNonExistingProgramStudiow() throws Exception {
        int databaseSizeBeforeUpdate = programStudiowRepository.findAll().size();

        // Create the ProgramStudiow

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramStudiowMockMvc.perform(put("/api/program-studiows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programStudiow)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramStudiow in the database
        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgramStudiow() throws Exception {
        // Initialize the database
        programStudiowRepository.saveAndFlush(programStudiow);

        int databaseSizeBeforeDelete = programStudiowRepository.findAll().size();

        // Delete the programStudiow
        restProgramStudiowMockMvc.perform(delete("/api/program-studiows/{id}", programStudiow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramStudiow> programStudiowList = programStudiowRepository.findAll();
        assertThat(programStudiowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
