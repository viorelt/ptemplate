package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.EVisibility;
import ro.orange.omoney.ptemplate.repository.EVisibilityRepository;
import ro.orange.omoney.ptemplate.service.EVisibilityService;
import ro.orange.omoney.ptemplate.service.dto.EVisibilityDTO;
import ro.orange.omoney.ptemplate.service.mapper.EVisibilityMapper;
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

import ro.orange.omoney.ptemplate.domain.enumeration.VisibilityType;
import ro.orange.omoney.ptemplate.domain.enumeration.OperandType;
/**
 * Test class for the EVisibilityResource REST controller.
 *
 * @see EVisibilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class EVisibilityResourceIntTest {

    private static final VisibilityType DEFAULT_TYPE = VisibilityType.REGEX;
    private static final VisibilityType UPDATED_TYPE = VisibilityType.VALUE;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final OperandType DEFAULT_OPERAND = OperandType.EQUALS;
    private static final OperandType UPDATED_OPERAND = OperandType.NOT_EQUALS;

    @Autowired
    private EVisibilityRepository eVisibilityRepository;

    @Autowired
    private EVisibilityMapper eVisibilityMapper;

    @Autowired
    private EVisibilityService eVisibilityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEVisibilityMockMvc;

    private EVisibility eVisibility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EVisibilityResource eVisibilityResource = new EVisibilityResource(eVisibilityService);
        this.restEVisibilityMockMvc = MockMvcBuilders.standaloneSetup(eVisibilityResource)
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
    public static EVisibility createEntity(EntityManager em) {
        EVisibility eVisibility = new EVisibility()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .operand(DEFAULT_OPERAND);
        return eVisibility;
    }

    @Before
    public void initTest() {
        eVisibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createEVisibility() throws Exception {
        int databaseSizeBeforeCreate = eVisibilityRepository.findAll().size();

        // Create the EVisibility
        EVisibilityDTO eVisibilityDTO = eVisibilityMapper.toDto(eVisibility);
        restEVisibilityMockMvc.perform(post("/api/e-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eVisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the EVisibility in the database
        List<EVisibility> eVisibilityList = eVisibilityRepository.findAll();
        assertThat(eVisibilityList).hasSize(databaseSizeBeforeCreate + 1);
        EVisibility testEVisibility = eVisibilityList.get(eVisibilityList.size() - 1);
        assertThat(testEVisibility.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEVisibility.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEVisibility.getOperand()).isEqualTo(DEFAULT_OPERAND);
    }

    @Test
    @Transactional
    public void createEVisibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eVisibilityRepository.findAll().size();

        // Create the EVisibility with an existing ID
        eVisibility.setId(1L);
        EVisibilityDTO eVisibilityDTO = eVisibilityMapper.toDto(eVisibility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEVisibilityMockMvc.perform(post("/api/e-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eVisibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EVisibility in the database
        List<EVisibility> eVisibilityList = eVisibilityRepository.findAll();
        assertThat(eVisibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEVisibilities() throws Exception {
        // Initialize the database
        eVisibilityRepository.saveAndFlush(eVisibility);

        // Get all the eVisibilityList
        restEVisibilityMockMvc.perform(get("/api/e-visibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eVisibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].operand").value(hasItem(DEFAULT_OPERAND.toString())));
    }

    @Test
    @Transactional
    public void getEVisibility() throws Exception {
        // Initialize the database
        eVisibilityRepository.saveAndFlush(eVisibility);

        // Get the eVisibility
        restEVisibilityMockMvc.perform(get("/api/e-visibilities/{id}", eVisibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eVisibility.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.operand").value(DEFAULT_OPERAND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEVisibility() throws Exception {
        // Get the eVisibility
        restEVisibilityMockMvc.perform(get("/api/e-visibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEVisibility() throws Exception {
        // Initialize the database
        eVisibilityRepository.saveAndFlush(eVisibility);
        int databaseSizeBeforeUpdate = eVisibilityRepository.findAll().size();

        // Update the eVisibility
        EVisibility updatedEVisibility = eVisibilityRepository.findOne(eVisibility.getId());
        // Disconnect from session so that the updates on updatedEVisibility are not directly saved in db
        em.detach(updatedEVisibility);
        updatedEVisibility
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .operand(UPDATED_OPERAND);
        EVisibilityDTO eVisibilityDTO = eVisibilityMapper.toDto(updatedEVisibility);

        restEVisibilityMockMvc.perform(put("/api/e-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eVisibilityDTO)))
            .andExpect(status().isOk());

        // Validate the EVisibility in the database
        List<EVisibility> eVisibilityList = eVisibilityRepository.findAll();
        assertThat(eVisibilityList).hasSize(databaseSizeBeforeUpdate);
        EVisibility testEVisibility = eVisibilityList.get(eVisibilityList.size() - 1);
        assertThat(testEVisibility.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEVisibility.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEVisibility.getOperand()).isEqualTo(UPDATED_OPERAND);
    }

    @Test
    @Transactional
    public void updateNonExistingEVisibility() throws Exception {
        int databaseSizeBeforeUpdate = eVisibilityRepository.findAll().size();

        // Create the EVisibility
        EVisibilityDTO eVisibilityDTO = eVisibilityMapper.toDto(eVisibility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEVisibilityMockMvc.perform(put("/api/e-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eVisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the EVisibility in the database
        List<EVisibility> eVisibilityList = eVisibilityRepository.findAll();
        assertThat(eVisibilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEVisibility() throws Exception {
        // Initialize the database
        eVisibilityRepository.saveAndFlush(eVisibility);
        int databaseSizeBeforeDelete = eVisibilityRepository.findAll().size();

        // Get the eVisibility
        restEVisibilityMockMvc.perform(delete("/api/e-visibilities/{id}", eVisibility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EVisibility> eVisibilityList = eVisibilityRepository.findAll();
        assertThat(eVisibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EVisibility.class);
        EVisibility eVisibility1 = new EVisibility();
        eVisibility1.setId(1L);
        EVisibility eVisibility2 = new EVisibility();
        eVisibility2.setId(eVisibility1.getId());
        assertThat(eVisibility1).isEqualTo(eVisibility2);
        eVisibility2.setId(2L);
        assertThat(eVisibility1).isNotEqualTo(eVisibility2);
        eVisibility1.setId(null);
        assertThat(eVisibility1).isNotEqualTo(eVisibility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EVisibilityDTO.class);
        EVisibilityDTO eVisibilityDTO1 = new EVisibilityDTO();
        eVisibilityDTO1.setId(1L);
        EVisibilityDTO eVisibilityDTO2 = new EVisibilityDTO();
        assertThat(eVisibilityDTO1).isNotEqualTo(eVisibilityDTO2);
        eVisibilityDTO2.setId(eVisibilityDTO1.getId());
        assertThat(eVisibilityDTO1).isEqualTo(eVisibilityDTO2);
        eVisibilityDTO2.setId(2L);
        assertThat(eVisibilityDTO1).isNotEqualTo(eVisibilityDTO2);
        eVisibilityDTO1.setId(null);
        assertThat(eVisibilityDTO1).isNotEqualTo(eVisibilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eVisibilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eVisibilityMapper.fromId(null)).isNull();
    }
}
