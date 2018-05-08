package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TBackendDTO;
import java.util.List;

/**
 * Service Interface for managing TBackend.
 */
public interface TBackendService {

    /**
     * Save a tBackend.
     *
     * @param tBackendDTO the entity to save
     * @return the persisted entity
     */
    TBackendDTO save(TBackendDTO tBackendDTO);

    /**
     * Get all the tBackends.
     *
     * @return the list of entities
     */
    List<TBackendDTO> findAll();

    /**
     * Get the "id" tBackend.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TBackendDTO findOne(Long id);

    /**
     * Delete the "id" tBackend.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
