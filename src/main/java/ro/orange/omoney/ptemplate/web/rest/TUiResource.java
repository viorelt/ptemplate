package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.TUiService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.TUiDTO;
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
 * REST controller for managing TUi.
 */
@RestController
@RequestMapping("/api")
public class TUiResource {

    private final Logger log = LoggerFactory.getLogger(TUiResource.class);

    private static final String ENTITY_NAME = "tUi";

    private final TUiService tUiService;

    public TUiResource(TUiService tUiService) {
        this.tUiService = tUiService;
    }

    /**
     * POST  /t-uis : Create a new tUi.
     *
     * @param tUiDTO the tUiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tUiDTO, or with status 400 (Bad Request) if the tUi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-uis")
    @Timed
    public ResponseEntity<TUiDTO> createTUi(@RequestBody TUiDTO tUiDTO) throws URISyntaxException {
        log.debug("REST request to save TUi : {}", tUiDTO);
        if (tUiDTO.getId() != null) {
            throw new BadRequestAlertException("A new tUi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TUiDTO result = tUiService.save(tUiDTO);
        return ResponseEntity.created(new URI("/api/t-uis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-uis : Updates an existing tUi.
     *
     * @param tUiDTO the tUiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tUiDTO,
     * or with status 400 (Bad Request) if the tUiDTO is not valid,
     * or with status 500 (Internal Server Error) if the tUiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-uis")
    @Timed
    public ResponseEntity<TUiDTO> updateTUi(@RequestBody TUiDTO tUiDTO) throws URISyntaxException {
        log.debug("REST request to update TUi : {}", tUiDTO);
        if (tUiDTO.getId() == null) {
            return createTUi(tUiDTO);
        }
        TUiDTO result = tUiService.save(tUiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tUiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-uis : get all the tUis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tUis in body
     */
    @GetMapping("/t-uis")
    @Timed
    public List<TUiDTO> getAllTUis() {
        log.debug("REST request to get all TUis");
        return tUiService.findAll();
        }

    /**
     * GET  /t-uis/:id : get the "id" tUi.
     *
     * @param id the id of the tUiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tUiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-uis/{id}")
    @Timed
    public ResponseEntity<TUiDTO> getTUi(@PathVariable Long id) {
        log.debug("REST request to get TUi : {}", id);
        TUiDTO tUiDTO = tUiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tUiDTO));
    }

    /**
     * DELETE  /t-uis/:id : delete the "id" tUi.
     *
     * @param id the id of the tUiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-uis/{id}")
    @Timed
    public ResponseEntity<Void> deleteTUi(@PathVariable Long id) {
        log.debug("REST request to delete TUi : {}", id);
        tUiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
