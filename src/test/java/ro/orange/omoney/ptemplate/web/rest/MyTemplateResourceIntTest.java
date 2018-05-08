package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.MyTemplate;
import ro.orange.omoney.ptemplate.repository.MyTemplateRepository;
import ro.orange.omoney.ptemplate.service.MyTemplateService;
import ro.orange.omoney.ptemplate.service.dto.MyTemplateDTO;
import ro.orange.omoney.ptemplate.service.mapper.MyTemplateMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ro.orange.omoney.ptemplate.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MyTemplateResource REST controller.
 *
 * @see MyTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class MyTemplateResourceIntTest {

    private static final Long DEFAULT_ACCOUNT_ID = 1L;
    private static final Long UPDATED_ACCOUNT_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MyTemplateRepository myTemplateRepository;

    @Autowired
    private MyTemplateMapper myTemplateMapper;

    @Autowired
    private MyTemplateService myTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyTemplateMockMvc;

    private MyTemplate myTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyTemplateResource myTemplateResource = new MyTemplateResource(myTemplateService);
        this.restMyTemplateMockMvc = MockMvcBuilders.standaloneSetup(myTemplateResource)
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
    public static MyTemplate createEntity(EntityManager em) {
        MyTemplate myTemplate = new MyTemplate()
            .accountId(DEFAULT_ACCOUNT_ID)
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return myTemplate;
    }

    @Before
    public void initTest() {
        myTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyTemplate() throws Exception {
        int databaseSizeBeforeCreate = myTemplateRepository.findAll().size();

        // Create the MyTemplate
        MyTemplateDTO myTemplateDTO = myTemplateMapper.toDto(myTemplate);
        restMyTemplateMockMvc.perform(post("/api/my-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the MyTemplate in the database
        List<MyTemplate> myTemplateList = myTemplateRepository.findAll();
        assertThat(myTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        MyTemplate testMyTemplate = myTemplateList.get(myTemplateList.size() - 1);
        assertThat(testMyTemplate.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testMyTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMyTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createMyTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myTemplateRepository.findAll().size();

        // Create the MyTemplate with an existing ID
        myTemplate.setId(1L);
        MyTemplateDTO myTemplateDTO = myTemplateMapper.toDto(myTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyTemplateMockMvc.perform(post("/api/my-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MyTemplate in the database
        List<MyTemplate> myTemplateList = myTemplateRepository.findAll();
        assertThat(myTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyTemplates() throws Exception {
        // Initialize the database
        myTemplateRepository.saveAndFlush(myTemplate);

        // Get all the myTemplateList
        restMyTemplateMockMvc.perform(get("/api/my-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMyTemplate() throws Exception {
        // Initialize the database
        myTemplateRepository.saveAndFlush(myTemplate);

        // Get the myTemplate
        restMyTemplateMockMvc.perform(get("/api/my-templates/{id}", myTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myTemplate.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyTemplate() throws Exception {
        // Get the myTemplate
        restMyTemplateMockMvc.perform(get("/api/my-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyTemplate() throws Exception {
        // Initialize the database
        myTemplateRepository.saveAndFlush(myTemplate);
        int databaseSizeBeforeUpdate = myTemplateRepository.findAll().size();

        // Update the myTemplate
        MyTemplate updatedMyTemplate = myTemplateRepository.findOne(myTemplate.getId());
        // Disconnect from session so that the updates on updatedMyTemplate are not directly saved in db
        em.detach(updatedMyTemplate);
        updatedMyTemplate
            .accountId(UPDATED_ACCOUNT_ID)
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        MyTemplateDTO myTemplateDTO = myTemplateMapper.toDto(updatedMyTemplate);

        restMyTemplateMockMvc.perform(put("/api/my-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the MyTemplate in the database
        List<MyTemplate> myTemplateList = myTemplateRepository.findAll();
        assertThat(myTemplateList).hasSize(databaseSizeBeforeUpdate);
        MyTemplate testMyTemplate = myTemplateList.get(myTemplateList.size() - 1);
        assertThat(testMyTemplate.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testMyTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMyTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyTemplate() throws Exception {
        int databaseSizeBeforeUpdate = myTemplateRepository.findAll().size();

        // Create the MyTemplate
        MyTemplateDTO myTemplateDTO = myTemplateMapper.toDto(myTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyTemplateMockMvc.perform(put("/api/my-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the MyTemplate in the database
        List<MyTemplate> myTemplateList = myTemplateRepository.findAll();
        assertThat(myTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyTemplate() throws Exception {
        // Initialize the database
        myTemplateRepository.saveAndFlush(myTemplate);
        int databaseSizeBeforeDelete = myTemplateRepository.findAll().size();

        // Get the myTemplate
        restMyTemplateMockMvc.perform(delete("/api/my-templates/{id}", myTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyTemplate> myTemplateList = myTemplateRepository.findAll();
        assertThat(myTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyTemplate.class);
        MyTemplate myTemplate1 = new MyTemplate();
        myTemplate1.setId(1L);
        MyTemplate myTemplate2 = new MyTemplate();
        myTemplate2.setId(myTemplate1.getId());
        assertThat(myTemplate1).isEqualTo(myTemplate2);
        myTemplate2.setId(2L);
        assertThat(myTemplate1).isNotEqualTo(myTemplate2);
        myTemplate1.setId(null);
        assertThat(myTemplate1).isNotEqualTo(myTemplate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyTemplateDTO.class);
        MyTemplateDTO myTemplateDTO1 = new MyTemplateDTO();
        myTemplateDTO1.setId(1L);
        MyTemplateDTO myTemplateDTO2 = new MyTemplateDTO();
        assertThat(myTemplateDTO1).isNotEqualTo(myTemplateDTO2);
        myTemplateDTO2.setId(myTemplateDTO1.getId());
        assertThat(myTemplateDTO1).isEqualTo(myTemplateDTO2);
        myTemplateDTO2.setId(2L);
        assertThat(myTemplateDTO1).isNotEqualTo(myTemplateDTO2);
        myTemplateDTO1.setId(null);
        assertThat(myTemplateDTO1).isNotEqualTo(myTemplateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(myTemplateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(myTemplateMapper.fromId(null)).isNull();
    }
}
