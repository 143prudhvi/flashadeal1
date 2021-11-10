package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.EmailSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmailSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {}
