package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.DealCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DealCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealCategoryRepository extends JpaRepository<DealCategory, Long> {}
