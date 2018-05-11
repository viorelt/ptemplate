package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.TranslateService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.TranslateDTO;
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
 * REST controller for managing Translate.
 */
@RestController
@RequestMapping("/api")
public class TranslateResource {

    private final Logger log = LoggerFactory.getLogger(TranslateResource.class);

    private static final String ENTITY_NAME = "translate";

    private final TranslateService translateService;

    public TranslateResource(TranslateService translateService) {
        this.translateService = translateService;
    }

    /**
     * POST  /translates : Create a new translate.
     *
     * @param translateDTO the translateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new translateDTO, or with status 400 (Bad Request) if the translate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/translates")
    @Timed
    public ResponseEntity<TranslateDTO> createTranslate(@RequestBody TranslateDTO translateDTO) throws URISyntaxException {
        log.debug("REST request to save Translate : {}", translateDTO);
        if (translateDTO.getId() != null) {
            throw new BadRequestAlertException("A new translate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranslateDTO result = translateService.save(translateDTO);
        return ResponseEntity.created(new URI("/api/translates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /translates : Updates an existing translate.
     *
     * @param translateDTO the translateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated translateDTO,
     * or with status 400 (Bad Request) if the translateDTO is not valid,
     * or with status 500 (Internal Server Error) if the translateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/translates")
    @Timed
    public ResponseEntity<TranslateDTO> updateTranslate(@RequestBody TranslateDTO translateDTO) throws URISyntaxException {
        log.debug("REST request to update Translate : {}", translateDTO);
        if (translateDTO.getId() == null) {
            return createTranslate(translateDTO);
        }
        TranslateDTO result = translateService.save(translateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, translateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /translates : get all the translates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of translates in body
     */
    @GetMapping("/translates")
    @Timed
    public List<TranslateDTO> getAllTranslates() {
        log.debug("REST request to get all Translates");
        return translateService.findAll();
        }

    /**
     * GET  /translates/:id : get the "id" translate.
     *
     * @param id the id of the translateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the translateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/translates/{id}")
    @Timed
    public ResponseEntity<TranslateDTO> getTranslate(@PathVariable Long id) {
        log.debug("REST request to get Translate : {}", id);
        TranslateDTO translateDTO = translateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(translateDTO));
    }

    /**
     * DELETE  /translates/:id : delete the "id" translate.
     *
     * @param id the id of the translateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/translates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTranslate(@PathVariable Long id) {
        log.debug("REST request to delete Translate : {}", id);
        translateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
