package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.TBackendService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.TBackendDTO;
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
 * REST controller for managing TBackend.
 */
@RestController
@RequestMapping("/api")
public class TBackendResource {

    private final Logger log = LoggerFactory.getLogger(TBackendResource.class);

    private static final String ENTITY_NAME = "tBackend";

    private final TBackendService tBackendService;

    public TBackendResource(TBackendService tBackendService) {
        this.tBackendService = tBackendService;
    }

    /**
     * POST  /t-backends : Create a new tBackend.
     *
     * @param tBackendDTO the tBackendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBackendDTO, or with status 400 (Bad Request) if the tBackend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-backends")
    @Timed
    public ResponseEntity<TBackendDTO> createTBackend(@RequestBody TBackendDTO tBackendDTO) throws URISyntaxException {
        log.debug("REST request to save TBackend : {}", tBackendDTO);
        if (tBackendDTO.getId() != null) {
            throw new BadRequestAlertException("A new tBackend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBackendDTO result = tBackendService.save(tBackendDTO);
        return ResponseEntity.created(new URI("/api/t-backends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-backends : Updates an existing tBackend.
     *
     * @param tBackendDTO the tBackendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBackendDTO,
     * or with status 400 (Bad Request) if the tBackendDTO is not valid,
     * or with status 500 (Internal Server Error) if the tBackendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-backends")
    @Timed
    public ResponseEntity<TBackendDTO> updateTBackend(@RequestBody TBackendDTO tBackendDTO) throws URISyntaxException {
        log.debug("REST request to update TBackend : {}", tBackendDTO);
        if (tBackendDTO.getId() == null) {
            return createTBackend(tBackendDTO);
        }
        TBackendDTO result = tBackendService.save(tBackendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBackendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-backends : get all the tBackends.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tBackends in body
     */
    @GetMapping("/t-backends")
    @Timed
    public List<TBackendDTO> getAllTBackends() {
        log.debug("REST request to get all TBackends");
        return tBackendService.findAll();
        }

    /**
     * GET  /t-backends/:id : get the "id" tBackend.
     *
     * @param id the id of the tBackendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBackendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-backends/{id}")
    @Timed
    public ResponseEntity<TBackendDTO> getTBackend(@PathVariable Long id) {
        log.debug("REST request to get TBackend : {}", id);
        TBackendDTO tBackendDTO = tBackendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBackendDTO));
    }

    /**
     * DELETE  /t-backends/:id : delete the "id" tBackend.
     *
     * @param id the id of the tBackendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-backends/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBackend(@PathVariable Long id) {
        log.debug("REST request to delete TBackend : {}", id);
        tBackendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
