package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.I18N;
import ro.orange.omoney.ptemplate.repository.I18NRepository;
import ro.orange.omoney.ptemplate.service.I18NService;
import ro.orange.omoney.ptemplate.service.dto.I18NDTO;
import ro.orange.omoney.ptemplate.service.mapper.I18NMapper;
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
 * Test class for the I18NResource REST controller.
 *
 * @see I18NResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class I18NResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private I18NRepository i18NRepository;

    @Autowired
    private I18NMapper i18NMapper;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restI18NMockMvc;

    private I18N i18N;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final I18NResource i18NResource = new I18NResource(i18NService);
        this.restI18NMockMvc = MockMvcBuilders.standaloneSetup(i18NResource)
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
    public static I18N createEntity(EntityManager em) {
        I18N i18N = new I18N()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return i18N;
    }

    @Before
    public void initTest() {
        i18N = createEntity(em);
    }

    @Test
    @Transactional
    public void createI18N() throws Exception {
        int databaseSizeBeforeCreate = i18NRepository.findAll().size();

        // Create the I18N
        I18NDTO i18NDTO = i18NMapper.toDto(i18N);
        restI18NMockMvc.perform(post("/api/i-18-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i18NDTO)))
            .andExpect(status().isCreated());

        // Validate the I18N in the database
        List<I18N> i18NList = i18NRepository.findAll();
        assertThat(i18NList).hasSize(databaseSizeBeforeCreate + 1);
        I18N testI18N = i18NList.get(i18NList.size() - 1);
        assertThat(testI18N.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testI18N.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createI18NWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = i18NRepository.findAll().size();

        // Create the I18N with an existing ID
        i18N.setId(1L);
        I18NDTO i18NDTO = i18NMapper.toDto(i18N);

        // An entity with an existing ID cannot be created, so this API call must fail
        restI18NMockMvc.perform(post("/api/i-18-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i18NDTO)))
            .andExpect(status().isBadRequest());

        // Validate the I18N in the database
        List<I18N> i18NList = i18NRepository.findAll();
        assertThat(i18NList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllI18NS() throws Exception {
        // Initialize the database
        i18NRepository.saveAndFlush(i18N);

        // Get all the i18NList
        restI18NMockMvc.perform(get("/api/i-18-ns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i18N.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getI18N() throws Exception {
        // Initialize the database
        i18NRepository.saveAndFlush(i18N);

        // Get the i18N
        restI18NMockMvc.perform(get("/api/i-18-ns/{id}", i18N.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(i18N.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingI18N() throws Exception {
        // Get the i18N
        restI18NMockMvc.perform(get("/api/i-18-ns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateI18N() throws Exception {
        // Initialize the database
        i18NRepository.saveAndFlush(i18N);
        int databaseSizeBeforeUpdate = i18NRepository.findAll().size();

        // Update the i18N
        I18N updatedI18N = i18NRepository.findOne(i18N.getId());
        // Disconnect from session so that the updates on updatedI18N are not directly saved in db
        em.detach(updatedI18N);
        updatedI18N
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        I18NDTO i18NDTO = i18NMapper.toDto(updatedI18N);

        restI18NMockMvc.perform(put("/api/i-18-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i18NDTO)))
            .andExpect(status().isOk());

        // Validate the I18N in the database
        List<I18N> i18NList = i18NRepository.findAll();
        assertThat(i18NList).hasSize(databaseSizeBeforeUpdate);
        I18N testI18N = i18NList.get(i18NList.size() - 1);
        assertThat(testI18N.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testI18N.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingI18N() throws Exception {
        int databaseSizeBeforeUpdate = i18NRepository.findAll().size();

        // Create the I18N
        I18NDTO i18NDTO = i18NMapper.toDto(i18N);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restI18NMockMvc.perform(put("/api/i-18-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(i18NDTO)))
            .andExpect(status().isCreated());

        // Validate the I18N in the database
        List<I18N> i18NList = i18NRepository.findAll();
        assertThat(i18NList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteI18N() throws Exception {
        // Initialize the database
        i18NRepository.saveAndFlush(i18N);
        int databaseSizeBeforeDelete = i18NRepository.findAll().size();

        // Get the i18N
        restI18NMockMvc.perform(delete("/api/i-18-ns/{id}", i18N.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<I18N> i18NList = i18NRepository.findAll();
        assertThat(i18NList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I18N.class);
        I18N i18N1 = new I18N();
        i18N1.setId(1L);
        I18N i18N2 = new I18N();
        i18N2.setId(i18N1.getId());
        assertThat(i18N1).isEqualTo(i18N2);
        i18N2.setId(2L);
        assertThat(i18N1).isNotEqualTo(i18N2);
        i18N1.setId(null);
        assertThat(i18N1).isNotEqualTo(i18N2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(I18NDTO.class);
        I18NDTO i18NDTO1 = new I18NDTO();
        i18NDTO1.setId(1L);
        I18NDTO i18NDTO2 = new I18NDTO();
        assertThat(i18NDTO1).isNotEqualTo(i18NDTO2);
        i18NDTO2.setId(i18NDTO1.getId());
        assertThat(i18NDTO1).isEqualTo(i18NDTO2);
        i18NDTO2.setId(2L);
        assertThat(i18NDTO1).isNotEqualTo(i18NDTO2);
        i18NDTO1.setId(null);
        assertThat(i18NDTO1).isNotEqualTo(i18NDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(i18NMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(i18NMapper.fromId(null)).isNull();
    }
}
