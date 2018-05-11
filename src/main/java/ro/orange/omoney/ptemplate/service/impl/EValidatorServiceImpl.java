package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.EValidatorService;
import ro.orange.omoney.ptemplate.domain.EValidator;
import ro.orange.omoney.ptemplate.repository.EValidatorRepository;
import ro.orange.omoney.ptemplate.service.dto.EValidatorDTO;
import ro.orange.omoney.ptemplate.service.mapper.EValidatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EValidator.
 */
@Service
@Transactional
public class EValidatorServiceImpl implements EValidatorService {

    private final Logger log = LoggerFactory.getLogger(EValidatorServiceImpl.class);

    private final EValidatorRepository eValidatorRepository;

    private final EValidatorMapper eValidatorMapper;

    public EValidatorServiceImpl(EValidatorRepository eValidatorRepository, EValidatorMapper eValidatorMapper) {
        this.eValidatorRepository = eValidatorRepository;
        this.eValidatorMapper = eValidatorMapper;
    }

    /**
     * Save a eValidator.
     *
     * @param eValidatorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EValidatorDTO save(EValidatorDTO eValidatorDTO) {
        log.debug("Request to save EValidator : {}", eValidatorDTO);
        EValidator eValidator = eValidatorMapper.toEntity(eValidatorDTO);
        eValidator = eValidatorRepository.save(eValidator);
        return eValidatorMapper.toDto(eValidator);
    }

    /**
     * Get all the eValidators.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EValidatorDTO> findAll() {
        log.debug("Request to get all EValidators");
        return eValidatorRepository.findAll().stream()
            .map(eValidatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eValidator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EValidatorDTO findOne(Long id) {
        log.debug("Request to get EValidator : {}", id);
        EValidator eValidator = eValidatorRepository.findOne(id);
        return eValidatorMapper.toDto(eValidator);
    }

    /**
     * Delete the eValidator by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EValidator : {}", id);
        eValidatorRepository.delete(id);
    }
}
