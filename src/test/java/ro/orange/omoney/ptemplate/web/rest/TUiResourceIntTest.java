package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.TUi;
import ro.orange.omoney.ptemplate.repository.TUiRepository;
import ro.orange.omoney.ptemplate.service.TUiService;
import ro.orange.omoney.ptemplate.service.dto.TUiDTO;
import ro.orange.omoney.ptemplate.service.mapper.TUiMapper;
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
 * Test class for the TUiResource REST controller.
 *
 * @see TUiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class TUiResourceIntTest {

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_BOX_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BOX_COLOR = "BBBBBBBBBB";

    @Autowired
    private TUiRepository tUiRepository;

    @Autowired
    private TUiMapper tUiMapper;

    @Autowired
    private TUiService tUiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTUiMockMvc;

    private TUi tUi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TUiResource tUiResource = new TUiResource(tUiService);
        this.restTUiMockMvc = MockMvcBuilders.standaloneSetup(tUiResource)
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
    public static TUi createEntity(EntityManager em) {
        TUi tUi = new TUi()
            .icon(DEFAULT_ICON)
            .boxColor(DEFAULT_BOX_COLOR);
        return tUi;
    }

    @Before
    public void initTest() {
        tUi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTUi() throws Exception {
        int databaseSizeBeforeCreate = tUiRepository.findAll().size();

        // Create the TUi
        TUiDTO tUiDTO = tUiMapper.toDto(tUi);
        restTUiMockMvc.perform(post("/api/t-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUiDTO)))
            .andExpect(status().isCreated());

        // Validate the TUi in the database
        List<TUi> tUiList = tUiRepository.findAll();
        assertThat(tUiList).hasSize(databaseSizeBeforeCreate + 1);
        TUi testTUi = tUiList.get(tUiList.size() - 1);
        assertThat(testTUi.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testTUi.getBoxColor()).isEqualTo(DEFAULT_BOX_COLOR);
    }

    @Test
    @Transactional
    public void createTUiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tUiRepository.findAll().size();

        // Create the TUi with an existing ID
        tUi.setId(1L);
        TUiDTO tUiDTO = tUiMapper.toDto(tUi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTUiMockMvc.perform(post("/api/t-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TUi in the database
        List<TUi> tUiList = tUiRepository.findAll();
        assertThat(tUiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTUis() throws Exception {
        // Initialize the database
        tUiRepository.saveAndFlush(tUi);

        // Get all the tUiList
        restTUiMockMvc.perform(get("/api/t-uis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tUi.getId().intValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].boxColor").value(hasItem(DEFAULT_BOX_COLOR.toString())));
    }

    @Test
    @Transactional
    public void getTUi() throws Exception {
        // Initialize the database
        tUiRepository.saveAndFlush(tUi);

        // Get the tUi
        restTUiMockMvc.perform(get("/api/t-uis/{id}", tUi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tUi.getId().intValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.boxColor").value(DEFAULT_BOX_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTUi() throws Exception {
        // Get the tUi
        restTUiMockMvc.perform(get("/api/t-uis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTUi() throws Exception {
        // Initialize the database
        tUiRepository.saveAndFlush(tUi);
        int databaseSizeBeforeUpdate = tUiRepository.findAll().size();

        // Update the tUi
        TUi updatedTUi = tUiRepository.findOne(tUi.getId());
        // Disconnect from session so that the updates on updatedTUi are not directly saved in db
        em.detach(updatedTUi);
        updatedTUi
            .icon(UPDATED_ICON)
            .boxColor(UPDATED_BOX_COLOR);
        TUiDTO tUiDTO = tUiMapper.toDto(updatedTUi);

        restTUiMockMvc.perform(put("/api/t-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUiDTO)))
            .andExpect(status().isOk());

        // Validate the TUi in the database
        List<TUi> tUiList = tUiRepository.findAll();
        assertThat(tUiList).hasSize(databaseSizeBeforeUpdate);
        TUi testTUi = tUiList.get(tUiList.size() - 1);
        assertThat(testTUi.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testTUi.getBoxColor()).isEqualTo(UPDATED_BOX_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTUi() throws Exception {
        int databaseSizeBeforeUpdate = tUiRepository.findAll().size();

        // Create the TUi
        TUiDTO tUiDTO = tUiMapper.toDto(tUi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTUiMockMvc.perform(put("/api/t-uis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tUiDTO)))
            .andExpect(status().isCreated());

        // Validate the TUi in the database
        List<TUi> tUiList = tUiRepository.findAll();
        assertThat(tUiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTUi() throws Exception {
        // Initialize the database
        tUiRepository.saveAndFlush(tUi);
        int databaseSizeBeforeDelete = tUiRepository.findAll().size();

        // Get the tUi
        restTUiMockMvc.perform(delete("/api/t-uis/{id}", tUi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TUi> tUiList = tUiRepository.findAll();
        assertThat(tUiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TUi.class);
        TUi tUi1 = new TUi();
        tUi1.setId(1L);
        TUi tUi2 = new TUi();
        tUi2.setId(tUi1.getId());
        assertThat(tUi1).isEqualTo(tUi2);
        tUi2.setId(2L);
        assertThat(tUi1).isNotEqualTo(tUi2);
        tUi1.setId(null);
        assertThat(tUi1).isNotEqualTo(tUi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TUiDTO.class);
        TUiDTO tUiDTO1 = new TUiDTO();
        tUiDTO1.setId(1L);
        TUiDTO tUiDTO2 = new TUiDTO();
        assertThat(tUiDTO1).isNotEqualTo(tUiDTO2);
        tUiDTO2.setId(tUiDTO1.getId());
        assertThat(tUiDTO1).isEqualTo(tUiDTO2);
        tUiDTO2.setId(2L);
        assertThat(tUiDTO1).isNotEqualTo(tUiDTO2);
        tUiDTO1.setId(null);
        assertThat(tUiDTO1).isNotEqualTo(tUiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tUiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tUiMapper.fromId(null)).isNull();
    }
}
