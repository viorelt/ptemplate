package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.SubmitActionDTO;
import java.util.List;

/**
 * Service Interface for managing SubmitAction.
 */
public interface SubmitActionService {

    /**
     * Save a submitAction.
     *
     * @param submitActionDTO the entity to save
     * @return the persisted entity
     */
    SubmitActionDTO save(SubmitActionDTO submitActionDTO);

    /**
     * Get all the submitActions.
     *
     * @return the list of entities
     */
    List<SubmitActionDTO> findAll();

    /**
     * Get the "id" submitAction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SubmitActionDTO findOne(Long id);

    /**
     * Delete the "id" submitAction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
