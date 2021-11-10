package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.repository.DealCategoryRepository;
import com.cloudfen.flashadeal.service.DealCategoryService;
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
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.DealCategory}.
 */
@RestController
@RequestMapping("/api")
public class DealCategoryResource {

    private final Logger log = LoggerFactory.getLogger(DealCategoryResource.class);

    private static final String ENTITY_NAME = "dealCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealCategoryService dealCategoryService;

    private final DealCategoryRepository dealCategoryRepository;

    public DealCategoryResource(DealCategoryService dealCategoryService, DealCategoryRepository dealCategoryRepository) {
        this.dealCategoryService = dealCategoryService;
        this.dealCategoryRepository = dealCategoryRepository;
    }

    /**
     * {@code POST  /deal-categories} : Create a new dealCategory.
     *
     * @param dealCategory the dealCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealCategory, or with status {@code 400 (Bad Request)} if the dealCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-categories")
    public ResponseEntity<DealCategory> createDealCategory(@RequestBody DealCategory dealCategory) throws URISyntaxException {
        log.debug("REST request to save DealCategory : {}", dealCategory);
        if (dealCategory.getId() != null) {
            throw new BadRequestAlertException("A new dealCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealCategory result = dealCategoryService.save(dealCategory);
        return ResponseEntity
            .created(new URI("/api/deal-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-categories/:id} : Updates an existing dealCategory.
     *
     * @param id the id of the dealCategory to save.
     * @param dealCategory the dealCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealCategory,
     * or with status {@code 400 (Bad Request)} if the dealCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-categories/{id}")
    public ResponseEntity<DealCategory> updateDealCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealCategory dealCategory
    ) throws URISyntaxException {
        log.debug("REST request to update DealCategory : {}, {}", id, dealCategory);
        if (dealCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealCategory result = dealCategoryService.save(dealCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-categories/:id} : Partial updates given fields of an existing dealCategory, field will ignore if it is null
     *
     * @param id the id of the dealCategory to save.
     * @param dealCategory the dealCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealCategory,
     * or with status {@code 400 (Bad Request)} if the dealCategory is not valid,
     * or with status {@code 404 (Not Found)} if the dealCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealCategory> partialUpdateDealCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealCategory dealCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealCategory partially : {}, {}", id, dealCategory);
        if (dealCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealCategory> result = dealCategoryService.partialUpdate(dealCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-categories} : get all the dealCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealCategories in body.
     */
    @GetMapping("/deal-categories")
    public List<DealCategory> getAllDealCategories() {
        log.debug("REST request to get all DealCategories");
        return dealCategoryService.findAll();
    }

    /**
     * {@code GET  /deal-categories/:id} : get the "id" dealCategory.
     *
     * @param id the id of the dealCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-categories/{id}")
    public ResponseEntity<DealCategory> getDealCategory(@PathVariable Long id) {
        log.debug("REST request to get DealCategory : {}", id);
        Optional<DealCategory> dealCategory = dealCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealCategory);
    }

    /**
     * {@code DELETE  /deal-categories/:id} : delete the "id" dealCategory.
     *
     * @param id the id of the dealCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-categories/{id}")
    public ResponseEntity<Void> deleteDealCategory(@PathVariable Long id) {
        log.debug("REST request to delete DealCategory : {}", id);
        dealCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
