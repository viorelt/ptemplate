package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.I18NService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.I18NDTO;
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
 * REST controller for managing I18N.
 */
@RestController
@RequestMapping("/api")
public class I18NResource {

    private final Logger log = LoggerFactory.getLogger(I18NResource.class);

    private static final String ENTITY_NAME = "i18N";

    private final I18NService i18NService;

    public I18NResource(I18NService i18NService) {
        this.i18NService = i18NService;
    }

    /**
     * POST  /i-18-ns : Create a new i18N.
     *
     * @param i18NDTO the i18NDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new i18NDTO, or with status 400 (Bad Request) if the i18N has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/i-18-ns")
    @Timed
    public ResponseEntity<I18NDTO> createI18N(@RequestBody I18NDTO i18NDTO) throws URISyntaxException {
        log.debug("REST request to save I18N : {}", i18NDTO);
        if (i18NDTO.getId() != null) {
            throw new BadRequestAlertException("A new i18N cannot already have an ID", ENTITY_NAME, "idexists");
        }
        I18NDTO result = i18NService.save(i18NDTO);
        return ResponseEntity.created(new URI("/api/i-18-ns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /i-18-ns : Updates an existing i18N.
     *
     * @param i18NDTO the i18NDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated i18NDTO,
     * or with status 400 (Bad Request) if the i18NDTO is not valid,
     * or with status 500 (Internal Server Error) if the i18NDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/i-18-ns")
    @Timed
    public ResponseEntity<I18NDTO> updateI18N(@RequestBody I18NDTO i18NDTO) throws URISyntaxException {
        log.debug("REST request to update I18N : {}", i18NDTO);
        if (i18NDTO.getId() == null) {
            return createI18N(i18NDTO);
        }
        I18NDTO result = i18NService.save(i18NDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, i18NDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /i-18-ns : get all the i18NS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of i18NS in body
     */
    @GetMapping("/i-18-ns")
    @Timed
    public List<I18NDTO> getAllI18NS() {
        log.debug("REST request to get all I18NS");
        return i18NService.findAll();
        }

    /**
     * GET  /i-18-ns/:id : get the "id" i18N.
     *
     * @param id the id of the i18NDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the i18NDTO, or with status 404 (Not Found)
     */
    @GetMapping("/i-18-ns/{id}")
    @Timed
    public ResponseEntity<I18NDTO> getI18N(@PathVariable Long id) {
        log.debug("REST request to get I18N : {}", id);
        I18NDTO i18NDTO = i18NService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(i18NDTO));
    }

    /**
     * DELETE  /i-18-ns/:id : delete the "id" i18N.
     *
     * @param id the id of the i18NDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/i-18-ns/{id}")
    @Timed
    public ResponseEntity<Void> deleteI18N(@PathVariable Long id) {
        log.debug("REST request to delete I18N : {}", id);
        i18NService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
