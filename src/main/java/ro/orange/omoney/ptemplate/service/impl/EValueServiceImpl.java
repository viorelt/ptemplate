package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.EValueService;
import ro.orange.omoney.ptemplate.domain.EValue;
import ro.orange.omoney.ptemplate.repository.EValueRepository;
import ro.orange.omoney.ptemplate.service.dto.EValueDTO;
import ro.orange.omoney.ptemplate.service.mapper.EValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EValue.
 */
@Service
@Transactional
public class EValueServiceImpl implements EValueService {

    private final Logger log = LoggerFactory.getLogger(EValueServiceImpl.class);

    private final EValueRepository eValueRepository;

    private final EValueMapper eValueMapper;

    public EValueServiceImpl(EValueRepository eValueRepository, EValueMapper eValueMapper) {
        this.eValueRepository = eValueRepository;
        this.eValueMapper = eValueMapper;
    }

    /**
     * Save a eValue.
     *
     * @param eValueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EValueDTO save(EValueDTO eValueDTO) {
        log.debug("Request to save EValue : {}", eValueDTO);
        EValue eValue = eValueMapper.toEntity(eValueDTO);
        eValue = eValueRepository.save(eValue);
        return eValueMapper.toDto(eValue);
    }

    /**
     * Get all the eValues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EValueDTO> findAll() {
        log.debug("Request to get all EValues");
        return eValueRepository.findAll().stream()
            .map(eValueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EValueDTO findOne(Long id) {
        log.debug("Request to get EValue : {}", id);
        EValue eValue = eValueRepository.findOne(id);
        return eValueMapper.toDto(eValue);
    }

    /**
     * Delete the eValue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EValue : {}", id);
        eValueRepository.delete(id);
    }
}
