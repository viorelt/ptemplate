package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.SubmitActionService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.SubmitActionDTO;
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
 * REST controller for managing SubmitAction.
 */
@RestController
@RequestMapping("/api")
public class SubmitActionResource {

    private final Logger log = LoggerFactory.getLogger(SubmitActionResource.class);

    private static final String ENTITY_NAME = "submitAction";

    private final SubmitActionService submitActionService;

    public SubmitActionResource(SubmitActionService submitActionService) {
        this.submitActionService = submitActionService;
    }

    /**
     * POST  /submit-actions : Create a new submitAction.
     *
     * @param submitActionDTO the submitActionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new submitActionDTO, or with status 400 (Bad Request) if the submitAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/submit-actions")
    @Timed
    public ResponseEntity<SubmitActionDTO> createSubmitAction(@RequestBody SubmitActionDTO submitActionDTO) throws URISyntaxException {
        log.debug("REST request to save SubmitAction : {}", submitActionDTO);
        if (submitActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new submitAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubmitActionDTO result = submitActionService.save(submitActionDTO);
        return ResponseEntity.created(new URI("/api/submit-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /submit-actions : Updates an existing submitAction.
     *
     * @param submitActionDTO the submitActionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated submitActionDTO,
     * or with status 400 (Bad Request) if the submitActionDTO is not valid,
     * or with status 500 (Internal Server Error) if the submitActionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/submit-actions")
    @Timed
    public ResponseEntity<SubmitActionDTO> updateSubmitAction(@RequestBody SubmitActionDTO submitActionDTO) throws URISyntaxException {
        log.debug("REST request to update SubmitAction : {}", submitActionDTO);
        if (submitActionDTO.getId() == null) {
            return createSubmitAction(submitActionDTO);
        }
        SubmitActionDTO result = submitActionService.save(submitActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, submitActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /submit-actions : get all the submitActions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of submitActions in body
     */
    @GetMapping("/submit-actions")
    @Timed
    public List<SubmitActionDTO> getAllSubmitActions() {
        log.debug("REST request to get all SubmitActions");
        return submitActionService.findAll();
        }

    /**
     * GET  /submit-actions/:id : get the "id" submitAction.
     *
     * @param id the id of the submitActionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the submitActionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/submit-actions/{id}")
    @Timed
    public ResponseEntity<SubmitActionDTO> getSubmitAction(@PathVariable Long id) {
        log.debug("REST request to get SubmitAction : {}", id);
        SubmitActionDTO submitActionDTO = submitActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(submitActionDTO));
    }

    /**
     * DELETE  /submit-actions/:id : delete the "id" submitAction.
     *
     * @param id the id of the submitActionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/submit-actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubmitAction(@PathVariable Long id) {
        log.debug("REST request to delete SubmitAction : {}", id);
        submitActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
