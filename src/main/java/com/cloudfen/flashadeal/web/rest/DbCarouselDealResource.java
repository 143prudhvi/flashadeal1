package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import com.cloudfen.flashadeal.repository.DbCarouselDealRepository;
import com.cloudfen.flashadeal.service.DbCarouselDealQueryService;
import com.cloudfen.flashadeal.service.DbCarouselDealService;
import com.cloudfen.flashadeal.service.criteria.DbCarouselDealCriteria;
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
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.DbCarouselDeal}.
 */
@RestController
@RequestMapping("/api")
public class DbCarouselDealResource {

    private final Logger log = LoggerFactory.getLogger(DbCarouselDealResource.class);

    private static final String ENTITY_NAME = "dbCarouselDeal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DbCarouselDealService dbCarouselDealService;

    private final DbCarouselDealRepository dbCarouselDealRepository;

    private final DbCarouselDealQueryService dbCarouselDealQueryService;

    public DbCarouselDealResource(
        DbCarouselDealService dbCarouselDealService,
        DbCarouselDealRepository dbCarouselDealRepository,
        DbCarouselDealQueryService dbCarouselDealQueryService
    ) {
        this.dbCarouselDealService = dbCarouselDealService;
        this.dbCarouselDealRepository = dbCarouselDealRepository;
        this.dbCarouselDealQueryService = dbCarouselDealQueryService;
    }

    /**
     * {@code POST  /db-carousel-deals} : Create a new dbCarouselDeal.
     *
     * @param dbCarouselDeal the dbCarouselDeal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dbCarouselDeal, or with status {@code 400 (Bad Request)} if the dbCarouselDeal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/db-carousel-deals")
    public ResponseEntity<DbCarouselDeal> createDbCarouselDeal(@RequestBody DbCarouselDeal dbCarouselDeal) throws URISyntaxException {
        log.debug("REST request to save DbCarouselDeal : {}", dbCarouselDeal);
        if (dbCarouselDeal.getId() != null) {
            throw new BadRequestAlertException("A new dbCarouselDeal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DbCarouselDeal result = dbCarouselDealService.save(dbCarouselDeal);
        return ResponseEntity
            .created(new URI("/api/db-carousel-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /db-carousel-deals/:id} : Updates an existing dbCarouselDeal.
     *
     * @param id the id of the dbCarouselDeal to save.
     * @param dbCarouselDeal the dbCarouselDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dbCarouselDeal,
     * or with status {@code 400 (Bad Request)} if the dbCarouselDeal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dbCarouselDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/db-carousel-deals/{id}")
    public ResponseEntity<DbCarouselDeal> updateDbCarouselDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DbCarouselDeal dbCarouselDeal
    ) throws URISyntaxException {
        log.debug("REST request to update DbCarouselDeal : {}, {}", id, dbCarouselDeal);
        if (dbCarouselDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dbCarouselDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dbCarouselDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DbCarouselDeal result = dbCarouselDealService.save(dbCarouselDeal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dbCarouselDeal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /db-carousel-deals/:id} : Partial updates given fields of an existing dbCarouselDeal, field will ignore if it is null
     *
     * @param id the id of the dbCarouselDeal to save.
     * @param dbCarouselDeal the dbCarouselDeal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dbCarouselDeal,
     * or with status {@code 400 (Bad Request)} if the dbCarouselDeal is not valid,
     * or with status {@code 404 (Not Found)} if the dbCarouselDeal is not found,
     * or with status {@code 500 (Internal Server Error)} if the dbCarouselDeal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/db-carousel-deals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DbCarouselDeal> partialUpdateDbCarouselDeal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DbCarouselDeal dbCarouselDeal
    ) throws URISyntaxException {
        log.debug("REST request to partial update DbCarouselDeal partially : {}, {}", id, dbCarouselDeal);
        if (dbCarouselDeal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dbCarouselDeal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dbCarouselDealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DbCarouselDeal> result = dbCarouselDealService.partialUpdate(dbCarouselDeal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dbCarouselDeal.getId().toString())
        );
    }

    /**
     * {@code GET  /db-carousel-deals} : get all the dbCarouselDeals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dbCarouselDeals in body.
     */
    @GetMapping("/db-carousel-deals")
    public ResponseEntity<List<DbCarouselDeal>> getAllDbCarouselDeals(DbCarouselDealCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DbCarouselDeals by criteria: {}", criteria);
        Page<DbCarouselDeal> page = dbCarouselDealQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /db-carousel-deals/count} : count all the dbCarouselDeals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/db-carousel-deals/count")
    public ResponseEntity<Long> countDbCarouselDeals(DbCarouselDealCriteria criteria) {
        log.debug("REST request to count DbCarouselDeals by criteria: {}", criteria);
        return ResponseEntity.ok().body(dbCarouselDealQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /db-carousel-deals/:id} : get the "id" dbCarouselDeal.
     *
     * @param id the id of the dbCarouselDeal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dbCarouselDeal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/db-carousel-deals/{id}")
    public ResponseEntity<DbCarouselDeal> getDbCarouselDeal(@PathVariable Long id) {
        log.debug("REST request to get DbCarouselDeal : {}", id);
        Optional<DbCarouselDeal> dbCarouselDeal = dbCarouselDealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dbCarouselDeal);
    }

    /**
     * {@code DELETE  /db-carousel-deals/:id} : delete the "id" dbCarouselDeal.
     *
     * @param id the id of the dbCarouselDeal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/db-carousel-deals/{id}")
    public ResponseEntity<Void> deleteDbCarouselDeal(@PathVariable Long id) {
        log.debug("REST request to delete DbCarouselDeal : {}", id);
        dbCarouselDealService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
