package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.TUiService;
import ro.orange.omoney.ptemplate.domain.TUi;
import ro.orange.omoney.ptemplate.repository.TUiRepository;
import ro.orange.omoney.ptemplate.service.dto.TUiDTO;
import ro.orange.omoney.ptemplate.service.mapper.TUiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TUi.
 */
@Service
@Transactional
public class TUiServiceImpl implements TUiService {

    private final Logger log = LoggerFactory.getLogger(TUiServiceImpl.class);

    private final TUiRepository tUiRepository;

    private final TUiMapper tUiMapper;

    public TUiServiceImpl(TUiRepository tUiRepository, TUiMapper tUiMapper) {
        this.tUiRepository = tUiRepository;
        this.tUiMapper = tUiMapper;
    }

    /**
     * Save a tUi.
     *
     * @param tUiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TUiDTO save(TUiDTO tUiDTO) {
        log.debug("Request to save TUi : {}", tUiDTO);
        TUi tUi = tUiMapper.toEntity(tUiDTO);
        tUi = tUiRepository.save(tUi);
        return tUiMapper.toDto(tUi);
    }

    /**
     * Get all the tUis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TUiDTO> findAll() {
        log.debug("Request to get all TUis");
        return tUiRepository.findAll().stream()
            .map(tUiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tUi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TUiDTO findOne(Long id) {
        log.debug("Request to get TUi : {}", id);
        TUi tUi = tUiRepository.findOne(id);
        return tUiMapper.toDto(tUi);
    }

    /**
     * Delete the tUi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TUi : {}", id);
        tUiRepository.delete(id);
    }
}
