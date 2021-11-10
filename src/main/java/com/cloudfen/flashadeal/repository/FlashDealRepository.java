package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.FlashDeal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FlashDeal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlashDealRepository extends JpaRepository<FlashDeal, Long>, JpaSpecificationExecutor<FlashDeal> {}
