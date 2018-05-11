package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.EBackend;
import ro.orange.omoney.ptemplate.repository.EBackendRepository;
import ro.orange.omoney.ptemplate.service.EBackendService;
import ro.orange.omoney.ptemplate.service.dto.EBackendDTO;
import ro.orange.omoney.ptemplate.service.mapper.EBackendMapper;
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
import java.util.List;

import static ro.orange.omoney.ptemplate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EBackendResource REST controller.
 *
 * @see EBackendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class EBackendResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REQUIRED = false;
    private static final Boolean UPDATED_REQUIRED = true;

    @Autowired
    private EBackendRepository eBackendRepository;

    @Autowired
    private EBackendMapper eBackendMapper;

    @Autowired
    private EBackendService eBackendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEBackendMockMvc;

    private EBackend eBackend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EBackendResource eBackendResource = new EBackendResource(eBackendService);
        this.restEBackendMockMvc = MockMvcBuilders.standaloneSetup(eBackendResource)
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
    public static EBackend createEntity(EntityManager em) {
        EBackend eBackend = new EBackend()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .format(DEFAULT_FORMAT)
            .required(DEFAULT_REQUIRED);
        return eBackend;
    }

    @Before
    public void initTest() {
        eBackend = createEntity(em);
    }

    @Test
    @Transactional
    public void createEBackend() throws Exception {
        int databaseSizeBeforeCreate = eBackendRepository.findAll().size();

        // Create the EBackend
        EBackendDTO eBackendDTO = eBackendMapper.toDto(eBackend);
        restEBackendMockMvc.perform(post("/api/e-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBackendDTO)))
            .andExpect(status().isCreated());

        // Validate the EBackend in the database
        List<EBackend> eBackendList = eBackendRepository.findAll();
        assertThat(eBackendList).hasSize(databaseSizeBeforeCreate + 1);
        EBackend testEBackend = eBackendList.get(eBackendList.size() - 1);
        assertThat(testEBackend.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEBackend.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEBackend.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testEBackend.isRequired()).isEqualTo(DEFAULT_REQUIRED);
    }

    @Test
    @Transactional
    public void createEBackendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eBackendRepository.findAll().size();

        // Create the EBackend with an existing ID
        eBackend.setId(1L);
        EBackendDTO eBackendDTO = eBackendMapper.toDto(eBackend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEBackendMockMvc.perform(post("/api/e-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBackendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EBackend in the database
        List<EBackend> eBackendList = eBackendRepository.findAll();
        assertThat(eBackendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEBackends() throws Exception {
        // Initialize the database
        eBackendRepository.saveAndFlush(eBackend);

        // Get all the eBackendList
        restEBackendMockMvc.perform(get("/api/e-backends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eBackend.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED.booleanValue())));
    }

    @Test
    @Transactional
    public void getEBackend() throws Exception {
        // Initialize the database
        eBackendRepository.saveAndFlush(eBackend);

        // Get the eBackend
        restEBackendMockMvc.perform(get("/api/e-backends/{id}", eBackend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eBackend.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEBackend() throws Exception {
        // Get the eBackend
        restEBackendMockMvc.perform(get("/api/e-backends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEBackend() throws Exception {
        // Initialize the database
        eBackendRepository.saveAndFlush(eBackend);
        int databaseSizeBeforeUpdate = eBackendRepository.findAll().size();

        // Update the eBackend
        EBackend updatedEBackend = eBackendRepository.findOne(eBackend.getId());
        // Disconnect from session so that the updates on updatedEBackend are not directly saved in db
        em.detach(updatedEBackend);
        updatedEBackend
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .format(UPDATED_FORMAT)
            .required(UPDATED_REQUIRED);
        EBackendDTO eBackendDTO = eBackendMapper.toDto(updatedEBackend);

        restEBackendMockMvc.perform(put("/api/e-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBackendDTO)))
            .andExpect(status().isOk());

        // Validate the EBackend in the database
        List<EBackend> eBackendList = eBackendRepository.findAll();
        assertThat(eBackendList).hasSize(databaseSizeBeforeUpdate);
        EBackend testEBackend = eBackendList.get(eBackendList.size() - 1);
        assertThat(testEBackend.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEBackend.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEBackend.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testEBackend.isRequired()).isEqualTo(UPDATED_REQUIRED);
    }

    @Test
    @Transactional
    public void updateNonExistingEBackend() throws Exception {
        int databaseSizeBeforeUpdate = eBackendRepository.findAll().size();

        // Create the EBackend
        EBackendDTO eBackendDTO = eBackendMapper.toDto(eBackend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEBackendMockMvc.perform(put("/api/e-backends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eBackendDTO)))
            .andExpect(status().isCreated());

        // Validate the EBackend in the database
        List<EBackend> eBackendList = eBackendRepository.findAll();
        assertThat(eBackendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEBackend() throws Exception {
        // Initialize the database
        eBackendRepository.saveAndFlush(eBackend);
        int databaseSizeBeforeDelete = eBackendRepository.findAll().size();

        // Get the eBackend
        restEBackendMockMvc.perform(delete("/api/e-backends/{id}", eBackend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EBackend> eBackendList = eBackendRepository.findAll();
        assertThat(eBackendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EBackend.class);
        EBackend eBackend1 = new EBackend();
        eBackend1.setId(1L);
        EBackend eBackend2 = new EBackend();
        eBackend2.setId(eBackend1.getId());
        assertThat(eBackend1).isEqualTo(eBackend2);
        eBackend2.setId(2L);
        assertThat(eBackend1).isNotEqualTo(eBackend2);
        eBackend1.setId(null);
        assertThat(eBackend1).isNotEqualTo(eBackend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EBackendDTO.class);
        EBackendDTO eBackendDTO1 = new EBackendDTO();
        eBackendDTO1.setId(1L);
        EBackendDTO eBackendDTO2 = new EBackendDTO();
        assertThat(eBackendDTO1).isNotEqualTo(eBackendDTO2);
        eBackendDTO2.setId(eBackendDTO1.getId());
        assertThat(eBackendDTO1).isEqualTo(eBackendDTO2);
        eBackendDTO2.setId(2L);
        assertThat(eBackendDTO1).isNotEqualTo(eBackendDTO2);
        eBackendDTO1.setId(null);
        assertThat(eBackendDTO1).isNotEqualTo(eBackendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eBackendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eBackendMapper.fromId(null)).isNull();
    }
}
