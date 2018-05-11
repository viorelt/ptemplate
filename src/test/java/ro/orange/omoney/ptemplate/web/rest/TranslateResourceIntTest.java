package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.Translate;
import ro.orange.omoney.ptemplate.repository.TranslateRepository;
import ro.orange.omoney.ptemplate.service.TranslateService;
import ro.orange.omoney.ptemplate.service.dto.TranslateDTO;
import ro.orange.omoney.ptemplate.service.mapper.TranslateMapper;
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
 * Test class for the TranslateResource REST controller.
 *
 * @see TranslateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class TranslateResourceIntTest {

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private TranslateRepository translateRepository;

    @Autowired
    private TranslateMapper translateMapper;

    @Autowired
    private TranslateService translateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTranslateMockMvc;

    private Translate translate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranslateResource translateResource = new TranslateResource(translateService);
        this.restTranslateMockMvc = MockMvcBuilders.standaloneSetup(translateResource)
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
    public static Translate createEntity(EntityManager em) {
        Translate translate = new Translate()
            .lang(DEFAULT_LANG)
            .value(DEFAULT_VALUE);
        return translate;
    }

    @Before
    public void initTest() {
        translate = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslate() throws Exception {
        int databaseSizeBeforeCreate = translateRepository.findAll().size();

        // Create the Translate
        TranslateDTO translateDTO = translateMapper.toDto(translate);
        restTranslateMockMvc.perform(post("/api/translates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translateDTO)))
            .andExpect(status().isCreated());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeCreate + 1);
        Translate testTranslate = translateList.get(translateList.size() - 1);
        assertThat(testTranslate.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testTranslate.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createTranslateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translateRepository.findAll().size();

        // Create the Translate with an existing ID
        translate.setId(1L);
        TranslateDTO translateDTO = translateMapper.toDto(translate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslateMockMvc.perform(post("/api/translates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTranslates() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        // Get all the translateList
        restTranslateMockMvc.perform(get("/api/translates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translate.getId().intValue())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        // Get the translate
        restTranslateMockMvc.perform(get("/api/translates/{id}", translate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(translate.getId().intValue()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTranslate() throws Exception {
        // Get the translate
        restTranslateMockMvc.perform(get("/api/translates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);
        int databaseSizeBeforeUpdate = translateRepository.findAll().size();

        // Update the translate
        Translate updatedTranslate = translateRepository.findOne(translate.getId());
        // Disconnect from session so that the updates on updatedTranslate are not directly saved in db
        em.detach(updatedTranslate);
        updatedTranslate
            .lang(UPDATED_LANG)
            .value(UPDATED_VALUE);
        TranslateDTO translateDTO = translateMapper.toDto(updatedTranslate);

        restTranslateMockMvc.perform(put("/api/translates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translateDTO)))
            .andExpect(status().isOk());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeUpdate);
        Translate testTranslate = translateList.get(translateList.size() - 1);
        assertThat(testTranslate.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testTranslate.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslate() throws Exception {
        int databaseSizeBeforeUpdate = translateRepository.findAll().size();

        // Create the Translate
        TranslateDTO translateDTO = translateMapper.toDto(translate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTranslateMockMvc.perform(put("/api/translates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translateDTO)))
            .andExpect(status().isCreated());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);
        int databaseSizeBeforeDelete = translateRepository.findAll().size();

        // Get the translate
        restTranslateMockMvc.perform(delete("/api/translates/{id}", translate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Translate.class);
        Translate translate1 = new Translate();
        translate1.setId(1L);
        Translate translate2 = new Translate();
        translate2.setId(translate1.getId());
        assertThat(translate1).isEqualTo(translate2);
        translate2.setId(2L);
        assertThat(translate1).isNotEqualTo(translate2);
        translate1.setId(null);
        assertThat(translate1).isNotEqualTo(translate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslateDTO.class);
        TranslateDTO translateDTO1 = new TranslateDTO();
        translateDTO1.setId(1L);
        TranslateDTO translateDTO2 = new TranslateDTO();
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
        translateDTO2.setId(translateDTO1.getId());
        assertThat(translateDTO1).isEqualTo(translateDTO2);
        translateDTO2.setId(2L);
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
        translateDTO1.setId(null);
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(translateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(translateMapper.fromId(null)).isNull();
    }
}
