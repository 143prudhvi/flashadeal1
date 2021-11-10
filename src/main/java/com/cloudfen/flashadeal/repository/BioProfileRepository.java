package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.BioProfile;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BioProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BioProfileRepository extends JpaRepository<BioProfile, Long> {
    @Query("select bioProfile from BioProfile bioProfile where bioProfile.user.login = ?#{principal.username}")
    List<BioProfile> findByUserIsCurrentUser();
}
