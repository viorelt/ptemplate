package ro.orange.omoney.ptemplate.web.rest;

import ro.orange.omoney.ptemplate.PtemplateApp;

import ro.orange.omoney.ptemplate.domain.Element;
import ro.orange.omoney.ptemplate.repository.ElementRepository;
import ro.orange.omoney.ptemplate.service.ElementService;
import ro.orange.omoney.ptemplate.service.dto.ElementDTO;
import ro.orange.omoney.ptemplate.service.mapper.ElementMapper;
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
 * Test class for the ElementResource REST controller.
 *
 * @see ElementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtemplateApp.class)
public class ElementResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private ElementMapper elementMapper;

    @Autowired
    private ElementService elementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElementMockMvc;

    private Element element;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElementResource elementResource = new ElementResource(elementService);
        this.restElementMockMvc = MockMvcBuilders.standaloneSetup(elementResource)
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
    public static Element createEntity(EntityManager em) {
        Element element = new Element()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED);
        return element;
    }

    @Before
    public void initTest() {
        element = createEntity(em);
    }

    @Test
    @Transactional
    public void createElement() throws Exception {
        int databaseSizeBeforeCreate = elementRepository.findAll().size();

        // Create the Element
        ElementDTO elementDTO = elementMapper.toDto(element);
        restElementMockMvc.perform(post("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementDTO)))
            .andExpect(status().isCreated());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate + 1);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testElement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElement.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createElementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementRepository.findAll().size();

        // Create the Element with an existing ID
        element.setId(1L);
        ElementDTO elementDTO = elementMapper.toDto(element);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementMockMvc.perform(post("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllElements() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList
        restElementMockMvc.perform(get("/api/elements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(element.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get the element
        restElementMockMvc.perform(get("/api/elements/{id}", element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(element.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElement() throws Exception {
        // Get the element
        restElementMockMvc.perform(get("/api/elements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Update the element
        Element updatedElement = elementRepository.findOne(element.getId());
        // Disconnect from session so that the updates on updatedElement are not directly saved in db
        em.detach(updatedElement);
        updatedElement
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED);
        ElementDTO elementDTO = elementMapper.toDto(updatedElement);

        restElementMockMvc.perform(put("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementDTO)))
            .andExpect(status().isOk());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testElement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElement.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Create the Element
        ElementDTO elementDTO = elementMapper.toDto(element);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restElementMockMvc.perform(put("/api/elements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementDTO)))
            .andExpect(status().isCreated());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);
        int databaseSizeBeforeDelete = elementRepository.findAll().size();

        // Get the element
        restElementMockMvc.perform(delete("/api/elements/{id}", element.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Element.class);
        Element element1 = new Element();
        element1.setId(1L);
        Element element2 = new Element();
        element2.setId(element1.getId());
        assertThat(element1).isEqualTo(element2);
        element2.setId(2L);
        assertThat(element1).isNotEqualTo(element2);
        element1.setId(null);
        assertThat(element1).isNotEqualTo(element2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementDTO.class);
        ElementDTO elementDTO1 = new ElementDTO();
        elementDTO1.setId(1L);
        ElementDTO elementDTO2 = new ElementDTO();
        assertThat(elementDTO1).isNotEqualTo(elementDTO2);
        elementDTO2.setId(elementDTO1.getId());
        assertThat(elementDTO1).isEqualTo(elementDTO2);
        elementDTO2.setId(2L);
        assertThat(elementDTO1).isNotEqualTo(elementDTO2);
        elementDTO1.setId(null);
        assertThat(elementDTO1).isNotEqualTo(elementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(elementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(elementMapper.fromId(null)).isNull();
    }
}
