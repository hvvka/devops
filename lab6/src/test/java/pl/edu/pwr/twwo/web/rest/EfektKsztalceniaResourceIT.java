package pl.edu.pwr.twwo.web.rest;

import pl.edu.pwr.twwo.AppApp;
import pl.edu.pwr.twwo.domain.EfektKsztalcenia;
import pl.edu.pwr.twwo.repository.EfektKsztalceniaRepository;
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
 * Integration tests for the {@link EfektKsztalceniaResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)
public class EfektKsztalceniaResourceIT {

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    @Autowired
    private EfektKsztalceniaRepository efektKsztalceniaRepository;

    @Mock
    private EfektKsztalceniaRepository efektKsztalceniaRepositoryMock;

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

    private MockMvc restEfektKsztalceniaMockMvc;

    private EfektKsztalcenia efektKsztalcenia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EfektKsztalceniaResource efektKsztalceniaResource = new EfektKsztalceniaResource(efektKsztalceniaRepository);
        this.restEfektKsztalceniaMockMvc = MockMvcBuilders.standaloneSetup(efektKsztalceniaResource)
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
    public static EfektKsztalcenia createEntity(EntityManager em) {
        EfektKsztalcenia efektKsztalcenia = new EfektKsztalcenia()
            .opis(DEFAULT_OPIS);
        return efektKsztalcenia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EfektKsztalcenia createUpdatedEntity(EntityManager em) {
        EfektKsztalcenia efektKsztalcenia = new EfektKsztalcenia()
            .opis(UPDATED_OPIS);
        return efektKsztalcenia;
    }

    @BeforeEach
    public void initTest() {
        efektKsztalcenia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEfektKsztalcenia() throws Exception {
        int databaseSizeBeforeCreate = efektKsztalceniaRepository.findAll().size();

        // Create the EfektKsztalcenia
        restEfektKsztalceniaMockMvc.perform(post("/api/efekt-ksztalcenias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektKsztalcenia)))
            .andExpect(status().isCreated());

        // Validate the EfektKsztalcenia in the database
        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeCreate + 1);
        EfektKsztalcenia testEfektKsztalcenia = efektKsztalceniaList.get(efektKsztalceniaList.size() - 1);
        assertThat(testEfektKsztalcenia.getOpis()).isEqualTo(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void createEfektKsztalceniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = efektKsztalceniaRepository.findAll().size();

        // Create the EfektKsztalcenia with an existing ID
        efektKsztalcenia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEfektKsztalceniaMockMvc.perform(post("/api/efekt-ksztalcenias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektKsztalcenia)))
            .andExpect(status().isBadRequest());

        // Validate the EfektKsztalcenia in the database
        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOpisIsRequired() throws Exception {
        int databaseSizeBeforeTest = efektKsztalceniaRepository.findAll().size();
        // set the field null
        efektKsztalcenia.setOpis(null);

        // Create the EfektKsztalcenia, which fails.

        restEfektKsztalceniaMockMvc.perform(post("/api/efekt-ksztalcenias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektKsztalcenia)))
            .andExpect(status().isBadRequest());

        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEfektKsztalcenias() throws Exception {
        // Initialize the database
        efektKsztalceniaRepository.saveAndFlush(efektKsztalcenia);

        // Get all the efektKsztalceniaList
        restEfektKsztalceniaMockMvc.perform(get("/api/efekt-ksztalcenias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(efektKsztalcenia.getId().intValue())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEfektKsztalceniasWithEagerRelationshipsIsEnabled() throws Exception {
        EfektKsztalceniaResource efektKsztalceniaResource = new EfektKsztalceniaResource(efektKsztalceniaRepositoryMock);
        when(efektKsztalceniaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEfektKsztalceniaMockMvc = MockMvcBuilders.standaloneSetup(efektKsztalceniaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEfektKsztalceniaMockMvc.perform(get("/api/efekt-ksztalcenias?eagerload=true"))
        .andExpect(status().isOk());

        verify(efektKsztalceniaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEfektKsztalceniasWithEagerRelationshipsIsNotEnabled() throws Exception {
        EfektKsztalceniaResource efektKsztalceniaResource = new EfektKsztalceniaResource(efektKsztalceniaRepositoryMock);
            when(efektKsztalceniaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEfektKsztalceniaMockMvc = MockMvcBuilders.standaloneSetup(efektKsztalceniaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEfektKsztalceniaMockMvc.perform(get("/api/efekt-ksztalcenias?eagerload=true"))
        .andExpect(status().isOk());

            verify(efektKsztalceniaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEfektKsztalcenia() throws Exception {
        // Initialize the database
        efektKsztalceniaRepository.saveAndFlush(efektKsztalcenia);

        // Get the efektKsztalcenia
        restEfektKsztalceniaMockMvc.perform(get("/api/efekt-ksztalcenias/{id}", efektKsztalcenia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(efektKsztalcenia.getId().intValue()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS));
    }

    @Test
    @Transactional
    public void getNonExistingEfektKsztalcenia() throws Exception {
        // Get the efektKsztalcenia
        restEfektKsztalceniaMockMvc.perform(get("/api/efekt-ksztalcenias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEfektKsztalcenia() throws Exception {
        // Initialize the database
        efektKsztalceniaRepository.saveAndFlush(efektKsztalcenia);

        int databaseSizeBeforeUpdate = efektKsztalceniaRepository.findAll().size();

        // Update the efektKsztalcenia
        EfektKsztalcenia updatedEfektKsztalcenia = efektKsztalceniaRepository.findById(efektKsztalcenia.getId()).get();
        // Disconnect from session so that the updates on updatedEfektKsztalcenia are not directly saved in db
        em.detach(updatedEfektKsztalcenia);
        updatedEfektKsztalcenia
            .opis(UPDATED_OPIS);

        restEfektKsztalceniaMockMvc.perform(put("/api/efekt-ksztalcenias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEfektKsztalcenia)))
            .andExpect(status().isOk());

        // Validate the EfektKsztalcenia in the database
        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeUpdate);
        EfektKsztalcenia testEfektKsztalcenia = efektKsztalceniaList.get(efektKsztalceniaList.size() - 1);
        assertThat(testEfektKsztalcenia.getOpis()).isEqualTo(UPDATED_OPIS);
    }

    @Test
    @Transactional
    public void updateNonExistingEfektKsztalcenia() throws Exception {
        int databaseSizeBeforeUpdate = efektKsztalceniaRepository.findAll().size();

        // Create the EfektKsztalcenia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEfektKsztalceniaMockMvc.perform(put("/api/efekt-ksztalcenias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efektKsztalcenia)))
            .andExpect(status().isBadRequest());

        // Validate the EfektKsztalcenia in the database
        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEfektKsztalcenia() throws Exception {
        // Initialize the database
        efektKsztalceniaRepository.saveAndFlush(efektKsztalcenia);

        int databaseSizeBeforeDelete = efektKsztalceniaRepository.findAll().size();

        // Delete the efektKsztalcenia
        restEfektKsztalceniaMockMvc.perform(delete("/api/efekt-ksztalcenias/{id}", efektKsztalcenia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EfektKsztalcenia> efektKsztalceniaList = efektKsztalceniaRepository.findAll();
        assertThat(efektKsztalceniaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
