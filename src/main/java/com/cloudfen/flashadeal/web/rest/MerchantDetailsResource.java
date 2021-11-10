package com.cloudfen.flashadeal.web.rest;

import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.repository.MerchantDetailsRepository;
import com.cloudfen.flashadeal.service.MerchantDetailsService;
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
 * REST controller for managing {@link com.cloudfen.flashadeal.domain.MerchantDetails}.
 */
@RestController
@RequestMapping("/api")
public class MerchantDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MerchantDetailsResource.class);

    private static final String ENTITY_NAME = "merchantDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MerchantDetailsService merchantDetailsService;

    private final MerchantDetailsRepository merchantDetailsRepository;

    public MerchantDetailsResource(MerchantDetailsService merchantDetailsService, MerchantDetailsRepository merchantDetailsRepository) {
        this.merchantDetailsService = merchantDetailsService;
        this.merchantDetailsRepository = merchantDetailsRepository;
    }

    /**
     * {@code POST  /merchant-details} : Create a new merchantDetails.
     *
     * @param merchantDetails the merchantDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new merchantDetails, or with status {@code 400 (Bad Request)} if the merchantDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/merchant-details")
    public ResponseEntity<MerchantDetails> createMerchantDetails(@RequestBody MerchantDetails merchantDetails) throws URISyntaxException {
        log.debug("REST request to save MerchantDetails : {}", merchantDetails);
        if (merchantDetails.getId() != null) {
            throw new BadRequestAlertException("A new merchantDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantDetails result = merchantDetailsService.save(merchantDetails);
        return ResponseEntity
            .created(new URI("/api/merchant-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /merchant-details/:id} : Updates an existing merchantDetails.
     *
     * @param id the id of the merchantDetails to save.
     * @param merchantDetails the merchantDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantDetails,
     * or with status {@code 400 (Bad Request)} if the merchantDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the merchantDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/merchant-details/{id}")
    public ResponseEntity<MerchantDetails> updateMerchantDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MerchantDetails merchantDetails
    ) throws URISyntaxException {
        log.debug("REST request to update MerchantDetails : {}, {}", id, merchantDetails);
        if (merchantDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, merchantDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!merchantDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MerchantDetails result = merchantDetailsService.save(merchantDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, merchantDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /merchant-details/:id} : Partial updates given fields of an existing merchantDetails, field will ignore if it is null
     *
     * @param id the id of the merchantDetails to save.
     * @param merchantDetails the merchantDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated merchantDetails,
     * or with status {@code 400 (Bad Request)} if the merchantDetails is not valid,
     * or with status {@code 404 (Not Found)} if the merchantDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the merchantDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/merchant-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MerchantDetails> partialUpdateMerchantDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MerchantDetails merchantDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update MerchantDetails partially : {}, {}", id, merchantDetails);
        if (merchantDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, merchantDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!merchantDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MerchantDetails> result = merchantDetailsService.partialUpdate(merchantDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, merchantDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /merchant-details} : get all the merchantDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of merchantDetails in body.
     */
    @GetMapping("/merchant-details")
    public List<MerchantDetails> getAllMerchantDetails() {
        log.debug("REST request to get all MerchantDetails");
        return merchantDetailsService.findAll();
    }

    /**
     * {@code GET  /merchant-details/:id} : get the "id" merchantDetails.
     *
     * @param id the id of the merchantDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the merchantDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/merchant-details/{id}")
    public ResponseEntity<MerchantDetails> getMerchantDetails(@PathVariable Long id) {
        log.debug("REST request to get MerchantDetails : {}", id);
        Optional<MerchantDetails> merchantDetails = merchantDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(merchantDetails);
    }

    /**
     * {@code DELETE  /merchant-details/:id} : delete the "id" merchantDetails.
     *
     * @param id the id of the merchantDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/merchant-details/{id}")
    public ResponseEntity<Void> deleteMerchantDetails(@PathVariable Long id) {
        log.debug("REST request to delete MerchantDetails : {}", id);
        merchantDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
