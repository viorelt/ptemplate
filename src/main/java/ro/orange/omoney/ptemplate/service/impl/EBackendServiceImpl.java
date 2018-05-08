package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.EBackendService;
import ro.orange.omoney.ptemplate.domain.EBackend;
import ro.orange.omoney.ptemplate.repository.EBackendRepository;
import ro.orange.omoney.ptemplate.service.dto.EBackendDTO;
import ro.orange.omoney.ptemplate.service.mapper.EBackendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EBackend.
 */
@Service
@Transactional
public class EBackendServiceImpl implements EBackendService {

    private final Logger log = LoggerFactory.getLogger(EBackendServiceImpl.class);

    private final EBackendRepository eBackendRepository;

    private final EBackendMapper eBackendMapper;

    public EBackendServiceImpl(EBackendRepository eBackendRepository, EBackendMapper eBackendMapper) {
        this.eBackendRepository = eBackendRepository;
        this.eBackendMapper = eBackendMapper;
    }

    /**
     * Save a eBackend.
     *
     * @param eBackendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EBackendDTO save(EBackendDTO eBackendDTO) {
        log.debug("Request to save EBackend : {}", eBackendDTO);
        EBackend eBackend = eBackendMapper.toEntity(eBackendDTO);
        eBackend = eBackendRepository.save(eBackend);
        return eBackendMapper.toDto(eBackend);
    }

    /**
     * Get all the eBackends.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EBackendDTO> findAll() {
        log.debug("Request to get all EBackends");
        return eBackendRepository.findAll().stream()
            .map(eBackendMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eBackend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EBackendDTO findOne(Long id) {
        log.debug("Request to get EBackend : {}", id);
        EBackend eBackend = eBackendRepository.findOne(id);
        return eBackendMapper.toDto(eBackend);
    }

    /**
     * Delete the eBackend by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EBackend : {}", id);
        eBackendRepository.delete(id);
    }
}
