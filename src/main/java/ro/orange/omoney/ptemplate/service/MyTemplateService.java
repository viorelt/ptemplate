package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.MyTemplateDTO;
import java.util.List;

/**
 * Service Interface for managing MyTemplate.
 */
public interface MyTemplateService {

    /**
     * Save a myTemplate.
     *
     * @param myTemplateDTO the entity to save
     * @return the persisted entity
     */
    MyTemplateDTO save(MyTemplateDTO myTemplateDTO);

    /**
     * Get all the myTemplates.
     *
     * @return the list of entities
     */
    List<MyTemplateDTO> findAll();

    /**
     * Get the "id" myTemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MyTemplateDTO findOne(Long id);

    /**
     * Delete the "id" myTemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
