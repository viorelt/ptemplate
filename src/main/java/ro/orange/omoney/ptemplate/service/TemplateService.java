package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TemplateDTO;
import java.util.List;

/**
 * Service Interface for managing Template.
 */
public interface TemplateService {

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    TemplateDTO save(TemplateDTO templateDTO);

    /**
     * Get all the templates.
     *
     * @return the list of entities
     */
    List<TemplateDTO> findAll();

    /**
     * Get the "id" template.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TemplateDTO findOne(Long id);

    /**
     * Delete the "id" template.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
