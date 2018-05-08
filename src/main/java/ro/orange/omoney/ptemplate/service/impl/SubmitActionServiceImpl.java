package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.SubmitActionService;
import ro.orange.omoney.ptemplate.domain.SubmitAction;
import ro.orange.omoney.ptemplate.repository.SubmitActionRepository;
import ro.orange.omoney.ptemplate.service.dto.SubmitActionDTO;
import ro.orange.omoney.ptemplate.service.mapper.SubmitActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SubmitAction.
 */
@Service
@Transactional
public class SubmitActionServiceImpl implements SubmitActionService {

    private final Logger log = LoggerFactory.getLogger(SubmitActionServiceImpl.class);

    private final SubmitActionRepository submitActionRepository;

    private final SubmitActionMapper submitActionMapper;

    public SubmitActionServiceImpl(SubmitActionRepository submitActionRepository, SubmitActionMapper submitActionMapper) {
        this.submitActionRepository = submitActionRepository;
        this.submitActionMapper = submitActionMapper;
    }

    /**
     * Save a submitAction.
     *
     * @param submitActionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SubmitActionDTO save(SubmitActionDTO submitActionDTO) {
        log.debug("Request to save SubmitAction : {}", submitActionDTO);
        SubmitAction submitAction = submitActionMapper.toEntity(submitActionDTO);
        submitAction = submitActionRepository.save(submitAction);
        return submitActionMapper.toDto(submitAction);
    }

    /**
     * Get all the submitActions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubmitActionDTO> findAll() {
        log.debug("Request to get all SubmitActions");
        return submitActionRepository.findAll().stream()
            .map(submitActionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one submitAction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SubmitActionDTO findOne(Long id) {
        log.debug("Request to get SubmitAction : {}", id);
        SubmitAction submitAction = submitActionRepository.findOne(id);
        return submitActionMapper.toDto(submitAction);
    }

    /**
     * Delete the submitAction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubmitAction : {}", id);
        submitActionRepository.delete(id);
    }
}
