package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.MerchantDetails;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MerchantDetails}.
 */
public interface MerchantDetailsService {
    /**
     * Save a merchantDetails.
     *
     * @param merchantDetails the entity to save.
     * @return the persisted entity.
     */
    MerchantDetails save(MerchantDetails merchantDetails);

    /**
     * Partially updates a merchantDetails.
     *
     * @param merchantDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MerchantDetails> partialUpdate(MerchantDetails merchantDetails);

    /**
     * Get all the merchantDetails.
     *
     * @return the list of entities.
     */
    List<MerchantDetails> findAll();

    /**
     * Get the "id" merchantDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantDetails> findOne(Long id);

    /**
     * Delete the "id" merchantDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
