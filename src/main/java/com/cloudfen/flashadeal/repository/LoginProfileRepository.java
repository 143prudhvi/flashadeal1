package com.cloudfen.flashadeal.repository;

import com.cloudfen.flashadeal.domain.LoginProfile;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoginProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginProfileRepository extends JpaRepository<LoginProfile, Long> {
    @Query("select loginProfile from LoginProfile loginProfile where loginProfile.user.login = ?#{principal.username}")
    List<LoginProfile> findByUserIsCurrentUser();
}
