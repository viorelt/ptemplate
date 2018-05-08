package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.EValue;
import ro.orange.omoney.ptemplate.repository.EValueRepository;
import ro.orange.omoney.ptemplate.service.EValueService;
import ro.orange.omoney.ptemplate.service.dto.EValueDTO;
import ro.orange.omoney.ptemplate.service.mapper.EValueMapper;
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
 * Test class for the EValueResource REST controller.
 *
 * @see EValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class EValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private EValueRepository eValueRepository;

    @Autowired
    private EValueMapper eValueMapper;

    @Autowired
    private EValueService eValueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEValueMockMvc;

    private EValue eValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EValueResource eValueResource = new EValueResource(eValueService);
        this.restEValueMockMvc = MockMvcBuilders.standaloneSetup(eValueResource)
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
    public static EValue createEntity(EntityManager em) {
        EValue eValue = new EValue()
            .value(DEFAULT_VALUE);
        return eValue;
    }

    @Before
    public void initTest() {
        eValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createEValue() throws Exception {
        int databaseSizeBeforeCreate = eValueRepository.findAll().size();

        // Create the EValue
        EValueDTO eValueDTO = eValueMapper.toDto(eValue);
        restEValueMockMvc.perform(post("/api/e-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValueDTO)))
            .andExpect(status().isCreated());

        // Validate the EValue in the database
        List<EValue> eValueList = eValueRepository.findAll();
        assertThat(eValueList).hasSize(databaseSizeBeforeCreate + 1);
        EValue testEValue = eValueList.get(eValueList.size() - 1);
        assertThat(testEValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createEValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eValueRepository.findAll().size();

        // Create the EValue with an existing ID
        eValue.setId(1L);
        EValueDTO eValueDTO = eValueMapper.toDto(eValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEValueMockMvc.perform(post("/api/e-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EValue in the database
        List<EValue> eValueList = eValueRepository.findAll();
        assertThat(eValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEValues() throws Exception {
        // Initialize the database
        eValueRepository.saveAndFlush(eValue);

        // Get all the eValueList
        restEValueMockMvc.perform(get("/api/e-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getEValue() throws Exception {
        // Initialize the database
        eValueRepository.saveAndFlush(eValue);

        // Get the eValue
        restEValueMockMvc.perform(get("/api/e-values/{id}", eValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEValue() throws Exception {
        // Get the eValue
        restEValueMockMvc.perform(get("/api/e-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEValue() throws Exception {
        // Initialize the database
        eValueRepository.saveAndFlush(eValue);
        int databaseSizeBeforeUpdate = eValueRepository.findAll().size();

        // Update the eValue
        EValue updatedEValue = eValueRepository.findOne(eValue.getId());
        // Disconnect from session so that the updates on updatedEValue are not directly saved in db
        em.detach(updatedEValue);
        updatedEValue
            .value(UPDATED_VALUE);
        EValueDTO eValueDTO = eValueMapper.toDto(updatedEValue);

        restEValueMockMvc.perform(put("/api/e-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValueDTO)))
            .andExpect(status().isOk());

        // Validate the EValue in the database
        List<EValue> eValueList = eValueRepository.findAll();
        assertThat(eValueList).hasSize(databaseSizeBeforeUpdate);
        EValue testEValue = eValueList.get(eValueList.size() - 1);
        assertThat(testEValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingEValue() throws Exception {
        int databaseSizeBeforeUpdate = eValueRepository.findAll().size();

        // Create the EValue
        EValueDTO eValueDTO = eValueMapper.toDto(eValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEValueMockMvc.perform(put("/api/e-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eValueDTO)))
            .andExpect(status().isCreated());

        // Validate the EValue in the database
        List<EValue> eValueList = eValueRepository.findAll();
        assertThat(eValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEValue() throws Exception {
        // Initialize the database
        eValueRepository.saveAndFlush(eValue);
        int databaseSizeBeforeDelete = eValueRepository.findAll().size();

        // Get the eValue
        restEValueMockMvc.perform(delete("/api/e-values/{id}", eValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EValue> eValueList = eValueRepository.findAll();
        assertThat(eValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EValue.class);
        EValue eValue1 = new EValue();
        eValue1.setId(1L);
        EValue eValue2 = new EValue();
        eValue2.setId(eValue1.getId());
        assertThat(eValue1).isEqualTo(eValue2);
        eValue2.setId(2L);
        assertThat(eValue1).isNotEqualTo(eValue2);
        eValue1.setId(null);
        assertThat(eValue1).isNotEqualTo(eValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EValueDTO.class);
        EValueDTO eValueDTO1 = new EValueDTO();
        eValueDTO1.setId(1L);
        EValueDTO eValueDTO2 = new EValueDTO();
        assertThat(eValueDTO1).isNotEqualTo(eValueDTO2);
        eValueDTO2.setId(eValueDTO1.getId());
        assertThat(eValueDTO1).isEqualTo(eValueDTO2);
        eValueDTO2.setId(2L);
        assertThat(eValueDTO1).isNotEqualTo(eValueDTO2);
        eValueDTO1.setId(null);
        assertThat(eValueDTO1).isNotEqualTo(eValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eValueMapper.fromId(null)).isNull();
    }
}
