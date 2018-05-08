package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.ValueOptionService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.ValueOptionDTO;
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
 * REST controller for managing ValueOption.
 */
@RestController
@RequestMapping("/api")
public class ValueOptionResource {

    private final Logger log = LoggerFactory.getLogger(ValueOptionResource.class);

    private static final String ENTITY_NAME = "valueOption";

    private final ValueOptionService valueOptionService;

    public ValueOptionResource(ValueOptionService valueOptionService) {
        this.valueOptionService = valueOptionService;
    }

    /**
     * POST  /value-options : Create a new valueOption.
     *
     * @param valueOptionDTO the valueOptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valueOptionDTO, or with status 400 (Bad Request) if the valueOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/value-options")
    @Timed
    public ResponseEntity<ValueOptionDTO> createValueOption(@RequestBody ValueOptionDTO valueOptionDTO) throws URISyntaxException {
        log.debug("REST request to save ValueOption : {}", valueOptionDTO);
        if (valueOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new valueOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValueOptionDTO result = valueOptionService.save(valueOptionDTO);
        return ResponseEntity.created(new URI("/api/value-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /value-options : Updates an existing valueOption.
     *
     * @param valueOptionDTO the valueOptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valueOptionDTO,
     * or with status 400 (Bad Request) if the valueOptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the valueOptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/value-options")
    @Timed
    public ResponseEntity<ValueOptionDTO> updateValueOption(@RequestBody ValueOptionDTO valueOptionDTO) throws URISyntaxException {
        log.debug("REST request to update ValueOption : {}", valueOptionDTO);
        if (valueOptionDTO.getId() == null) {
            return createValueOption(valueOptionDTO);
        }
        ValueOptionDTO result = valueOptionService.save(valueOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valueOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /value-options : get all the valueOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valueOptions in body
     */
    @GetMapping("/value-options")
    @Timed
    public List<ValueOptionDTO> getAllValueOptions() {
        log.debug("REST request to get all ValueOptions");
        return valueOptionService.findAll();
        }

    /**
     * GET  /value-options/:id : get the "id" valueOption.
     *
     * @param id the id of the valueOptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valueOptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/value-options/{id}")
    @Timed
    public ResponseEntity<ValueOptionDTO> getValueOption(@PathVariable Long id) {
        log.debug("REST request to get ValueOption : {}", id);
        ValueOptionDTO valueOptionDTO = valueOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valueOptionDTO));
    }

    /**
     * DELETE  /value-options/:id : delete the "id" valueOption.
     *
     * @param id the id of the valueOptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/value-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteValueOption(@PathVariable Long id) {
        log.debug("REST request to delete ValueOption : {}", id);
        valueOptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
