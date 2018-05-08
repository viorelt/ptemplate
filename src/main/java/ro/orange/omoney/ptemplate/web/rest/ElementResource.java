package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.ElementService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.ElementDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Element.
 */
@RestController
@RequestMapping("/api")
public class ElementResource {

    private final Logger log = LoggerFactory.getLogger(ElementResource.class);

    private static final String ENTITY_NAME = "element";

    private final ElementService elementService;

    public ElementResource(ElementService elementService) {
        this.elementService = elementService;
    }

    /**
     * POST  /elements : Create a new element.
     *
     * @param elementDTO the elementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elementDTO, or with status 400 (Bad Request) if the element has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elements")
    @Timed
    public ResponseEntity<ElementDTO> createElement(@RequestBody ElementDTO elementDTO) throws URISyntaxException {
        log.debug("REST request to save Element : {}", elementDTO);
        if (elementDTO.getId() != null) {
            throw new BadRequestAlertException("A new element cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementDTO result = elementService.save(elementDTO);
        return ResponseEntity.created(new URI("/api/elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elements : Updates an existing element.
     *
     * @param elementDTO the elementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elementDTO,
     * or with status 400 (Bad Request) if the elementDTO is not valid,
     * or with status 500 (Internal Server Error) if the elementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elements")
    @Timed
    public ResponseEntity<ElementDTO> updateElement(@RequestBody ElementDTO elementDTO) throws URISyntaxException {
        log.debug("REST request to update Element : {}", elementDTO);
        if (elementDTO.getId() == null) {
            return createElement(elementDTO);
        }
        ElementDTO result = elementService.save(elementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elements : get all the elements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elements in body
     */
    @GetMapping("/elements")
    @Timed
    public List<ElementDTO> getAllElements() {
        log.debug("REST request to get all Elements");
        return elementService.findAll();
        }

    /**
     * GET  /elements/:id : get the "id" element.
     *
     * @param id the id of the elementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/elements/{id}")
    @Timed
    public ResponseEntity<ElementDTO> getElement(@PathVariable Long id) {
        log.debug("REST request to get Element : {}", id);
        ElementDTO elementDTO = elementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(elementDTO));
    }

    /**
     * DELETE  /elements/:id : delete the "id" element.
     *
     * @param id the id of the elementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elements/{id}")
    @Timed
    public ResponseEntity<Void> deleteElement(@PathVariable Long id) {
        log.debug("REST request to delete Element : {}", id);
        elementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
