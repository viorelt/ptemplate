package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.EUiService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.EUiDTO;
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
 * REST controller for managing EUi.
 */
@RestController
@RequestMapping("/api")
public class EUiResource {

    private final Logger log = LoggerFactory.getLogger(EUiResource.class);

    private static final String ENTITY_NAME = "eUi";

    private final EUiService eUiService;

    public EUiResource(EUiService eUiService) {
        this.eUiService = eUiService;
    }

    /**
     * POST  /e-uis : Create a new eUi.
     *
     * @param eUiDTO the eUiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eUiDTO, or with status 400 (Bad Request) if the eUi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-uis")
    @Timed
    public ResponseEntity<EUiDTO> createEUi(@RequestBody EUiDTO eUiDTO) throws URISyntaxException {
        log.debug("REST request to save EUi : {}", eUiDTO);
        if (eUiDTO.getId() != null) {
            throw new BadRequestAlertException("A new eUi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EUiDTO result = eUiService.save(eUiDTO);
        return ResponseEntity.created(new URI("/api/e-uis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-uis : Updates an existing eUi.
     *
     * @param eUiDTO the eUiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eUiDTO,
     * or with status 400 (Bad Request) if the eUiDTO is not valid,
     * or with status 500 (Internal Server Error) if the eUiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-uis")
    @Timed
    public ResponseEntity<EUiDTO> updateEUi(@RequestBody EUiDTO eUiDTO) throws URISyntaxException {
        log.debug("REST request to update EUi : {}", eUiDTO);
        if (eUiDTO.getId() == null) {
            return createEUi(eUiDTO);
        }
        EUiDTO result = eUiService.save(eUiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eUiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-uis : get all the eUis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eUis in body
     */
    @GetMapping("/e-uis")
    @Timed
    public List<EUiDTO> getAllEUis() {
        log.debug("REST request to get all EUis");
        return eUiService.findAll();
        }

    /**
     * GET  /e-uis/:id : get the "id" eUi.
     *
     * @param id the id of the eUiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eUiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/e-uis/{id}")
    @Timed
    public ResponseEntity<EUiDTO> getEUi(@PathVariable Long id) {
        log.debug("REST request to get EUi : {}", id);
        EUiDTO eUiDTO = eUiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eUiDTO));
    }

    /**
     * DELETE  /e-uis/:id : delete the "id" eUi.
     *
     * @param id the id of the eUiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-uis/{id}")
    @Timed
    public ResponseEntity<Void> deleteEUi(@PathVariable Long id) {
        log.debug("REST request to delete EUi : {}", id);
        eUiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
