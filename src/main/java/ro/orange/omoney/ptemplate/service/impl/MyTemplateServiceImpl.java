package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.MyTemplateService;
import ro.orange.omoney.ptemplate.domain.MyTemplate;
import ro.orange.omoney.ptemplate.repository.MyTemplateRepository;
import ro.orange.omoney.ptemplate.service.dto.MyTemplateDTO;
import ro.orange.omoney.ptemplate.service.mapper.MyTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MyTemplate.
 */
@Service
@Transactional
public class MyTemplateServiceImpl implements MyTemplateService {

    private final Logger log = LoggerFactory.getLogger(MyTemplateServiceImpl.class);

    private final MyTemplateRepository myTemplateRepository;

    private final MyTemplateMapper myTemplateMapper;

    public MyTemplateServiceImpl(MyTemplateRepository myTemplateRepository, MyTemplateMapper myTemplateMapper) {
        this.myTemplateRepository = myTemplateRepository;
        this.myTemplateMapper = myTemplateMapper;
    }

    /**
     * Save a myTemplate.
     *
     * @param myTemplateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MyTemplateDTO save(MyTemplateDTO myTemplateDTO) {
        log.debug("Request to save MyTemplate : {}", myTemplateDTO);
        MyTemplate myTemplate = myTemplateMapper.toEntity(myTemplateDTO);
        myTemplate = myTemplateRepository.save(myTemplate);
        return myTemplateMapper.toDto(myTemplate);
    }

    /**
     * Get all the myTemplates.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MyTemplateDTO> findAll() {
        log.debug("Request to get all MyTemplates");
        return myTemplateRepository.findAll().stream()
            .map(myTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one myTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MyTemplateDTO findOne(Long id) {
        log.debug("Request to get MyTemplate : {}", id);
        MyTemplate myTemplate = myTemplateRepository.findOne(id);
        return myTemplateMapper.toDto(myTemplate);
    }

    /**
     * Delete the myTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyTemplate : {}", id);
        myTemplateRepository.delete(id);
    }
}
