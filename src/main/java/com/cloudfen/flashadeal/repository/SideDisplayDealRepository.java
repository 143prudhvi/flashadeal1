package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SideDisplayDeal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SideDisplayDealRepository extends JpaRepository<SideDisplayDeal, Long>, JpaSpecificationExecutor<SideDisplayDeal> {}
