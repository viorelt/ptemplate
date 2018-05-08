package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.ElementDTO;
import java.util.List;

/**
 * Service Interface for managing Element.
 */
public interface ElementService {

    /**
     * Save a element.
     *
     * @param elementDTO the entity to save
     * @return the persisted entity
     */
    ElementDTO save(ElementDTO elementDTO);

    /**
     * Get all the elements.
     *
     * @return the list of entities
     */
    List<ElementDTO> findAll();

    /**
     * Get the "id" element.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ElementDTO findOne(Long id);

    /**
     * Delete the "id" element.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
