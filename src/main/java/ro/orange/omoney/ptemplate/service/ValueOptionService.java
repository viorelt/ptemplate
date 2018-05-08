package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.ValueOptionDTO;
import java.util.List;

/**
 * Service Interface for managing ValueOption.
 */
public interface ValueOptionService {

    /**
     * Save a valueOption.
     *
     * @param valueOptionDTO the entity to save
     * @return the persisted entity
     */
    ValueOptionDTO save(ValueOptionDTO valueOptionDTO);

    /**
     * Get all the valueOptions.
     *
     * @return the list of entities
     */
    List<ValueOptionDTO> findAll();

    /**
     * Get the "id" valueOption.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ValueOptionDTO findOne(Long id);

    /**
     * Delete the "id" valueOption.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
