package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.ValueOption;
import ro.orange.omoney.ptemplate.repository.ValueOptionRepository;
import ro.orange.omoney.ptemplate.service.ValueOptionService;
import ro.orange.omoney.ptemplate.service.dto.ValueOptionDTO;
import ro.orange.omoney.ptemplate.service.mapper.ValueOptionMapper;
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
 * Test class for the ValueOptionResource REST controller.
 *
 * @see ValueOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class ValueOptionResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ValueOptionRepository valueOptionRepository;

    @Autowired
    private ValueOptionMapper valueOptionMapper;

    @Autowired
    private ValueOptionService valueOptionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValueOptionMockMvc;

    private ValueOption valueOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValueOptionResource valueOptionResource = new ValueOptionResource(valueOptionService);
        this.restValueOptionMockMvc = MockMvcBuilders.standaloneSetup(valueOptionResource)
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
    public static ValueOption createEntity(EntityManager em) {
        ValueOption valueOption = new ValueOption()
            .value(DEFAULT_VALUE);
        return valueOption;
    }

    @Before
    public void initTest() {
        valueOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createValueOption() throws Exception {
        int databaseSizeBeforeCreate = valueOptionRepository.findAll().size();

        // Create the ValueOption
        ValueOptionDTO valueOptionDTO = valueOptionMapper.toDto(valueOption);
        restValueOptionMockMvc.perform(post("/api/value-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ValueOption in the database
        List<ValueOption> valueOptionList = valueOptionRepository.findAll();
        assertThat(valueOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ValueOption testValueOption = valueOptionList.get(valueOptionList.size() - 1);
        assertThat(testValueOption.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createValueOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valueOptionRepository.findAll().size();

        // Create the ValueOption with an existing ID
        valueOption.setId(1L);
        ValueOptionDTO valueOptionDTO = valueOptionMapper.toDto(valueOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValueOptionMockMvc.perform(post("/api/value-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ValueOption in the database
        List<ValueOption> valueOptionList = valueOptionRepository.findAll();
        assertThat(valueOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValueOptions() throws Exception {
        // Initialize the database
        valueOptionRepository.saveAndFlush(valueOption);

        // Get all the valueOptionList
        restValueOptionMockMvc.perform(get("/api/value-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valueOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getValueOption() throws Exception {
        // Initialize the database
        valueOptionRepository.saveAndFlush(valueOption);

        // Get the valueOption
        restValueOptionMockMvc.perform(get("/api/value-options/{id}", valueOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valueOption.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValueOption() throws Exception {
        // Get the valueOption
        restValueOptionMockMvc.perform(get("/api/value-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValueOption() throws Exception {
        // Initialize the database
        valueOptionRepository.saveAndFlush(valueOption);
        int databaseSizeBeforeUpdate = valueOptionRepository.findAll().size();

        // Update the valueOption
        ValueOption updatedValueOption = valueOptionRepository.findOne(valueOption.getId());
        // Disconnect from session so that the updates on updatedValueOption are not directly saved in db
        em.detach(updatedValueOption);
        updatedValueOption
            .value(UPDATED_VALUE);
        ValueOptionDTO valueOptionDTO = valueOptionMapper.toDto(updatedValueOption);

        restValueOptionMockMvc.perform(put("/api/value-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueOptionDTO)))
            .andExpect(status().isOk());

        // Validate the ValueOption in the database
        List<ValueOption> valueOptionList = valueOptionRepository.findAll();
        assertThat(valueOptionList).hasSize(databaseSizeBeforeUpdate);
        ValueOption testValueOption = valueOptionList.get(valueOptionList.size() - 1);
        assertThat(testValueOption.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingValueOption() throws Exception {
        int databaseSizeBeforeUpdate = valueOptionRepository.findAll().size();

        // Create the ValueOption
        ValueOptionDTO valueOptionDTO = valueOptionMapper.toDto(valueOption);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValueOptionMockMvc.perform(put("/api/value-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valueOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ValueOption in the database
        List<ValueOption> valueOptionList = valueOptionRepository.findAll();
        assertThat(valueOptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValueOption() throws Exception {
        // Initialize the database
        valueOptionRepository.saveAndFlush(valueOption);
        int databaseSizeBeforeDelete = valueOptionRepository.findAll().size();

        // Get the valueOption
        restValueOptionMockMvc.perform(delete("/api/value-options/{id}", valueOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValueOption> valueOptionList = valueOptionRepository.findAll();
        assertThat(valueOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueOption.class);
        ValueOption valueOption1 = new ValueOption();
        valueOption1.setId(1L);
        ValueOption valueOption2 = new ValueOption();
        valueOption2.setId(valueOption1.getId());
        assertThat(valueOption1).isEqualTo(valueOption2);
        valueOption2.setId(2L);
        assertThat(valueOption1).isNotEqualTo(valueOption2);
        valueOption1.setId(null);
        assertThat(valueOption1).isNotEqualTo(valueOption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValueOptionDTO.class);
        ValueOptionDTO valueOptionDTO1 = new ValueOptionDTO();
        valueOptionDTO1.setId(1L);
        ValueOptionDTO valueOptionDTO2 = new ValueOptionDTO();
        assertThat(valueOptionDTO1).isNotEqualTo(valueOptionDTO2);
        valueOptionDTO2.setId(valueOptionDTO1.getId());
        assertThat(valueOptionDTO1).isEqualTo(valueOptionDTO2);
        valueOptionDTO2.setId(2L);
        assertThat(valueOptionDTO1).isNotEqualTo(valueOptionDTO2);
        valueOptionDTO1.setId(null);
        assertThat(valueOptionDTO1).isNotEqualTo(valueOptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(valueOptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(valueOptionMapper.fromId(null)).isNull();
    }
}
