package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.LoginProfile;
import com.cloudfen.flashadeal.repository.LoginProfileRepository;
import com.cloudfen.flashadeal.service.LoginProfileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoginProfile}.
 */
@Service
@Transactional
public class LoginProfileServiceImpl implements LoginProfileService {

    private final Logger log = LoggerFactory.getLogger(LoginProfileServiceImpl.class);

    private final LoginProfileRepository loginProfileRepository;

    public LoginProfileServiceImpl(LoginProfileRepository loginProfileRepository) {
        this.loginProfileRepository = loginProfileRepository;
    }

    @Override
    public LoginProfile save(LoginProfile loginProfile) {
        log.debug("Request to save LoginProfile : {}", loginProfile);
        return loginProfileRepository.save(loginProfile);
    }

    @Override
    public Optional<LoginProfile> partialUpdate(LoginProfile loginProfile) {
        log.debug("Request to partially update LoginProfile : {}", loginProfile);

        return loginProfileRepository
            .findById(loginProfile.getId())
            .map(existingLoginProfile -> {
                if (loginProfile.getMemberType() != null) {
                    existingLoginProfile.setMemberType(loginProfile.getMemberType());
                }
                if (loginProfile.getPhoneNumber() != null) {
                    existingLoginProfile.setPhoneNumber(loginProfile.getPhoneNumber());
                }
                if (loginProfile.getEmailId() != null) {
                    existingLoginProfile.setEmailId(loginProfile.getEmailId());
                }
                if (loginProfile.getPassword() != null) {
                    existingLoginProfile.setPassword(loginProfile.getPassword());
                }
                if (loginProfile.getActivationCode() != null) {
                    existingLoginProfile.setActivationCode(loginProfile.getActivationCode());
                }

                return existingLoginProfile;
            })
            .map(loginProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoginProfile> findAll(Pageable pageable) {
        log.debug("Request to get all LoginProfiles");
        return loginProfileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginProfile> findOne(Long id) {
        log.debug("Request to get LoginProfile : {}", id);
        return loginProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginProfile : {}", id);
        loginProfileRepository.deleteById(id);
    }
}
