package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.DealUserRelation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DealUserRelation}.
 */
public interface DealUserRelationService {
    /**
     * Save a dealUserRelation.
     *
     * @param dealUserRelation the entity to save.
     * @return the persisted entity.
     */
    DealUserRelation save(DealUserRelation dealUserRelation);

    /**
     * Partially updates a dealUserRelation.
     *
     * @param dealUserRelation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DealUserRelation> partialUpdate(DealUserRelation dealUserRelation);

    /**
     * Get all the dealUserRelations.
     *
     * @return the list of entities.
     */
    List<DealUserRelation> findAll();

    /**
     * Get the "id" dealUserRelation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DealUserRelation> findOne(Long id);

    /**
     * Delete the "id" dealUserRelation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
