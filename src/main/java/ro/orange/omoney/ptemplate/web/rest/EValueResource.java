package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.EValueService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.EValueDTO;
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
 * REST controller for managing EValue.
 */
@RestController
@RequestMapping("/api")
public class EValueResource {

    private final Logger log = LoggerFactory.getLogger(EValueResource.class);

    private static final String ENTITY_NAME = "eValue";

    private final EValueService eValueService;

    public EValueResource(EValueService eValueService) {
        this.eValueService = eValueService;
    }

    /**
     * POST  /e-values : Create a new eValue.
     *
     * @param eValueDTO the eValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eValueDTO, or with status 400 (Bad Request) if the eValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-values")
    @Timed
    public ResponseEntity<EValueDTO> createEValue(@RequestBody EValueDTO eValueDTO) throws URISyntaxException {
        log.debug("REST request to save EValue : {}", eValueDTO);
        if (eValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new eValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EValueDTO result = eValueService.save(eValueDTO);
        return ResponseEntity.created(new URI("/api/e-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-values : Updates an existing eValue.
     *
     * @param eValueDTO the eValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eValueDTO,
     * or with status 400 (Bad Request) if the eValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the eValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-values")
    @Timed
    public ResponseEntity<EValueDTO> updateEValue(@RequestBody EValueDTO eValueDTO) throws URISyntaxException {
        log.debug("REST request to update EValue : {}", eValueDTO);
        if (eValueDTO.getId() == null) {
            return createEValue(eValueDTO);
        }
        EValueDTO result = eValueService.save(eValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-values : get all the eValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eValues in body
     */
    @GetMapping("/e-values")
    @Timed
    public List<EValueDTO> getAllEValues() {
        log.debug("REST request to get all EValues");
        return eValueService.findAll();
        }

    /**
     * GET  /e-values/:id : get the "id" eValue.
     *
     * @param id the id of the eValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/e-values/{id}")
    @Timed
    public ResponseEntity<EValueDTO> getEValue(@PathVariable Long id) {
        log.debug("REST request to get EValue : {}", id);
        EValueDTO eValueDTO = eValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eValueDTO));
    }

    /**
     * DELETE  /e-values/:id : delete the "id" eValue.
     *
     * @param id the id of the eValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteEValue(@PathVariable Long id) {
        log.debug("REST request to delete EValue : {}", id);
        eValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
