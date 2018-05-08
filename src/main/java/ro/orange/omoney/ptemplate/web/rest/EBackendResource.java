package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.EBackendService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.EBackendDTO;
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
 * REST controller for managing EBackend.
 */
@RestController
@RequestMapping("/api")
public class EBackendResource {

    private final Logger log = LoggerFactory.getLogger(EBackendResource.class);

    private static final String ENTITY_NAME = "eBackend";

    private final EBackendService eBackendService;

    public EBackendResource(EBackendService eBackendService) {
        this.eBackendService = eBackendService;
    }

    /**
     * POST  /e-backends : Create a new eBackend.
     *
     * @param eBackendDTO the eBackendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eBackendDTO, or with status 400 (Bad Request) if the eBackend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-backends")
    @Timed
    public ResponseEntity<EBackendDTO> createEBackend(@RequestBody EBackendDTO eBackendDTO) throws URISyntaxException {
        log.debug("REST request to save EBackend : {}", eBackendDTO);
        if (eBackendDTO.getId() != null) {
            throw new BadRequestAlertException("A new eBackend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EBackendDTO result = eBackendService.save(eBackendDTO);
        return ResponseEntity.created(new URI("/api/e-backends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-backends : Updates an existing eBackend.
     *
     * @param eBackendDTO the eBackendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eBackendDTO,
     * or with status 400 (Bad Request) if the eBackendDTO is not valid,
     * or with status 500 (Internal Server Error) if the eBackendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-backends")
    @Timed
    public ResponseEntity<EBackendDTO> updateEBackend(@RequestBody EBackendDTO eBackendDTO) throws URISyntaxException {
        log.debug("REST request to update EBackend : {}", eBackendDTO);
        if (eBackendDTO.getId() == null) {
            return createEBackend(eBackendDTO);
        }
        EBackendDTO result = eBackendService.save(eBackendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eBackendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-backends : get all the eBackends.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eBackends in body
     */
    @GetMapping("/e-backends")
    @Timed
    public List<EBackendDTO> getAllEBackends() {
        log.debug("REST request to get all EBackends");
        return eBackendService.findAll();
        }

    /**
     * GET  /e-backends/:id : get the "id" eBackend.
     *
     * @param id the id of the eBackendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eBackendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/e-backends/{id}")
    @Timed
    public ResponseEntity<EBackendDTO> getEBackend(@PathVariable Long id) {
        log.debug("REST request to get EBackend : {}", id);
        EBackendDTO eBackendDTO = eBackendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eBackendDTO));
    }

    /**
     * DELETE  /e-backends/:id : delete the "id" eBackend.
     *
     * @param id the id of the eBackendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-backends/{id}")
    @Timed
    public ResponseEntity<Void> deleteEBackend(@PathVariable Long id) {
        log.debug("REST request to delete EBackend : {}", id);
        eBackendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
