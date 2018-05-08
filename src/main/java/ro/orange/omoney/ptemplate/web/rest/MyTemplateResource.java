package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.MyTemplateService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.MyTemplateDTO;
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
 * REST controller for managing MyTemplate.
 */
@RestController
@RequestMapping("/api")
public class MyTemplateResource {

    private final Logger log = LoggerFactory.getLogger(MyTemplateResource.class);

    private static final String ENTITY_NAME = "myTemplate";

    private final MyTemplateService myTemplateService;

    public MyTemplateResource(MyTemplateService myTemplateService) {
        this.myTemplateService = myTemplateService;
    }

    /**
     * POST  /my-templates : Create a new myTemplate.
     *
     * @param myTemplateDTO the myTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myTemplateDTO, or with status 400 (Bad Request) if the myTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-templates")
    @Timed
    public ResponseEntity<MyTemplateDTO> createMyTemplate(@RequestBody MyTemplateDTO myTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save MyTemplate : {}", myTemplateDTO);
        if (myTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new myTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyTemplateDTO result = myTemplateService.save(myTemplateDTO);
        return ResponseEntity.created(new URI("/api/my-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-templates : Updates an existing myTemplate.
     *
     * @param myTemplateDTO the myTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myTemplateDTO,
     * or with status 400 (Bad Request) if the myTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the myTemplateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-templates")
    @Timed
    public ResponseEntity<MyTemplateDTO> updateMyTemplate(@RequestBody MyTemplateDTO myTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update MyTemplate : {}", myTemplateDTO);
        if (myTemplateDTO.getId() == null) {
            return createMyTemplate(myTemplateDTO);
        }
        MyTemplateDTO result = myTemplateService.save(myTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-templates : get all the myTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myTemplates in body
     */
    @GetMapping("/my-templates")
    @Timed
    public List<MyTemplateDTO> getAllMyTemplates() {
        log.debug("REST request to get all MyTemplates");
        return myTemplateService.findAll();
        }

    /**
     * GET  /my-templates/:id : get the "id" myTemplate.
     *
     * @param id the id of the myTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-templates/{id}")
    @Timed
    public ResponseEntity<MyTemplateDTO> getMyTemplate(@PathVariable Long id) {
        log.debug("REST request to get MyTemplate : {}", id);
        MyTemplateDTO myTemplateDTO = myTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myTemplateDTO));
    }

    /**
     * DELETE  /my-templates/:id : delete the "id" myTemplate.
     *
     * @param id the id of the myTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyTemplate(@PathVariable Long id) {
        log.debug("REST request to delete MyTemplate : {}", id);
        myTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
