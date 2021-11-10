package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.DealCategory;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DealCategory}.
 */
public interface DealCategoryService {
    /**
     * Save a dealCategory.
     *
     * @param dealCategory the entity to save.
     * @return the persisted entity.
     */
    DealCategory save(DealCategory dealCategory);

    /**
     * Partially updates a dealCategory.
     *
     * @param dealCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DealCategory> partialUpdate(DealCategory dealCategory);

    /**
     * Get all the dealCategories.
     *
     * @return the list of entities.
     */
    List<DealCategory> findAll();

    /**
     * Get the "id" dealCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DealCategory> findOne(Long id);

    /**
     * Delete the "id" dealCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
