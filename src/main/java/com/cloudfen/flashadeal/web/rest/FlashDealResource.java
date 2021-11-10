package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.FlashDeal;
import com.cloudfen.flashadeal.repository.FlashDealRepository;
import com.cloudfen.flashadeal.service.FlashDealQueryService;
import com.cloudfen.flashadeal.service.FlashDealService;
import com.cloudfen.flashadeal.service.criteria.FlashDealCriteria;
import com.cloudfen.flashadeal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.FlashDeal}.
 */
@RestController
@RequestMapping("/api")
public class FlashDealResource {

    private final Logger log = LoggerFactory.getLogger(FlashDealResource.class);

    private static final String ENTITY_NAME = "flashDeal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlashDealService flashDealService;

    private final FlashDealRepository flashDealRepository;

    private final FlashDealQueryService flashDealQueryService;

    public FlashDealResource(
        FlashDealService flashDealService,
        FlashDealRepository flashDealRepository,
        FlashDealQueryService flashDealQueryService
    ) {
        this.flashDealService = flashDealService;
        this.flashDealRepository = flashDealRepository;
        this.flashDealQueryService = flashDealQueryService;
    }

    /**
     * {@code POST  /flash-deals} : Create a new flashDeal.
     *
     * @param flashDeal the flashDeal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flashDeal, or with status {@code 400 (Bad Request)} if the flashDeal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flash-deals")
    public ResponseEntity<FlashDeal> createFlashDeal(@RequestBody FlashDeal flashDeal) throws URISyntaxException {
        log.debug("REST request to save FlashDeal : {}", flashDeal);
        if (flashDeal.getId() != null) {
            throw new BadRequestAlertException("A new flashDeal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlashDeal result = flashDealService.save(flashDeal);
        return ResponseEntity
            .created(new URI("/api/flash-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flash-deals/:id} : Updates an existing flashDeal.
     *
     * @param id the id of the flashDeal to save.
     * @param flashDeal the flashDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flashDeal,
     * or with status {@code 400 (Bad Request)} if the flashDeal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flashDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flash-deals/{id}")
    public ResponseEntity<FlashDeal> updateFlashDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FlashDeal flashDeal
    ) throws URISyntaxException {
        log.debug("REST request to update FlashDeal : {}, {}", id, flashDeal);
        if (flashDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flashDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flashDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlashDeal result = flashDealService.save(flashDeal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flashDeal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flash-deals/:id} : Partial updates given fields of an existing flashDeal, field will ignore if it is null
     *
     * @param id the id of the flashDeal to save.
     * @param flashDeal the flashDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flashDeal,
     * or with status {@code 400 (Bad Request)} if the flashDeal is not valid,
     * or with status {@code 404 (Not Found)} if the flashDeal is not found,
     * or with status {@code 500 (Internal Server Error)} if the flashDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flash-deals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlashDeal> partialUpdateFlashDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FlashDeal flashDeal
    ) throws URISyntaxException {
        log.debug("REST request to partial update FlashDeal partially : {}, {}", id, flashDeal);
        if (flashDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flashDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flashDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlashDeal> result = flashDealService.partialUpdate(flashDeal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flashDeal.getId().toString())
        );
    }

    /**
     * {@code GET  /flash-deals} : get all the flashDeals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flashDeals in body.
     */
    @GetMapping("/flash-deals")
    public ResponseEntity<List<FlashDeal>> getAllFlashDeals(FlashDealCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FlashDeals by criteria: {}", criteria);
        Page<FlashDeal> page = flashDealQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /flash-deals/count} : count all the flashDeals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/flash-deals/count")
    public ResponseEntity<Long> countFlashDeals(FlashDealCriteria criteria) {
        log.debug("REST request to count FlashDeals by criteria: {}", criteria);
        return ResponseEntity.ok().body(flashDealQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /flash-deals/:id} : get the "id" flashDeal.
     *
     * @param id the id of the flashDeal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flashDeal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flash-deals/{id}")
    public ResponseEntity<FlashDeal> getFlashDeal(@PathVariable Long id) {
        log.debug("REST request to get FlashDeal : {}", id);
        Optional<FlashDeal> flashDeal = flashDealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flashDeal);
    }

    /**
     * {@code DELETE  /flash-deals/:id} : delete the "id" flashDeal.
     *
     * @param id the id of the flashDeal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flash-deals/{id}")
    public ResponseEntity<Void> deleteFlashDeal(@PathVariable Long id) {
        log.debug("REST request to delete FlashDeal : {}", id);
        flashDealService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
