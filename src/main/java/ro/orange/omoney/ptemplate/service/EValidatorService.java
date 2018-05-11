package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.EValidatorDTO;
import java.util.List;

/**
 * Service Interface for managing EValidator.
 */
public interface EValidatorService {

    /**
     * Save a eValidator.
     *
     * @param eValidatorDTO the entity to save
     * @return the persisted entity
     */
    EValidatorDTO save(EValidatorDTO eValidatorDTO);

    /**
     * Get all the eValidators.
     *
     * @return the list of entities
     */
    List<EValidatorDTO> findAll();

    /**
     * Get the "id" eValidator.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EValidatorDTO findOne(Long id);

    /**
     * Delete the "id" eValidator.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
