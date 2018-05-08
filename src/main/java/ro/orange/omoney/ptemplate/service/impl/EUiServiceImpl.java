package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.EUiService;
import ro.orange.omoney.ptemplate.domain.EUi;
import ro.orange.omoney.ptemplate.repository.EUiRepository;
import ro.orange.omoney.ptemplate.service.dto.EUiDTO;
import ro.orange.omoney.ptemplate.service.mapper.EUiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EUi.
 */
@Service
@Transactional
public class EUiServiceImpl implements EUiService {

    private final Logger log = LoggerFactory.getLogger(EUiServiceImpl.class);

    private final EUiRepository eUiRepository;

    private final EUiMapper eUiMapper;

    public EUiServiceImpl(EUiRepository eUiRepository, EUiMapper eUiMapper) {
        this.eUiRepository = eUiRepository;
        this.eUiMapper = eUiMapper;
    }

    /**
     * Save a eUi.
     *
     * @param eUiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EUiDTO save(EUiDTO eUiDTO) {
        log.debug("Request to save EUi : {}", eUiDTO);
        EUi eUi = eUiMapper.toEntity(eUiDTO);
        eUi = eUiRepository.save(eUi);
        return eUiMapper.toDto(eUi);
    }

    /**
     * Get all the eUis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EUiDTO> findAll() {
        log.debug("Request to get all EUis");
        return eUiRepository.findAll().stream()
            .map(eUiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eUi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EUiDTO findOne(Long id) {
        log.debug("Request to get EUi : {}", id);
        EUi eUi = eUiRepository.findOne(id);
        return eUiMapper.toDto(eUi);
    }

    /**
     * Delete the eUi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EUi : {}", id);
        eUiRepository.delete(id);
    }
}
