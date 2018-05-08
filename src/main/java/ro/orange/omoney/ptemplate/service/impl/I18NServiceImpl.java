package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.I18NService;
import ro.orange.omoney.ptemplate.domain.I18N;
import ro.orange.omoney.ptemplate.repository.I18NRepository;
import ro.orange.omoney.ptemplate.service.dto.I18NDTO;
import ro.orange.omoney.ptemplate.service.mapper.I18NMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing I18N.
 */
@Service
@Transactional
public class I18NServiceImpl implements I18NService {

    private final Logger log = LoggerFactory.getLogger(I18NServiceImpl.class);

    private final I18NRepository i18NRepository;

    private final I18NMapper i18NMapper;

    public I18NServiceImpl(I18NRepository i18NRepository, I18NMapper i18NMapper) {
        this.i18NRepository = i18NRepository;
        this.i18NMapper = i18NMapper;
    }

    /**
     * Save a i18N.
     *
     * @param i18NDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public I18NDTO save(I18NDTO i18NDTO) {
        log.debug("Request to save I18N : {}", i18NDTO);
        I18N i18N = i18NMapper.toEntity(i18NDTO);
        i18N = i18NRepository.save(i18N);
        return i18NMapper.toDto(i18N);
    }

    /**
     * Get all the i18NS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<I18NDTO> findAll() {
        log.debug("Request to get all I18NS");
        return i18NRepository.findAll().stream()
            .map(i18NMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one i18N by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public I18NDTO findOne(Long id) {
        log.debug("Request to get I18N : {}", id);
        I18N i18N = i18NRepository.findOne(id);
        return i18NMapper.toDto(i18N);
    }

    /**
     * Delete the i18N by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete I18N : {}", id);
        i18NRepository.delete(id);
    }
}
