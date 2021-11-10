package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.LoginProfile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link LoginProfile}.
 */
public interface LoginProfileService {
    /**
     * Save a loginProfile.
     *
     * @param loginProfile the entity to save.
     * @return the persisted entity.
     */
    LoginProfile save(LoginProfile loginProfile);

    /**
     * Partially updates a loginProfile.
     *
     * @param loginProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoginProfile> partialUpdate(LoginProfile loginProfile);

    /**
     * Get all the loginProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoginProfile> findAll(Pageable pageable);

    /**
     * Get the "id" loginProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoginProfile> findOne(Long id);

    /**
     * Delete the "id" loginProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
