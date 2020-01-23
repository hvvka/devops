package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.Dyscyplina;
import pl.edu.pwr.twwo.repository.DyscyplinaRepository;
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
 * Integration tests for the {@link DyscyplinaResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class DyscyplinaResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CZY_WIODACA = false;
    private static final Boolean UPDATED_CZY_WIODACA = true;

    @Autowired
    private DyscyplinaRepository dyscyplinaRepository;

    @Mock
    private DyscyplinaRepository dyscyplinaRepositoryMock;

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

    private MockMvc restDyscyplinaMockMvc;

    private Dyscyplina dyscyplina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DyscyplinaResource dyscyplinaResource = new DyscyplinaResource(dyscyplinaRepository);
        this.restDyscyplinaMockMvc = MockMvcBuilders.standaloneSetup(dyscyplinaResource)
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
    public static Dyscyplina createEntity(EntityManager em) {
        Dyscyplina dyscyplina = new Dyscyplina()
            .nazwa(DEFAULT_NAZWA)
            .czyWiodaca(DEFAULT_CZY_WIODACA);
        return dyscyplina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dyscyplina createUpdatedEntity(EntityManager em) {
        Dyscyplina dyscyplina = new Dyscyplina()
            .nazwa(UPDATED_NAZWA)
            .czyWiodaca(UPDATED_CZY_WIODACA);
        return dyscyplina;
    }

    @BeforeEach
    public void initTest() {
        dyscyplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDyscyplina() throws Exception {
        int databaseSizeBeforeCreate = dyscyplinaRepository.findAll().size();

        // Create the Dyscyplina
        restDyscyplinaMockMvc.perform(post("/api/dyscyplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dyscyplina)))
            .andExpect(status().isCreated());

        // Validate the Dyscyplina in the database
        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Dyscyplina testDyscyplina = dyscyplinaList.get(dyscyplinaList.size() - 1);
        assertThat(testDyscyplina.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testDyscyplina.isCzyWiodaca()).isEqualTo(DEFAULT_CZY_WIODACA);
    }

    @Test
    @Transactional
    public void createDyscyplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dyscyplinaRepository.findAll().size();

        // Create the Dyscyplina with an existing ID
        dyscyplina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDyscyplinaMockMvc.perform(post("/api/dyscyplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dyscyplina)))
            .andExpect(status().isBadRequest());

        // Validate the Dyscyplina in the database
        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCzyWiodacaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dyscyplinaRepository.findAll().size();
        // set the field null
        dyscyplina.setCzyWiodaca(null);

        // Create the Dyscyplina, which fails.

        restDyscyplinaMockMvc.perform(post("/api/dyscyplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dyscyplina)))
            .andExpect(status().isBadRequest());

        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDyscyplinas() throws Exception {
        // Initialize the database
        dyscyplinaRepository.saveAndFlush(dyscyplina);

        // Get all the dyscyplinaList
        restDyscyplinaMockMvc.perform(get("/api/dyscyplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dyscyplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].czyWiodaca").value(hasItem(DEFAULT_CZY_WIODACA.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDyscyplinasWithEagerRelationshipsIsEnabled() throws Exception {
        DyscyplinaResource dyscyplinaResource = new DyscyplinaResource(dyscyplinaRepositoryMock);
        when(dyscyplinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDyscyplinaMockMvc = MockMvcBuilders.standaloneSetup(dyscyplinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDyscyplinaMockMvc.perform(get("/api/dyscyplinas?eagerload=true"))
        .andExpect(status().isOk());

        verify(dyscyplinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDyscyplinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        DyscyplinaResource dyscyplinaResource = new DyscyplinaResource(dyscyplinaRepositoryMock);
            when(dyscyplinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDyscyplinaMockMvc = MockMvcBuilders.standaloneSetup(dyscyplinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDyscyplinaMockMvc.perform(get("/api/dyscyplinas?eagerload=true"))
        .andExpect(status().isOk());

            verify(dyscyplinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDyscyplina() throws Exception {
        // Initialize the database
        dyscyplinaRepository.saveAndFlush(dyscyplina);

        // Get the dyscyplina
        restDyscyplinaMockMvc.perform(get("/api/dyscyplinas/{id}", dyscyplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dyscyplina.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.czyWiodaca").value(DEFAULT_CZY_WIODACA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDyscyplina() throws Exception {
        // Get the dyscyplina
        restDyscyplinaMockMvc.perform(get("/api/dyscyplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDyscyplina() throws Exception {
        // Initialize the database
        dyscyplinaRepository.saveAndFlush(dyscyplina);

        int databaseSizeBeforeUpdate = dyscyplinaRepository.findAll().size();

        // Update the dyscyplina
        Dyscyplina updatedDyscyplina = dyscyplinaRepository.findById(dyscyplina.getId()).get();
        // Disconnect from session so that the updates on updatedDyscyplina are not directly saved in db
        em.detach(updatedDyscyplina);
        updatedDyscyplina
            .nazwa(UPDATED_NAZWA)
            .czyWiodaca(UPDATED_CZY_WIODACA);

        restDyscyplinaMockMvc.perform(put("/api/dyscyplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDyscyplina)))
            .andExpect(status().isOk());

        // Validate the Dyscyplina in the database
        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeUpdate);
        Dyscyplina testDyscyplina = dyscyplinaList.get(dyscyplinaList.size() - 1);
        assertThat(testDyscyplina.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testDyscyplina.isCzyWiodaca()).isEqualTo(UPDATED_CZY_WIODACA);
    }

    @Test
    @Transactional
    public void updateNonExistingDyscyplina() throws Exception {
        int databaseSizeBeforeUpdate = dyscyplinaRepository.findAll().size();

        // Create the Dyscyplina

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDyscyplinaMockMvc.perform(put("/api/dyscyplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dyscyplina)))
            .andExpect(status().isBadRequest());

        // Validate the Dyscyplina in the database
        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDyscyplina() throws Exception {
        // Initialize the database
        dyscyplinaRepository.saveAndFlush(dyscyplina);

        int databaseSizeBeforeDelete = dyscyplinaRepository.findAll().size();

        // Delete the dyscyplina
        restDyscyplinaMockMvc.perform(delete("/api/dyscyplinas/{id}", dyscyplina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dyscyplina> dyscyplinaList = dyscyplinaRepository.findAll();
        assertThat(dyscyplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
