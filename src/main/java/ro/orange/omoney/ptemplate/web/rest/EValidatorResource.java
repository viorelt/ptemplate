package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.EValidatorService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.EValidatorDTO;
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
 * REST controller for managing EValidator.
 */
@RestController
@RequestMapping("/api")
public class EValidatorResource {

    private final Logger log = LoggerFactory.getLogger(EValidatorResource.class);

    private static final String ENTITY_NAME = "eValidator";

    private final EValidatorService eValidatorService;

    public EValidatorResource(EValidatorService eValidatorService) {
        this.eValidatorService = eValidatorService;
    }

    /**
     * POST  /e-validators : Create a new eValidator.
     *
     * @param eValidatorDTO the eValidatorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eValidatorDTO, or with status 400 (Bad Request) if the eValidator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-validators")
    @Timed
    public ResponseEntity<EValidatorDTO> createEValidator(@RequestBody EValidatorDTO eValidatorDTO) throws URISyntaxException {
        log.debug("REST request to save EValidator : {}", eValidatorDTO);
        if (eValidatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new eValidator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EValidatorDTO result = eValidatorService.save(eValidatorDTO);
        return ResponseEntity.created(new URI("/api/e-validators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-validators : Updates an existing eValidator.
     *
     * @param eValidatorDTO the eValidatorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eValidatorDTO,
     * or with status 400 (Bad Request) if the eValidatorDTO is not valid,
     * or with status 500 (Internal Server Error) if the eValidatorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-validators")
    @Timed
    public ResponseEntity<EValidatorDTO> updateEValidator(@RequestBody EValidatorDTO eValidatorDTO) throws URISyntaxException {
        log.debug("REST request to update EValidator : {}", eValidatorDTO);
        if (eValidatorDTO.getId() == null) {
            return createEValidator(eValidatorDTO);
        }
        EValidatorDTO result = eValidatorService.save(eValidatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eValidatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-validators : get all the eValidators.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eValidators in body
     */
    @GetMapping("/e-validators")
    @Timed
    public List<EValidatorDTO> getAllEValidators() {
        log.debug("REST request to get all EValidators");
        return eValidatorService.findAll();
        }

    /**
     * GET  /e-validators/:id : get the "id" eValidator.
     *
     * @param id the id of the eValidatorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eValidatorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/e-validators/{id}")
    @Timed
    public ResponseEntity<EValidatorDTO> getEValidator(@PathVariable Long id) {
        log.debug("REST request to get EValidator : {}", id);
        EValidatorDTO eValidatorDTO = eValidatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eValidatorDTO));
    }

    /**
     * DELETE  /e-validators/:id : delete the "id" eValidator.
     *
     * @param id the id of the eValidatorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-validators/{id}")
    @Timed
    public ResponseEntity<Void> deleteEValidator(@PathVariable Long id) {
        log.debug("REST request to delete EValidator : {}", id);
        eValidatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
