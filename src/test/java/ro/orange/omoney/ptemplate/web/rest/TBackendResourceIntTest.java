package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.TBackend;
import ro.orange.omoney.ptemplate.repository.TBackendRepository;
import ro.orange.omoney.ptemplate.service.TBackendService;
import ro.orange.omoney.ptemplate.service.dto.TBackendDTO;
import ro.orange.omoney.ptemplate.service.mapper.TBackendMapper;
import ro.orange.omoney.ptemplate.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ro.orange.omoney.ptemplate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TBackendResource REST controller.
 *
 * @see TBackendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class TBackendResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECURRENCE = false;
    private static final Boolean UPDATED_RECURRENCE = true;

    private static final Instant DEFAULT_RECURRING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECURRING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TBackendRepository tBackendRepository;

    @Autowired
    private TBackendMapper tBackendMapper;

    @Autowired
    private TBackendService tBackendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTBackendMockMvc;

    private TBackend tBackend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TBackendResource tBackendResource = new TBackendResource(tBackendService);
        this.restTBackendMockMvc = MockMvcBuilders.standaloneSetup(tBackendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TBackend createEntity(EntityManager em) {
        TBackend tBackend = new TBackend()
            .type(DEFAULT_TYPE)
            .recurrence(DEFAULT_RECURRENCE)
            .recurringDate(DEFAULT_RECURRING_DATE);
        return tBackend;
    }

    @Before
    public void initTest() {
        tBackend = createEntity(em);
    }

    @Test
    @Transactional
    public void createTBackend() throws Exception {
        int databaseSizeBeforeCreate = tBackendRepository.findAll().size();

        // Create the TBackend
        TBackendDTO tBackendDTO = tBackendMapper.toDto(tBackend);
        restTBackendMockMvc.perform(post("/api/t-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBackendDTO)))
            .andExpect(status().isCreated());

        // Validate the TBackend in the database
        List<TBackend> tBackendList = tBackendRepository.findAll();
        assertThat(tBackendList).hasSize(databaseSizeBeforeCreate + 1);
        TBackend testTBackend = tBackendList.get(tBackendList.size() - 1);
        assertThat(testTBackend.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTBackend.isRecurrence()).isEqualTo(DEFAULT_RECURRENCE);
        assertThat(testTBackend.getRecurringDate()).isEqualTo(DEFAULT_RECURRING_DATE);
    }

    @Test
    @Transactional
    public void createTBackendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tBackendRepository.findAll().size();

        // Create the TBackend with an existing ID
        tBackend.setId(1L);
        TBackendDTO tBackendDTO = tBackendMapper.toDto(tBackend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTBackendMockMvc.perform(post("/api/t-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBackendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TBackend in the database
        List<TBackend> tBackendList = tBackendRepository.findAll();
        assertThat(tBackendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTBackends() throws Exception {
        // Initialize the database
        tBackendRepository.saveAndFlush(tBackend);

        // Get all the tBackendList
        restTBackendMockMvc.perform(get("/api/t-backends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tBackend.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].recurrence").value(hasItem(DEFAULT_RECURRENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].recurringDate").value(hasItem(DEFAULT_RECURRING_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTBackend() throws Exception {
        // Initialize the database
        tBackendRepository.saveAndFlush(tBackend);

        // Get the tBackend
        restTBackendMockMvc.perform(get("/api/t-backends/{id}", tBackend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tBackend.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.recurrence").value(DEFAULT_RECURRENCE.booleanValue()))
            .andExpect(jsonPath("$.recurringDate").value(DEFAULT_RECURRING_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTBackend() throws Exception {
        // Get the tBackend
        restTBackendMockMvc.perform(get("/api/t-backends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTBackend() throws Exception {
        // Initialize the database
        tBackendRepository.saveAndFlush(tBackend);
        int databaseSizeBeforeUpdate = tBackendRepository.findAll().size();

        // Update the tBackend
        TBackend updatedTBackend = tBackendRepository.findOne(tBackend.getId());
        // Disconnect from session so that the updates on updatedTBackend are not directly saved in db
        em.detach(updatedTBackend);
        updatedTBackend
            .type(UPDATED_TYPE)
            .recurrence(UPDATED_RECURRENCE)
            .recurringDate(UPDATED_RECURRING_DATE);
        TBackendDTO tBackendDTO = tBackendMapper.toDto(updatedTBackend);

        restTBackendMockMvc.perform(put("/api/t-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBackendDTO)))
            .andExpect(status().isOk());

        // Validate the TBackend in the database
        List<TBackend> tBackendList = tBackendRepository.findAll();
        assertThat(tBackendList).hasSize(databaseSizeBeforeUpdate);
        TBackend testTBackend = tBackendList.get(tBackendList.size() - 1);
        assertThat(testTBackend.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTBackend.isRecurrence()).isEqualTo(UPDATED_RECURRENCE);
        assertThat(testTBackend.getRecurringDate()).isEqualTo(UPDATED_RECURRING_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTBackend() throws Exception {
        int databaseSizeBeforeUpdate = tBackendRepository.findAll().size();

        // Create the TBackend
        TBackendDTO tBackendDTO = tBackendMapper.toDto(tBackend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTBackendMockMvc.perform(put("/api/t-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tBackendDTO)))
            .andExpect(status().isCreated());

        // Validate the TBackend in the database
        List<TBackend> tBackendList = tBackendRepository.findAll();
        assertThat(tBackendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTBackend() throws Exception {
        // Initialize the database
        tBackendRepository.saveAndFlush(tBackend);
        int databaseSizeBeforeDelete = tBackendRepository.findAll().size();

        // Get the tBackend
        restTBackendMockMvc.perform(delete("/api/t-backends/{id}", tBackend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TBackend> tBackendList = tBackendRepository.findAll();
        assertThat(tBackendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBackend.class);
        TBackend tBackend1 = new TBackend();
        tBackend1.setId(1L);
        TBackend tBackend2 = new TBackend();
        tBackend2.setId(tBackend1.getId());
        assertThat(tBackend1).isEqualTo(tBackend2);
        tBackend2.setId(2L);
        assertThat(tBackend1).isNotEqualTo(tBackend2);
        tBackend1.setId(null);
        assertThat(tBackend1).isNotEqualTo(tBackend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TBackendDTO.class);
        TBackendDTO tBackendDTO1 = new TBackendDTO();
        tBackendDTO1.setId(1L);
        TBackendDTO tBackendDTO2 = new TBackendDTO();
        assertThat(tBackendDTO1).isNotEqualTo(tBackendDTO2);
        tBackendDTO2.setId(tBackendDTO1.getId());
        assertThat(tBackendDTO1).isEqualTo(tBackendDTO2);
        tBackendDTO2.setId(2L);
        assertThat(tBackendDTO1).isNotEqualTo(tBackendDTO2);
        tBackendDTO1.setId(null);
        assertThat(tBackendDTO1).isNotEqualTo(tBackendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tBackendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tBackendMapper.fromId(null)).isNull();
    }
}
