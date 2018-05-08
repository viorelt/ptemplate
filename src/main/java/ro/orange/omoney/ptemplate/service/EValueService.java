package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.EValueDTO;
import java.util.List;

/**
 * Service Interface for managing EValue.
 */
public interface EValueService {

    /**
     * Save a eValue.
     *
     * @param eValueDTO the entity to save
     * @return the persisted entity
     */
    EValueDTO save(EValueDTO eValueDTO);

    /**
     * Get all the eValues.
     *
     * @return the list of entities
     */
    List<EValueDTO> findAll();

    /**
     * Get the "id" eValue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EValueDTO findOne(Long id);

    /**
     * Delete the "id" eValue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
