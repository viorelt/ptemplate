package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.EVisibilityService;
import ro.orange.omoney.ptemplate.domain.EVisibility;
import ro.orange.omoney.ptemplate.repository.EVisibilityRepository;
import ro.orange.omoney.ptemplate.service.dto.EVisibilityDTO;
import ro.orange.omoney.ptemplate.service.mapper.EVisibilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EVisibility.
 */
@Service
@Transactional
public class EVisibilityServiceImpl implements EVisibilityService {

    private final Logger log = LoggerFactory.getLogger(EVisibilityServiceImpl.class);

    private final EVisibilityRepository eVisibilityRepository;

    private final EVisibilityMapper eVisibilityMapper;

    public EVisibilityServiceImpl(EVisibilityRepository eVisibilityRepository, EVisibilityMapper eVisibilityMapper) {
        this.eVisibilityRepository = eVisibilityRepository;
        this.eVisibilityMapper = eVisibilityMapper;
    }

    /**
     * Save a eVisibility.
     *
     * @param eVisibilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EVisibilityDTO save(EVisibilityDTO eVisibilityDTO) {
        log.debug("Request to save EVisibility : {}", eVisibilityDTO);
        EVisibility eVisibility = eVisibilityMapper.toEntity(eVisibilityDTO);
        eVisibility = eVisibilityRepository.save(eVisibility);
        return eVisibilityMapper.toDto(eVisibility);
    }

    /**
     * Get all the eVisibilities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EVisibilityDTO> findAll() {
        log.debug("Request to get all EVisibilities");
        return eVisibilityRepository.findAll().stream()
            .map(eVisibilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eVisibility by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EVisibilityDTO findOne(Long id) {
        log.debug("Request to get EVisibility : {}", id);
        EVisibility eVisibility = eVisibilityRepository.findOne(id);
        return eVisibilityMapper.toDto(eVisibility);
    }

    /**
     * Delete the eVisibility by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EVisibility : {}", id);
        eVisibilityRepository.delete(id);
    }
}
