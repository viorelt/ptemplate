package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.SubmitAction;
import ro.orange.omoney.ptemplate.repository.SubmitActionRepository;
import ro.orange.omoney.ptemplate.service.SubmitActionService;
import ro.orange.omoney.ptemplate.service.dto.SubmitActionDTO;
import ro.orange.omoney.ptemplate.service.mapper.SubmitActionMapper;
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
 * Test class for the SubmitActionResource REST controller.
 *
 * @see SubmitActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class SubmitActionResourceIntTest {

    private static final String DEFAULT_LABEL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT = "BBBBBBBBBB";

    @Autowired
    private SubmitActionRepository submitActionRepository;

    @Autowired
    private SubmitActionMapper submitActionMapper;

    @Autowired
    private SubmitActionService submitActionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubmitActionMockMvc;

    private SubmitAction submitAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubmitActionResource submitActionResource = new SubmitActionResource(submitActionService);
        this.restSubmitActionMockMvc = MockMvcBuilders.standaloneSetup(submitActionResource)
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
    public static SubmitAction createEntity(EntityManager em) {
        SubmitAction submitAction = new SubmitAction()
            .labelKey(DEFAULT_LABEL_KEY)
            .endpoint(DEFAULT_ENDPOINT);
        return submitAction;
    }

    @Before
    public void initTest() {
        submitAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubmitAction() throws Exception {
        int databaseSizeBeforeCreate = submitActionRepository.findAll().size();

        // Create the SubmitAction
        SubmitActionDTO submitActionDTO = submitActionMapper.toDto(submitAction);
        restSubmitActionMockMvc.perform(post("/api/submit-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submitActionDTO)))
            .andExpect(status().isCreated());

        // Validate the SubmitAction in the database
        List<SubmitAction> submitActionList = submitActionRepository.findAll();
        assertThat(submitActionList).hasSize(databaseSizeBeforeCreate + 1);
        SubmitAction testSubmitAction = submitActionList.get(submitActionList.size() - 1);
        assertThat(testSubmitAction.getLabelKey()).isEqualTo(DEFAULT_LABEL_KEY);
        assertThat(testSubmitAction.getEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    }

    @Test
    @Transactional
    public void createSubmitActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = submitActionRepository.findAll().size();

        // Create the SubmitAction with an existing ID
        submitAction.setId(1L);
        SubmitActionDTO submitActionDTO = submitActionMapper.toDto(submitAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubmitActionMockMvc.perform(post("/api/submit-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submitActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubmitAction in the database
        List<SubmitAction> submitActionList = submitActionRepository.findAll();
        assertThat(submitActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubmitActions() throws Exception {
        // Initialize the database
        submitActionRepository.saveAndFlush(submitAction);

        // Get all the submitActionList
        restSubmitActionMockMvc.perform(get("/api/submit-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submitAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelKey").value(hasItem(DEFAULT_LABEL_KEY.toString())))
            .andExpect(jsonPath("$.[*].endpoint").value(hasItem(DEFAULT_ENDPOINT.toString())));
    }

    @Test
    @Transactional
    public void getSubmitAction() throws Exception {
        // Initialize the database
        submitActionRepository.saveAndFlush(submitAction);

        // Get the submitAction
        restSubmitActionMockMvc.perform(get("/api/submit-actions/{id}", submitAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(submitAction.getId().intValue()))
            .andExpect(jsonPath("$.labelKey").value(DEFAULT_LABEL_KEY.toString()))
            .andExpect(jsonPath("$.endpoint").value(DEFAULT_ENDPOINT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubmitAction() throws Exception {
        // Get the submitAction
        restSubmitActionMockMvc.perform(get("/api/submit-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubmitAction() throws Exception {
        // Initialize the database
        submitActionRepository.saveAndFlush(submitAction);
        int databaseSizeBeforeUpdate = submitActionRepository.findAll().size();

        // Update the submitAction
        SubmitAction updatedSubmitAction = submitActionRepository.findOne(submitAction.getId());
        // Disconnect from session so that the updates on updatedSubmitAction are not directly saved in db
        em.detach(updatedSubmitAction);
        updatedSubmitAction
            .labelKey(UPDATED_LABEL_KEY)
            .endpoint(UPDATED_ENDPOINT);
        SubmitActionDTO submitActionDTO = submitActionMapper.toDto(updatedSubmitAction);

        restSubmitActionMockMvc.perform(put("/api/submit-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submitActionDTO)))
            .andExpect(status().isOk());

        // Validate the SubmitAction in the database
        List<SubmitAction> submitActionList = submitActionRepository.findAll();
        assertThat(submitActionList).hasSize(databaseSizeBeforeUpdate);
        SubmitAction testSubmitAction = submitActionList.get(submitActionList.size() - 1);
        assertThat(testSubmitAction.getLabelKey()).isEqualTo(UPDATED_LABEL_KEY);
        assertThat(testSubmitAction.getEndpoint()).isEqualTo(UPDATED_ENDPOINT);
    }

    @Test
    @Transactional
    public void updateNonExistingSubmitAction() throws Exception {
        int databaseSizeBeforeUpdate = submitActionRepository.findAll().size();

        // Create the SubmitAction
        SubmitActionDTO submitActionDTO = submitActionMapper.toDto(submitAction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubmitActionMockMvc.perform(put("/api/submit-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submitActionDTO)))
            .andExpect(status().isCreated());

        // Validate the SubmitAction in the database
        List<SubmitAction> submitActionList = submitActionRepository.findAll();
        assertThat(submitActionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubmitAction() throws Exception {
        // Initialize the database
        submitActionRepository.saveAndFlush(submitAction);
        int databaseSizeBeforeDelete = submitActionRepository.findAll().size();

        // Get the submitAction
        restSubmitActionMockMvc.perform(delete("/api/submit-actions/{id}", submitAction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubmitAction> submitActionList = submitActionRepository.findAll();
        assertThat(submitActionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmitAction.class);
        SubmitAction submitAction1 = new SubmitAction();
        submitAction1.setId(1L);
        SubmitAction submitAction2 = new SubmitAction();
        submitAction2.setId(submitAction1.getId());
        assertThat(submitAction1).isEqualTo(submitAction2);
        submitAction2.setId(2L);
        assertThat(submitAction1).isNotEqualTo(submitAction2);
        submitAction1.setId(null);
        assertThat(submitAction1).isNotEqualTo(submitAction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmitActionDTO.class);
        SubmitActionDTO submitActionDTO1 = new SubmitActionDTO();
        submitActionDTO1.setId(1L);
        SubmitActionDTO submitActionDTO2 = new SubmitActionDTO();
        assertThat(submitActionDTO1).isNotEqualTo(submitActionDTO2);
        submitActionDTO2.setId(submitActionDTO1.getId());
        assertThat(submitActionDTO1).isEqualTo(submitActionDTO2);
        submitActionDTO2.setId(2L);
        assertThat(submitActionDTO1).isNotEqualTo(submitActionDTO2);
        submitActionDTO1.setId(null);
        assertThat(submitActionDTO1).isNotEqualTo(submitActionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(submitActionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(submitActionMapper.fromId(null)).isNull();
    }
}
