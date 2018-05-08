package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.ValueOptionService;
import ro.orange.omoney.ptemplate.domain.ValueOption;
import ro.orange.omoney.ptemplate.repository.ValueOptionRepository;
import ro.orange.omoney.ptemplate.service.dto.ValueOptionDTO;
import ro.orange.omoney.ptemplate.service.mapper.ValueOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ValueOption.
 */
@Service
@Transactional
public class ValueOptionServiceImpl implements ValueOptionService {

    private final Logger log = LoggerFactory.getLogger(ValueOptionServiceImpl.class);

    private final ValueOptionRepository valueOptionRepository;

    private final ValueOptionMapper valueOptionMapper;

    public ValueOptionServiceImpl(ValueOptionRepository valueOptionRepository, ValueOptionMapper valueOptionMapper) {
        this.valueOptionRepository = valueOptionRepository;
        this.valueOptionMapper = valueOptionMapper;
    }

    /**
     * Save a valueOption.
     *
     * @param valueOptionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ValueOptionDTO save(ValueOptionDTO valueOptionDTO) {
        log.debug("Request to save ValueOption : {}", valueOptionDTO);
        ValueOption valueOption = valueOptionMapper.toEntity(valueOptionDTO);
        valueOption = valueOptionRepository.save(valueOption);
        return valueOptionMapper.toDto(valueOption);
    }

    /**
     * Get all the valueOptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValueOptionDTO> findAll() {
        log.debug("Request to get all ValueOptions");
        return valueOptionRepository.findAll().stream()
            .map(valueOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one valueOption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValueOptionDTO findOne(Long id) {
        log.debug("Request to get ValueOption : {}", id);
        ValueOption valueOption = valueOptionRepository.findOne(id);
        return valueOptionMapper.toDto(valueOption);
    }

    /**
     * Delete the valueOption by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ValueOption : {}", id);
        valueOptionRepository.delete(id);
    }
}
