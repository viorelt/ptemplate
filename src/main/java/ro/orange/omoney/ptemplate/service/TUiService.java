package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TUiDTO;
import java.util.List;

/**
 * Service Interface for managing TUi.
 */
public interface TUiService {

    /**
     * Save a tUi.
     *
     * @param tUiDTO the entity to save
     * @return the persisted entity
     */
    TUiDTO save(TUiDTO tUiDTO);

    /**
     * Get all the tUis.
     *
     * @return the list of entities
     */
    List<TUiDTO> findAll();

    /**
     * Get the "id" tUi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TUiDTO findOne(Long id);

    /**
     * Delete the "id" tUi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
