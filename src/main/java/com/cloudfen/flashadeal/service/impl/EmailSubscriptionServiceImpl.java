package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.EmailSubscription;
import com.cloudfen.flashadeal.repository.EmailSubscriptionRepository;
import com.cloudfen.flashadeal.service.EmailSubscriptionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmailSubscription}.
 */
@Service
@Transactional
public class EmailSubscriptionServiceImpl implements EmailSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(EmailSubscriptionServiceImpl.class);

    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public EmailSubscriptionServiceImpl(EmailSubscriptionRepository emailSubscriptionRepository) {
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    @Override
    public EmailSubscription save(EmailSubscription emailSubscription) {
        log.debug("Request to save EmailSubscription : {}", emailSubscription);
        return emailSubscriptionRepository.save(emailSubscription);
    }

    @Override
    public Optional<EmailSubscription> partialUpdate(EmailSubscription emailSubscription) {
        log.debug("Request to partially update EmailSubscription : {}", emailSubscription);

        return emailSubscriptionRepository
            .findById(emailSubscription.getId())
            .map(existingEmailSubscription -> {
                if (emailSubscription.getEmail() != null) {
                    existingEmailSubscription.setEmail(emailSubscription.getEmail());
                }
                if (emailSubscription.getCountry() != null) {
                    existingEmailSubscription.setCountry(emailSubscription.getCountry());
                }

                return existingEmailSubscription;
            })
            .map(emailSubscriptionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailSubscription> findAll() {
        log.debug("Request to get all EmailSubscriptions");
        return emailSubscriptionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailSubscription> findOne(Long id) {
        log.debug("Request to get EmailSubscription : {}", id);
        return emailSubscriptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailSubscription : {}", id);
        emailSubscriptionRepository.deleteById(id);
    }
}
