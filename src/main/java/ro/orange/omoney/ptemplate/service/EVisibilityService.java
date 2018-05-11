package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.EVisibilityDTO;
import java.util.List;

/**
 * Service Interface for managing EVisibility.
 */
public interface EVisibilityService {

    /**
     * Save a eVisibility.
     *
     * @param eVisibilityDTO the entity to save
     * @return the persisted entity
     */
    EVisibilityDTO save(EVisibilityDTO eVisibilityDTO);

    /**
     * Get all the eVisibilities.
     *
     * @return the list of entities
     */
    List<EVisibilityDTO> findAll();

    /**
     * Get the "id" eVisibility.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EVisibilityDTO findOne(Long id);

    /**
     * Delete the "id" eVisibility.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
