package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.EUi;
import ro.orange.omoney.ptemplate.repository.EUiRepository;
import ro.orange.omoney.ptemplate.service.EUiService;
import ro.orange.omoney.ptemplate.service.dto.EUiDTO;
import ro.orange.omoney.ptemplate.service.mapper.EUiMapper;
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

import ro.orange.omoney.ptemplate.domain.enumeration.EUiType;
/**
 * Test class for the EUiResource REST controller.
 *
 * @see EUiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class EUiResourceIntTest {

    private static final EUiType DEFAULT_TYPE = EUiType.TEXT;
    private static final EUiType UPDATED_TYPE = EUiType.NUMERIC;

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ_ONLY = false;
    private static final Boolean UPDATED_READ_ONLY = true;

    private static final Boolean DEFAULT_REQUIRED = false;
    private static final Boolean UPDATED_REQUIRED = true;

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATOR = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATOR = "BBBBBBBBBB";

    @Autowired
    private EUiRepository eUiRepository;

    @Autowired
    private EUiMapper eUiMapper;

    @Autowired
    private EUiService eUiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEUiMockMvc;

    private EUi eUi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EUiResource eUiResource = new EUiResource(eUiService);
        this.restEUiMockMvc = MockMvcBuilders.standaloneSetup(eUiResource)
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
    public static EUi createEntity(EntityManager em) {
        EUi eUi = new EUi()
            .type(DEFAULT_TYPE)
            .index(DEFAULT_INDEX)
            .icon(DEFAULT_ICON)
            .readOnly(DEFAULT_READ_ONLY)
            .required(DEFAULT_REQUIRED)
            .visible(DEFAULT_VISIBLE)
            .format(DEFAULT_FORMAT)
            .validator(DEFAULT_VALIDATOR);
        return eUi;
    }

    @Before
    public void initTest() {
        eUi = createEntity(em);
    }

    @Test
    @Transactional
    public void createEUi() throws Exception {
        int databaseSizeBeforeCreate = eUiRepository.findAll().size();

        // Create the EUi
        EUiDTO eUiDTO = eUiMapper.toDto(eUi);
        restEUiMockMvc.perform(post("/api/e-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eUiDTO)))
            .andExpect(status().isCreated());

        // Validate the EUi in the database
        List<EUi> eUiList = eUiRepository.findAll();
        assertThat(eUiList).hasSize(databaseSizeBeforeCreate + 1);
        EUi testEUi = eUiList.get(eUiList.size() - 1);
        assertThat(testEUi.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEUi.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testEUi.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testEUi.isReadOnly()).isEqualTo(DEFAULT_READ_ONLY);
        assertThat(testEUi.isRequired()).isEqualTo(DEFAULT_REQUIRED);
        assertThat(testEUi.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testEUi.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testEUi.getValidator()).isEqualTo(DEFAULT_VALIDATOR);
    }

    @Test
    @Transactional
    public void createEUiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eUiRepository.findAll().size();

        // Create the EUi with an existing ID
        eUi.setId(1L);
        EUiDTO eUiDTO = eUiMapper.toDto(eUi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEUiMockMvc.perform(post("/api/e-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eUiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EUi in the database
        List<EUi> eUiList = eUiRepository.findAll();
        assertThat(eUiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEUis() throws Exception {
        // Initialize the database
        eUiRepository.saveAndFlush(eUi);

        // Get all the eUiList
        restEUiMockMvc.perform(get("/api/e-uis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eUi.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].readOnly").value(hasItem(DEFAULT_READ_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].validator").value(hasItem(DEFAULT_VALIDATOR.toString())));
    }

    @Test
    @Transactional
    public void getEUi() throws Exception {
        // Initialize the database
        eUiRepository.saveAndFlush(eUi);

        // Get the eUi
        restEUiMockMvc.perform(get("/api/e-uis/{id}", eUi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eUi.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.readOnly").value(DEFAULT_READ_ONLY.booleanValue()))
            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.validator").value(DEFAULT_VALIDATOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEUi() throws Exception {
        // Get the eUi
        restEUiMockMvc.perform(get("/api/e-uis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEUi() throws Exception {
        // Initialize the database
        eUiRepository.saveAndFlush(eUi);
        int databaseSizeBeforeUpdate = eUiRepository.findAll().size();

        // Update the eUi
        EUi updatedEUi = eUiRepository.findOne(eUi.getId());
        // Disconnect from session so that the updates on updatedEUi are not directly saved in db
        em.detach(updatedEUi);
        updatedEUi
            .type(UPDATED_TYPE)
            .index(UPDATED_INDEX)
            .icon(UPDATED_ICON)
            .readOnly(UPDATED_READ_ONLY)
            .required(UPDATED_REQUIRED)
            .visible(UPDATED_VISIBLE)
            .format(UPDATED_FORMAT)
            .validator(UPDATED_VALIDATOR);
        EUiDTO eUiDTO = eUiMapper.toDto(updatedEUi);

        restEUiMockMvc.perform(put("/api/e-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eUiDTO)))
            .andExpect(status().isOk());

        // Validate the EUi in the database
        List<EUi> eUiList = eUiRepository.findAll();
        assertThat(eUiList).hasSize(databaseSizeBeforeUpdate);
        EUi testEUi = eUiList.get(eUiList.size() - 1);
        assertThat(testEUi.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEUi.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testEUi.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testEUi.isReadOnly()).isEqualTo(UPDATED_READ_ONLY);
        assertThat(testEUi.isRequired()).isEqualTo(UPDATED_REQUIRED);
        assertThat(testEUi.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testEUi.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testEUi.getValidator()).isEqualTo(UPDATED_VALIDATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEUi() throws Exception {
        int databaseSizeBeforeUpdate = eUiRepository.findAll().size();

        // Create the EUi
        EUiDTO eUiDTO = eUiMapper.toDto(eUi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEUiMockMvc.perform(put("/api/e-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eUiDTO)))
            .andExpect(status().isCreated());

        // Validate the EUi in the database
        List<EUi> eUiList = eUiRepository.findAll();
        assertThat(eUiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEUi() throws Exception {
        // Initialize the database
        eUiRepository.saveAndFlush(eUi);
        int databaseSizeBeforeDelete = eUiRepository.findAll().size();

        // Get the eUi
        restEUiMockMvc.perform(delete("/api/e-uis/{id}", eUi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EUi> eUiList = eUiRepository.findAll();
        assertThat(eUiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EUi.class);
        EUi eUi1 = new EUi();
        eUi1.setId(1L);
        EUi eUi2 = new EUi();
        eUi2.setId(eUi1.getId());
        assertThat(eUi1).isEqualTo(eUi2);
        eUi2.setId(2L);
        assertThat(eUi1).isNotEqualTo(eUi2);
        eUi1.setId(null);
        assertThat(eUi1).isNotEqualTo(eUi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EUiDTO.class);
        EUiDTO eUiDTO1 = new EUiDTO();
        eUiDTO1.setId(1L);
        EUiDTO eUiDTO2 = new EUiDTO();
        assertThat(eUiDTO1).isNotEqualTo(eUiDTO2);
        eUiDTO2.setId(eUiDTO1.getId());
        assertThat(eUiDTO1).isEqualTo(eUiDTO2);
        eUiDTO2.setId(2L);
        assertThat(eUiDTO1).isNotEqualTo(eUiDTO2);
        eUiDTO1.setId(null);
        assertThat(eUiDTO1).isNotEqualTo(eUiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eUiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eUiMapper.fromId(null)).isNull();
    }
}
