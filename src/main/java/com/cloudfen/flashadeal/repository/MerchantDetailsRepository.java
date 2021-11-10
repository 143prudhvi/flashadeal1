package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.MerchantDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MerchantDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantDetailsRepository extends JpaRepository<MerchantDetails, Long> {}
