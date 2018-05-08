package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.EUiDTO;
import java.util.List;

/**
 * Service Interface for managing EUi.
 */
public interface EUiService {

    /**
     * Save a eUi.
     *
     * @param eUiDTO the entity to save
     * @return the persisted entity
     */
    EUiDTO save(EUiDTO eUiDTO);

    /**
     * Get all the eUis.
     *
     * @return the list of entities
     */
    List<EUiDTO> findAll();

    /**
     * Get the "id" eUi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EUiDTO findOne(Long id);

    /**
     * Delete the "id" eUi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
