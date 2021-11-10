package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DbCarouselDeal}.
 */
public interface DbCarouselDealService {
    /**
     * Save a dbCarouselDeal.
     *
     * @param dbCarouselDeal the entity to save.
     * @return the persisted entity.
     */
    DbCarouselDeal save(DbCarouselDeal dbCarouselDeal);

    /**
     * Partially updates a dbCarouselDeal.
     *
     * @param dbCarouselDeal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DbCarouselDeal> partialUpdate(DbCarouselDeal dbCarouselDeal);

    /**
     * Get all the dbCarouselDeals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DbCarouselDeal> findAll(Pageable pageable);

    /**
     * Get the "id" dbCarouselDeal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DbCarouselDeal> findOne(Long id);

    /**
     * Delete the "id" dbCarouselDeal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
