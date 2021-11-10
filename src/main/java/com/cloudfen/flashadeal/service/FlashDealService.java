package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.FlashDeal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FlashDeal}.
 */
public interface FlashDealService {
    /**
     * Save a flashDeal.
     *
     * @param flashDeal the entity to save.
     * @return the persisted entity.
     */
    FlashDeal save(FlashDeal flashDeal);

    /**
     * Partially updates a flashDeal.
     *
     * @param flashDeal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FlashDeal> partialUpdate(FlashDeal flashDeal);

    /**
     * Get all the flashDeals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FlashDeal> findAll(Pageable pageable);

    /**
     * Get the "id" flashDeal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FlashDeal> findOne(Long id);

    /**
     * Delete the "id" flashDeal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
