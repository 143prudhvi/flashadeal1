package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import com.cloudfen.flashadeal.repository.SideDisplayDealRepository;
import com.cloudfen.flashadeal.service.SideDisplayDealQueryService;
import com.cloudfen.flashadeal.service.SideDisplayDealService;
import com.cloudfen.flashadeal.service.criteria.SideDisplayDealCriteria;
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
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.SideDisplayDeal}.
 */
@RestController
@RequestMapping("/api")
public class SideDisplayDealResource {

    private final Logger log = LoggerFactory.getLogger(SideDisplayDealResource.class);

    private static final String ENTITY_NAME = "sideDisplayDeal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SideDisplayDealService sideDisplayDealService;

    private final SideDisplayDealRepository sideDisplayDealRepository;

    private final SideDisplayDealQueryService sideDisplayDealQueryService;

    public SideDisplayDealResource(
        SideDisplayDealService sideDisplayDealService,
        SideDisplayDealRepository sideDisplayDealRepository,
        SideDisplayDealQueryService sideDisplayDealQueryService
    ) {
        this.sideDisplayDealService = sideDisplayDealService;
        this.sideDisplayDealRepository = sideDisplayDealRepository;
        this.sideDisplayDealQueryService = sideDisplayDealQueryService;
    }

    /**
     * {@code POST  /side-display-deals} : Create a new sideDisplayDeal.
     *
     * @param sideDisplayDeal the sideDisplayDeal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sideDisplayDeal, or with status {@code 400 (Bad Request)} if the sideDisplayDeal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/side-display-deals")
    public ResponseEntity<SideDisplayDeal> createSideDisplayDeal(@RequestBody SideDisplayDeal sideDisplayDeal) throws URISyntaxException {
        log.debug("REST request to save SideDisplayDeal : {}", sideDisplayDeal);
        if (sideDisplayDeal.getId() != null) {
            throw new BadRequestAlertException("A new sideDisplayDeal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SideDisplayDeal result = sideDisplayDealService.save(sideDisplayDeal);
        return ResponseEntity
            .created(new URI("/api/side-display-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /side-display-deals/:id} : Updates an existing sideDisplayDeal.
     *
     * @param id the id of the sideDisplayDeal to save.
     * @param sideDisplayDeal the sideDisplayDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sideDisplayDeal,
     * or with status {@code 400 (Bad Request)} if the sideDisplayDeal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sideDisplayDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/side-display-deals/{id}")
    public ResponseEntity<SideDisplayDeal> updateSideDisplayDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SideDisplayDeal sideDisplayDeal
    ) throws URISyntaxException {
        log.debug("REST request to update SideDisplayDeal : {}, {}", id, sideDisplayDeal);
        if (sideDisplayDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sideDisplayDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sideDisplayDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SideDisplayDeal result = sideDisplayDealService.save(sideDisplayDeal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sideDisplayDeal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /side-display-deals/:id} : Partial updates given fields of an existing sideDisplayDeal, field will ignore if it is null
     *
     * @param id the id of the sideDisplayDeal to save.
     * @param sideDisplayDeal the sideDisplayDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sideDisplayDeal,
     * or with status {@code 400 (Bad Request)} if the sideDisplayDeal is not valid,
     * or with status {@code 404 (Not Found)} if the sideDisplayDeal is not found,
     * or with status {@code 500 (Internal Server Error)} if the sideDisplayDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/side-display-deals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SideDisplayDeal> partialUpdateSideDisplayDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SideDisplayDeal sideDisplayDeal
    ) throws URISyntaxException {
        log.debug("REST request to partial update SideDisplayDeal partially : {}, {}", id, sideDisplayDeal);
        if (sideDisplayDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sideDisplayDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sideDisplayDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SideDisplayDeal> result = sideDisplayDealService.partialUpdate(sideDisplayDeal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sideDisplayDeal.getId().toString())
        );
    }

    /**
     * {@code GET  /side-display-deals} : get all the sideDisplayDeals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sideDisplayDeals in body.
     */
    @GetMapping("/side-display-deals")
    public ResponseEntity<List<SideDisplayDeal>> getAllSideDisplayDeals(SideDisplayDealCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SideDisplayDeals by criteria: {}", criteria);
        Page<SideDisplayDeal> page = sideDisplayDealQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /side-display-deals/count} : count all the sideDisplayDeals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/side-display-deals/count")
    public ResponseEntity<Long> countSideDisplayDeals(SideDisplayDealCriteria criteria) {
        log.debug("REST request to count SideDisplayDeals by criteria: {}", criteria);
        return ResponseEntity.ok().body(sideDisplayDealQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /side-display-deals/:id} : get the "id" sideDisplayDeal.
     *
     * @param id the id of the sideDisplayDeal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sideDisplayDeal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/side-display-deals/{id}")
    public ResponseEntity<SideDisplayDeal> getSideDisplayDeal(@PathVariable Long id) {
        log.debug("REST request to get SideDisplayDeal : {}", id);
        Optional<SideDisplayDeal> sideDisplayDeal = sideDisplayDealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sideDisplayDeal);
    }

    /**
     * {@code DELETE  /side-display-deals/:id} : delete the "id" sideDisplayDeal.
     *
     * @param id the id of the sideDisplayDeal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/side-display-deals/{id}")
    public ResponseEntity<Void> deleteSideDisplayDeal(@PathVariable Long id) {
        log.debug("REST request to delete SideDisplayDeal : {}", id);
        sideDisplayDealService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
