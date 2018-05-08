package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.EBackendDTO;
import java.util.List;

/**
 * Service Interface for managing EBackend.
 */
public interface EBackendService {

    /**
     * Save a eBackend.
     *
     * @param eBackendDTO the entity to save
     * @return the persisted entity
     */
    EBackendDTO save(EBackendDTO eBackendDTO);

    /**
     * Get all the eBackends.
     *
     * @return the list of entities
     */
    List<EBackendDTO> findAll();

    /**
     * Get the "id" eBackend.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EBackendDTO findOne(Long id);

    /**
     * Delete the "id" eBackend.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
