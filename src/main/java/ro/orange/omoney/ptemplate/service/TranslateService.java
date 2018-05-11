package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TranslateDTO;
import java.util.List;

/**
 * Service Interface for managing Translate.
 */
public interface TranslateService {

    /**
     * Save a translate.
     *
     * @param translateDTO the entity to save
     * @return the persisted entity
     */
    TranslateDTO save(TranslateDTO translateDTO);

    /**
     * Get all the translates.
     *
     * @return the list of entities
     */
    List<TranslateDTO> findAll();

    /**
     * Get the "id" translate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TranslateDTO findOne(Long id);

    /**
     * Delete the "id" translate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
