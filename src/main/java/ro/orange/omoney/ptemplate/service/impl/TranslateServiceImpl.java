package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.TranslateService;
import ro.orange.omoney.ptemplate.domain.Translate;
import ro.orange.omoney.ptemplate.repository.TranslateRepository;
import ro.orange.omoney.ptemplate.service.dto.TranslateDTO;
import ro.orange.omoney.ptemplate.service.mapper.TranslateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Translate.
 */
@Service
@Transactional
public class TranslateServiceImpl implements TranslateService {

    private final Logger log = LoggerFactory.getLogger(TranslateServiceImpl.class);

    private final TranslateRepository translateRepository;

    private final TranslateMapper translateMapper;

    public TranslateServiceImpl(TranslateRepository translateRepository, TranslateMapper translateMapper) {
        this.translateRepository = translateRepository;
        this.translateMapper = translateMapper;
    }

    /**
     * Save a translate.
     *
     * @param translateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TranslateDTO save(TranslateDTO translateDTO) {
        log.debug("Request to save Translate : {}", translateDTO);
        Translate translate = translateMapper.toEntity(translateDTO);
        translate = translateRepository.save(translate);
        return translateMapper.toDto(translate);
    }

    /**
     * Get all the translates.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TranslateDTO> findAll() {
        log.debug("Request to get all Translates");
        return translateRepository.findAll().stream()
            .map(translateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one translate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TranslateDTO findOne(Long id) {
        log.debug("Request to get Translate : {}", id);
        Translate translate = translateRepository.findOne(id);
        return translateMapper.toDto(translate);
    }

    /**
     * Delete the translate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Translate : {}", id);
        translateRepository.delete(id);
    }
}
