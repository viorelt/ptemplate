package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.TBackendService;
import ro.orange.omoney.ptemplate.domain.TBackend;
import ro.orange.omoney.ptemplate.repository.TBackendRepository;
import ro.orange.omoney.ptemplate.service.dto.TBackendDTO;
import ro.orange.omoney.ptemplate.service.mapper.TBackendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TBackend.
 */
@Service
@Transactional
public class TBackendServiceImpl implements TBackendService {

    private final Logger log = LoggerFactory.getLogger(TBackendServiceImpl.class);

    private final TBackendRepository tBackendRepository;

    private final TBackendMapper tBackendMapper;

    public TBackendServiceImpl(TBackendRepository tBackendRepository, TBackendMapper tBackendMapper) {
        this.tBackendRepository = tBackendRepository;
        this.tBackendMapper = tBackendMapper;
    }

    /**
     * Save a tBackend.
     *
     * @param tBackendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TBackendDTO save(TBackendDTO tBackendDTO) {
        log.debug("Request to save TBackend : {}", tBackendDTO);
        TBackend tBackend = tBackendMapper.toEntity(tBackendDTO);
        tBackend = tBackendRepository.save(tBackend);
        return tBackendMapper.toDto(tBackend);
    }

    /**
     * Get all the tBackends.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TBackendDTO> findAll() {
        log.debug("Request to get all TBackends");
        return tBackendRepository.findAll().stream()
            .map(tBackendMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tBackend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TBackendDTO findOne(Long id) {
        log.debug("Request to get TBackend : {}", id);
        TBackend tBackend = tBackendRepository.findOne(id);
        return tBackendMapper.toDto(tBackend);
    }

    /**
     * Delete the tBackend by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TBackend : {}", id);
        tBackendRepository.delete(id);
    }
}
