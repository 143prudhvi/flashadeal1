package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SideDisplayDeal}.
 */
public interface SideDisplayDealService {
    /**
     * Save a sideDisplayDeal.
     *
     * @param sideDisplayDeal the entity to save.
     * @return the persisted entity.
     */
    SideDisplayDeal save(SideDisplayDeal sideDisplayDeal);

    /**
     * Partially updates a sideDisplayDeal.
     *
     * @param sideDisplayDeal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SideDisplayDeal> partialUpdate(SideDisplayDeal sideDisplayDeal);

    /**
     * Get all the sideDisplayDeals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SideDisplayDeal> findAll(Pageable pageable);

    /**
     * Get the "id" sideDisplayDeal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SideDisplayDeal> findOne(Long id);

    /**
     * Delete the "id" sideDisplayDeal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
