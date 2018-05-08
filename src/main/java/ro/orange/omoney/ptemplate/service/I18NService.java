package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.I18NDTO;
import java.util.List;

/**
 * Service Interface for managing I18N.
 */
public interface I18NService {

    /**
     * Save a i18N.
     *
     * @param i18NDTO the entity to save
     * @return the persisted entity
     */
    I18NDTO save(I18NDTO i18NDTO);

    /**
     * Get all the i18NS.
     *
     * @return the list of entities
     */
    List<I18NDTO> findAll();

    /**
     * Get the "id" i18N.
     *
     * @param id the id of the entity
     * @return the entity
     */
    I18NDTO findOne(Long id);

    /**
     * Delete the "id" i18N.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
