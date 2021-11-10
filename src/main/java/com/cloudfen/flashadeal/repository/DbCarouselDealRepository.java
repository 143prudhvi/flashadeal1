package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DbCarouselDeal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DbCarouselDealRepository extends JpaRepository<DbCarouselDeal, Long>, JpaSpecificationExecutor<DbCarouselDeal> {}
