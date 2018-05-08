package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.ElementService;
import ro.orange.omoney.ptemplate.domain.Element;
import ro.orange.omoney.ptemplate.repository.ElementRepository;
import ro.orange.omoney.ptemplate.service.dto.ElementDTO;
import ro.orange.omoney.ptemplate.service.mapper.ElementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Element.
 */
@Service
@Transactional
public class ElementServiceImpl implements ElementService {

    private final Logger log = LoggerFactory.getLogger(ElementServiceImpl.class);

    private final ElementRepository elementRepository;

    private final ElementMapper elementMapper;

    public ElementServiceImpl(ElementRepository elementRepository, ElementMapper elementMapper) {
        this.elementRepository = elementRepository;
        this.elementMapper = elementMapper;
    }

    /**
     * Save a element.
     *
     * @param elementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ElementDTO save(ElementDTO elementDTO) {
        log.debug("Request to save Element : {}", elementDTO);
        Element element = elementMapper.toEntity(elementDTO);
        element = elementRepository.save(element);
        return elementMapper.toDto(element);
    }

    /**
     * Get all the elements.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElementDTO> findAll() {
        log.debug("Request to get all Elements");
        return elementRepository.findAll().stream()
            .map(elementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one element by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ElementDTO findOne(Long id) {
        log.debug("Request to get Element : {}", id);
        Element element = elementRepository.findOne(id);
        return elementMapper.toDto(element);
    }

    /**
     * Delete the element by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Element : {}", id);
        elementRepository.delete(id);
    }
}
