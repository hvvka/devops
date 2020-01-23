package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.Przedmiot;
import pl.edu.pwr.twwo.repository.PrzedmiotRepository;
import pl.edu.pwr.twwo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static pl.edu.pwr.twwo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrzedmiotResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class PrzedmiotResourceIT {

    private static final Long DEFAULT_NR_SEMESTRU = 1L;
    private static final Long UPDATED_NR_SEMESTRU = 2L;

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    @Autowired
    private PrzedmiotRepository przedmiotRepository;

    @Mock
    private PrzedmiotRepository przedmiotRepositoryMock;

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

    private MockMvc restPrzedmiotMockMvc;

    private Przedmiot przedmiot;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrzedmiotResource przedmiotResource = new PrzedmiotResource(przedmiotRepository);
        this.restPrzedmiotMockMvc = MockMvcBuilders.standaloneSetup(przedmiotResource)
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
    public static Przedmiot createEntity(EntityManager em) {
        Przedmiot przedmiot = new Przedmiot()
            .nrSemestru(DEFAULT_NR_SEMESTRU)
            .nazwa(DEFAULT_NAZWA);
        return przedmiot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Przedmiot createUpdatedEntity(EntityManager em) {
        Przedmiot przedmiot = new Przedmiot()
            .nrSemestru(UPDATED_NR_SEMESTRU)
            .nazwa(UPDATED_NAZWA);
        return przedmiot;
    }

    @BeforeEach
    public void initTest() {
        przedmiot = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrzedmiot() throws Exception {
        int databaseSizeBeforeCreate = przedmiotRepository.findAll().size();

        // Create the Przedmiot
        restPrzedmiotMockMvc.perform(post("/api/przedmiots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przedmiot)))
            .andExpect(status().isCreated());

        // Validate the Przedmiot in the database
        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeCreate + 1);
        Przedmiot testPrzedmiot = przedmiotList.get(przedmiotList.size() - 1);
        assertThat(testPrzedmiot.getNrSemestru()).isEqualTo(DEFAULT_NR_SEMESTRU);
        assertThat(testPrzedmiot.getNazwa()).isEqualTo(DEFAULT_NAZWA);
    }

    @Test
    @Transactional
    public void createPrzedmiotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = przedmiotRepository.findAll().size();

        // Create the Przedmiot with an existing ID
        przedmiot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrzedmiotMockMvc.perform(post("/api/przedmiots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przedmiot)))
            .andExpect(status().isBadRequest());

        // Validate the Przedmiot in the database
        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNrSemestruIsRequired() throws Exception {
        int databaseSizeBeforeTest = przedmiotRepository.findAll().size();
        // set the field null
        przedmiot.setNrSemestru(null);

        // Create the Przedmiot, which fails.

        restPrzedmiotMockMvc.perform(post("/api/przedmiots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przedmiot)))
            .andExpect(status().isBadRequest());

        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrzedmiots() throws Exception {
        // Initialize the database
        przedmiotRepository.saveAndFlush(przedmiot);

        // Get all the przedmiotList
        restPrzedmiotMockMvc.perform(get("/api/przedmiots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przedmiot.getId().intValue())))
            .andExpect(jsonPath("$.[*].nrSemestru").value(hasItem(DEFAULT_NR_SEMESTRU.intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPrzedmiotsWithEagerRelationshipsIsEnabled() throws Exception {
        PrzedmiotResource przedmiotResource = new PrzedmiotResource(przedmiotRepositoryMock);
        when(przedmiotRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPrzedmiotMockMvc = MockMvcBuilders.standaloneSetup(przedmiotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPrzedmiotMockMvc.perform(get("/api/przedmiots?eagerload=true"))
        .andExpect(status().isOk());

        verify(przedmiotRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPrzedmiotsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PrzedmiotResource przedmiotResource = new PrzedmiotResource(przedmiotRepositoryMock);
            when(przedmiotRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPrzedmiotMockMvc = MockMvcBuilders.standaloneSetup(przedmiotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPrzedmiotMockMvc.perform(get("/api/przedmiots?eagerload=true"))
        .andExpect(status().isOk());

            verify(przedmiotRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPrzedmiot() throws Exception {
        // Initialize the database
        przedmiotRepository.saveAndFlush(przedmiot);

        // Get the przedmiot
        restPrzedmiotMockMvc.perform(get("/api/przedmiots/{id}", przedmiot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(przedmiot.getId().intValue()))
            .andExpect(jsonPath("$.nrSemestru").value(DEFAULT_NR_SEMESTRU.intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA));
    }

    @Test
    @Transactional
    public void getNonExistingPrzedmiot() throws Exception {
        // Get the przedmiot
        restPrzedmiotMockMvc.perform(get("/api/przedmiots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzedmiot() throws Exception {
        // Initialize the database
        przedmiotRepository.saveAndFlush(przedmiot);

        int databaseSizeBeforeUpdate = przedmiotRepository.findAll().size();

        // Update the przedmiot
        Przedmiot updatedPrzedmiot = przedmiotRepository.findById(przedmiot.getId()).get();
        // Disconnect from session so that the updates on updatedPrzedmiot are not directly saved in db
        em.detach(updatedPrzedmiot);
        updatedPrzedmiot
            .nrSemestru(UPDATED_NR_SEMESTRU)
            .nazwa(UPDATED_NAZWA);

        restPrzedmiotMockMvc.perform(put("/api/przedmiots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrzedmiot)))
            .andExpect(status().isOk());

        // Validate the Przedmiot in the database
        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeUpdate);
        Przedmiot testPrzedmiot = przedmiotList.get(przedmiotList.size() - 1);
        assertThat(testPrzedmiot.getNrSemestru()).isEqualTo(UPDATED_NR_SEMESTRU);
        assertThat(testPrzedmiot.getNazwa()).isEqualTo(UPDATED_NAZWA);
    }

    @Test
    @Transactional
    public void updateNonExistingPrzedmiot() throws Exception {
        int databaseSizeBeforeUpdate = przedmiotRepository.findAll().size();

        // Create the Przedmiot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrzedmiotMockMvc.perform(put("/api/przedmiots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przedmiot)))
            .andExpect(status().isBadRequest());

        // Validate the Przedmiot in the database
        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrzedmiot() throws Exception {
        // Initialize the database
        przedmiotRepository.saveAndFlush(przedmiot);

        int databaseSizeBeforeDelete = przedmiotRepository.findAll().size();

        // Delete the przedmiot
        restPrzedmiotMockMvc.perform(delete("/api/przedmiots/{id}", przedmiot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Przedmiot> przedmiotList = przedmiotRepository.findAll();
        assertThat(przedmiotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
