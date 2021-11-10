package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.repository.MerchantDetailsRepository;
import com.cloudfen.flashadeal.service.MerchantDetailsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MerchantDetails}.
 */
@Service
@Transactional
public class MerchantDetailsServiceImpl implements MerchantDetailsService {

    private final Logger log = LoggerFactory.getLogger(MerchantDetailsServiceImpl.class);

    private final MerchantDetailsRepository merchantDetailsRepository;

    public MerchantDetailsServiceImpl(MerchantDetailsRepository merchantDetailsRepository) {
        this.merchantDetailsRepository = merchantDetailsRepository;
    }

    @Override
    public MerchantDetails save(MerchantDetails merchantDetails) {
        log.debug("Request to save MerchantDetails : {}", merchantDetails);
        return merchantDetailsRepository.save(merchantDetails);
    }

    @Override
    public Optional<MerchantDetails> partialUpdate(MerchantDetails merchantDetails) {
        log.debug("Request to partially update MerchantDetails : {}", merchantDetails);

        return merchantDetailsRepository
            .findById(merchantDetails.getId())
            .map(existingMerchantDetails -> {
                if (merchantDetails.getName() != null) {
                    existingMerchantDetails.setName(merchantDetails.getName());
                }
                if (merchantDetails.getCountry() != null) {
                    existingMerchantDetails.setCountry(merchantDetails.getCountry());
                }
                if (merchantDetails.getCity() != null) {
                    existingMerchantDetails.setCity(merchantDetails.getCity());
                }
                if (merchantDetails.getStoreIcon() != null) {
                    existingMerchantDetails.setStoreIcon(merchantDetails.getStoreIcon());
                }
                if (merchantDetails.getType() != null) {
                    existingMerchantDetails.setType(merchantDetails.getType());
                }
                if (merchantDetails.getLocation() != null) {
                    existingMerchantDetails.setLocation(merchantDetails.getLocation());
                }

                return existingMerchantDetails;
            })
            .map(merchantDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDetails> findAll() {
        log.debug("Request to get all MerchantDetails");
        return merchantDetailsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDetails> findOne(Long id) {
        log.debug("Request to get MerchantDetails : {}", id);
        return merchantDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MerchantDetails : {}", id);
        merchantDetailsRepository.deleteById(id);
    }
}
