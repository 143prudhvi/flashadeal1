package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.DealUserRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DealUserRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealUserRelationRepository extends JpaRepository<DealUserRelation, Long> {}
