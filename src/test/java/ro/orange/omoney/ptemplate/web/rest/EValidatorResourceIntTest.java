package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.EValidator;
import ro.orange.omoney.ptemplate.repository.EValidatorRepository;
import ro.orange.omoney.ptemplate.service.EValidatorService;
import ro.orange.omoney.ptemplate.service.dto.EValidatorDTO;
import ro.orange.omoney.ptemplate.service.mapper.EValidatorMapper;
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

import ro.orange.omoney.ptemplate.domain.enumeration.ValidatorType;
/**
 * Test class for the EValidatorResource REST controller.
 *
 * @see EValidatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class EValidatorResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final ValidatorType DEFAULT_TYPE = ValidatorType.REGEX;
    private static final ValidatorType UPDATED_TYPE = ValidatorType.MOD_97;

    @Autowired
    private EValidatorRepository eValidatorRepository;

    @Autowired
    private EValidatorMapper eValidatorMapper;

    @Autowired
    private EValidatorService eValidatorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEValidatorMockMvc;

    private EValidator eValidator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EValidatorResource eValidatorResource = new EValidatorResource(eValidatorService);
        this.restEValidatorMockMvc = MockMvcBuilders.standaloneSetup(eValidatorResource)
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
    public static EValidator createEntity(EntityManager em) {
        EValidator eValidator = new EValidator()
            .value(DEFAULT_VALUE)
            .type(DEFAULT_TYPE);
        return eValidator;
    }

    @Before
    public void initTest() {
        eValidator = createEntity(em);
    }

    @Test
    @Transactional
    public void createEValidator() throws Exception {
        int databaseSizeBeforeCreate = eValidatorRepository.findAll().size();

        // Create the EValidator
        EValidatorDTO eValidatorDTO = eValidatorMapper.toDto(eValidator);
        restEValidatorMockMvc.perform(post("/api/e-validators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValidatorDTO)))
            .andExpect(status().isCreated());

        // Validate the EValidator in the database
        List<EValidator> eValidatorList = eValidatorRepository.findAll();
        assertThat(eValidatorList).hasSize(databaseSizeBeforeCreate + 1);
        EValidator testEValidator = eValidatorList.get(eValidatorList.size() - 1);
        assertThat(testEValidator.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEValidator.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEValidatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eValidatorRepository.findAll().size();

        // Create the EValidator with an existing ID
        eValidator.setId(1L);
        EValidatorDTO eValidatorDTO = eValidatorMapper.toDto(eValidator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEValidatorMockMvc.perform(post("/api/e-validators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValidatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EValidator in the database
        List<EValidator> eValidatorList = eValidatorRepository.findAll();
        assertThat(eValidatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEValidators() throws Exception {
        // Initialize the database
        eValidatorRepository.saveAndFlush(eValidator);

        // Get all the eValidatorList
        restEValidatorMockMvc.perform(get("/api/e-validators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eValidator.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEValidator() throws Exception {
        // Initialize the database
        eValidatorRepository.saveAndFlush(eValidator);

        // Get the eValidator
        restEValidatorMockMvc.perform(get("/api/e-validators/{id}", eValidator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eValidator.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEValidator() throws Exception {
        // Get the eValidator
        restEValidatorMockMvc.perform(get("/api/e-validators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEValidator() throws Exception {
        // Initialize the database
        eValidatorRepository.saveAndFlush(eValidator);
        int databaseSizeBeforeUpdate = eValidatorRepository.findAll().size();

        // Update the eValidator
        EValidator updatedEValidator = eValidatorRepository.findOne(eValidator.getId());
        // Disconnect from session so that the updates on updatedEValidator are not directly saved in db
        em.detach(updatedEValidator);
        updatedEValidator
            .value(UPDATED_VALUE)
            .type(UPDATED_TYPE);
        EValidatorDTO eValidatorDTO = eValidatorMapper.toDto(updatedEValidator);

        restEValidatorMockMvc.perform(put("/api/e-validators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValidatorDTO)))
            .andExpect(status().isOk());

        // Validate the EValidator in the database
        List<EValidator> eValidatorList = eValidatorRepository.findAll();
        assertThat(eValidatorList).hasSize(databaseSizeBeforeUpdate);
        EValidator testEValidator = eValidatorList.get(eValidatorList.size() - 1);
        assertThat(testEValidator.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEValidator.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEValidator() throws Exception {
        int databaseSizeBeforeUpdate = eValidatorRepository.findAll().size();

        // Create the EValidator
        EValidatorDTO eValidatorDTO = eValidatorMapper.toDto(eValidator);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEValidatorMockMvc.perform(put("/api/e-validators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValidatorDTO)))
            .andExpect(status().isCreated());

        // Validate the EValidator in the database
        List<EValidator> eValidatorList = eValidatorRepository.findAll();
        assertThat(eValidatorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEValidator() throws Exception {
        // Initialize the database
        eValidatorRepository.saveAndFlush(eValidator);
        int databaseSizeBeforeDelete = eValidatorRepository.findAll().size();

        // Get the eValidator
        restEValidatorMockMvc.perform(delete("/api/e-validators/{id}", eValidator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EValidator> eValidatorList = eValidatorRepository.findAll();
        assertThat(eValidatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EValidator.class);
        EValidator eValidator1 = new EValidator();
        eValidator1.setId(1L);
        EValidator eValidator2 = new EValidator();
        eValidator2.setId(eValidator1.getId());
        assertThat(eValidator1).isEqualTo(eValidator2);
        eValidator2.setId(2L);
        assertThat(eValidator1).isNotEqualTo(eValidator2);
        eValidator1.setId(null);
        assertThat(eValidator1).isNotEqualTo(eValidator2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EValidatorDTO.class);
        EValidatorDTO eValidatorDTO1 = new EValidatorDTO();
        eValidatorDTO1.setId(1L);
        EValidatorDTO eValidatorDTO2 = new EValidatorDTO();
        assertThat(eValidatorDTO1).isNotEqualTo(eValidatorDTO2);
        eValidatorDTO2.setId(eValidatorDTO1.getId());
        assertThat(eValidatorDTO1).isEqualTo(eValidatorDTO2);
        eValidatorDTO2.setId(2L);
        assertThat(eValidatorDTO1).isNotEqualTo(eValidatorDTO2);
        eValidatorDTO1.setId(null);
        assertThat(eValidatorDTO1).isNotEqualTo(eValidatorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eValidatorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eValidatorMapper.fromId(null)).isNull();
    }
}
