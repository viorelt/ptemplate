package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.EVisibilityService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.EVisibilityDTO;
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
 * REST controller for managing EVisibility.
 */
@RestController
@RequestMapping("/api")
public class EVisibilityResource {

    private final Logger log = LoggerFactory.getLogger(EVisibilityResource.class);

    private static final String ENTITY_NAME = "eVisibility";

    private final EVisibilityService eVisibilityService;

    public EVisibilityResource(EVisibilityService eVisibilityService) {
        this.eVisibilityService = eVisibilityService;
    }

    /**
     * POST  /e-visibilities : Create a new eVisibility.
     *
     * @param eVisibilityDTO the eVisibilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eVisibilityDTO, or with status 400 (Bad Request) if the eVisibility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-visibilities")
    @Timed
    public ResponseEntity<EVisibilityDTO> createEVisibility(@RequestBody EVisibilityDTO eVisibilityDTO) throws URISyntaxException {
        log.debug("REST request to save EVisibility : {}", eVisibilityDTO);
        if (eVisibilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new eVisibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EVisibilityDTO result = eVisibilityService.save(eVisibilityDTO);
        return ResponseEntity.created(new URI("/api/e-visibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-visibilities : Updates an existing eVisibility.
     *
     * @param eVisibilityDTO the eVisibilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eVisibilityDTO,
     * or with status 400 (Bad Request) if the eVisibilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the eVisibilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-visibilities")
    @Timed
    public ResponseEntity<EVisibilityDTO> updateEVisibility(@RequestBody EVisibilityDTO eVisibilityDTO) throws URISyntaxException {
        log.debug("REST request to update EVisibility : {}", eVisibilityDTO);
        if (eVisibilityDTO.getId() == null) {
            return createEVisibility(eVisibilityDTO);
        }
        EVisibilityDTO result = eVisibilityService.save(eVisibilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eVisibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-visibilities : get all the eVisibilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eVisibilities in body
     */
    @GetMapping("/e-visibilities")
    @Timed
    public List<EVisibilityDTO> getAllEVisibilities() {
        log.debug("REST request to get all EVisibilities");
        return eVisibilityService.findAll();
        }

    /**
     * GET  /e-visibilities/:id : get the "id" eVisibility.
     *
     * @param id the id of the eVisibilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eVisibilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/e-visibilities/{id}")
    @Timed
    public ResponseEntity<EVisibilityDTO> getEVisibility(@PathVariable Long id) {
        log.debug("REST request to get EVisibility : {}", id);
        EVisibilityDTO eVisibilityDTO = eVisibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eVisibilityDTO));
    }

    /**
     * DELETE  /e-visibilities/:id : delete the "id" eVisibility.
     *
     * @param id the id of the eVisibilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-visibilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteEVisibility(@PathVariable Long id) {
        log.debug("REST request to delete EVisibility : {}", id);
        eVisibilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
