package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.DealUserRelation;
import com.cloudfen.flashadeal.repository.DealUserRelationRepository;
import com.cloudfen.flashadeal.service.DealUserRelationService;
import com.cloudfen.flashadeal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.DealUserRelation}.
 */
@RestController
@RequestMapping("/api")
public class DealUserRelationResource {

    private final Logger log = LoggerFactory.getLogger(DealUserRelationResource.class);

    private static final String ENTITY_NAME = "dealUserRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealUserRelationService dealUserRelationService;

    private final DealUserRelationRepository dealUserRelationRepository;

    public DealUserRelationResource(
        DealUserRelationService dealUserRelationService,
        DealUserRelationRepository dealUserRelationRepository
    ) {
        this.dealUserRelationService = dealUserRelationService;
        this.dealUserRelationRepository = dealUserRelationRepository;
    }

    /**
     * {@code POST  /deal-user-relations} : Create a new dealUserRelation.
     *
     * @param dealUserRelation the dealUserRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealUserRelation, or with status {@code 400 (Bad Request)} if the dealUserRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-user-relations")
    public ResponseEntity<DealUserRelation> createDealUserRelation(@RequestBody DealUserRelation dealUserRelation)
        throws URISyntaxException {
        log.debug("REST request to save DealUserRelation : {}", dealUserRelation);
        if (dealUserRelation.getId() != null) {
            throw new BadRequestAlertException("A new dealUserRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealUserRelation result = dealUserRelationService.save(dealUserRelation);
        return ResponseEntity
            .created(new URI("/api/deal-user-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-user-relations/:id} : Updates an existing dealUserRelation.
     *
     * @param id the id of the dealUserRelation to save.
     * @param dealUserRelation the dealUserRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealUserRelation,
     * or with status {@code 400 (Bad Request)} if the dealUserRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealUserRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-user-relations/{id}")
    public ResponseEntity<DealUserRelation> updateDealUserRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealUserRelation dealUserRelation
    ) throws URISyntaxException {
        log.debug("REST request to update DealUserRelation : {}, {}", id, dealUserRelation);
        if (dealUserRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealUserRelation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealUserRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealUserRelation result = dealUserRelationService.save(dealUserRelation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealUserRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-user-relations/:id} : Partial updates given fields of an existing dealUserRelation, field will ignore if it is null
     *
     * @param id the id of the dealUserRelation to save.
     * @param dealUserRelation the dealUserRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealUserRelation,
     * or with status {@code 400 (Bad Request)} if the dealUserRelation is not valid,
     * or with status {@code 404 (Not Found)} if the dealUserRelation is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealUserRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-user-relations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealUserRelation> partialUpdateDealUserRelation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealUserRelation dealUserRelation
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealUserRelation partially : {}, {}", id, dealUserRelation);
        if (dealUserRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealUserRelation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealUserRelationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealUserRelation> result = dealUserRelationService.partialUpdate(dealUserRelation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealUserRelation.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-user-relations} : get all the dealUserRelations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealUserRelations in body.
     */
    @GetMapping("/deal-user-relations")
    public List<DealUserRelation> getAllDealUserRelations() {
        log.debug("REST request to get all DealUserRelations");
        return dealUserRelationService.findAll();
    }

    /**
     * {@code GET  /deal-user-relations/:id} : get the "id" dealUserRelation.
     *
     * @param id the id of the dealUserRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealUserRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-user-relations/{id}")
    public ResponseEntity<DealUserRelation> getDealUserRelation(@PathVariable Long id) {
        log.debug("REST request to get DealUserRelation : {}", id);
        Optional<DealUserRelation> dealUserRelation = dealUserRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealUserRelation);
    }

    /**
     * {@code DELETE  /deal-user-relations/:id} : delete the "id" dealUserRelation.
     *
     * @param id the id of the dealUserRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-user-relations/{id}")
    public ResponseEntity<Void> deleteDealUserRelation(@PathVariable Long id) {
        log.debug("REST request to delete DealUserRelation : {}", id);
        dealUserRelationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
