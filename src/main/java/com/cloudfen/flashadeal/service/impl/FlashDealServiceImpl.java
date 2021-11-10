package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.FlashDeal;
import com.cloudfen.flashadeal.repository.FlashDealRepository;
import com.cloudfen.flashadeal.service.FlashDealService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FlashDeal}.
 */
@Service
@Transactional
public class FlashDealServiceImpl implements FlashDealService {

    private final Logger log = LoggerFactory.getLogger(FlashDealServiceImpl.class);

    private final FlashDealRepository flashDealRepository;

    public FlashDealServiceImpl(FlashDealRepository flashDealRepository) {
        this.flashDealRepository = flashDealRepository;
    }

    @Override
    public FlashDeal save(FlashDeal flashDeal) {
        log.debug("Request to save FlashDeal : {}", flashDeal);
        return flashDealRepository.save(flashDeal);
    }

    @Override
    public Optional<FlashDeal> partialUpdate(FlashDeal flashDeal) {
        log.debug("Request to partially update FlashDeal : {}", flashDeal);

        return flashDealRepository
            .findById(flashDeal.getId())
            .map(existingFlashDeal -> {
                if (flashDeal.getType() != null) {
                    existingFlashDeal.setType(flashDeal.getType());
                }
                if (flashDeal.getTitle() != null) {
                    existingFlashDeal.setTitle(flashDeal.getTitle());
                }
                if (flashDeal.getDescription() != null) {
                    existingFlashDeal.setDescription(flashDeal.getDescription());
                }
                if (flashDeal.getImageUrl() != null) {
                    existingFlashDeal.setImageUrl(flashDeal.getImageUrl());
                }
                if (flashDeal.getDealUrl() != null) {
                    existingFlashDeal.setDealUrl(flashDeal.getDealUrl());
                }
                if (flashDeal.getPostedBy() != null) {
                    existingFlashDeal.setPostedBy(flashDeal.getPostedBy());
                }
                if (flashDeal.getPostedDate() != null) {
                    existingFlashDeal.setPostedDate(flashDeal.getPostedDate());
                }
                if (flashDeal.getStartDate() != null) {
                    existingFlashDeal.setStartDate(flashDeal.getStartDate());
                }
                if (flashDeal.getEndDate() != null) {
                    existingFlashDeal.setEndDate(flashDeal.getEndDate());
                }
                if (flashDeal.getOriginalPrice() != null) {
                    existingFlashDeal.setOriginalPrice(flashDeal.getOriginalPrice());
                }
                if (flashDeal.getCurrentPrice() != null) {
                    existingFlashDeal.setCurrentPrice(flashDeal.getCurrentPrice());
                }
                if (flashDeal.getDiscount() != null) {
                    existingFlashDeal.setDiscount(flashDeal.getDiscount());
                }
                if (flashDeal.getActive() != null) {
                    existingFlashDeal.setActive(flashDeal.getActive());
                }
                if (flashDeal.getPosition() != null) {
                    existingFlashDeal.setPosition(flashDeal.getPosition());
                }
                if (flashDeal.getApproved() != null) {
                    existingFlashDeal.setApproved(flashDeal.getApproved());
                }
                if (flashDeal.getCountry() != null) {
                    existingFlashDeal.setCountry(flashDeal.getCountry());
                }
                if (flashDeal.getCity() != null) {
                    existingFlashDeal.setCity(flashDeal.getCity());
                }
                if (flashDeal.getPinCode() != null) {
                    existingFlashDeal.setPinCode(flashDeal.getPinCode());
                }

                return existingFlashDeal;
            })
            .map(flashDealRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlashDeal> findAll(Pageable pageable) {
        log.debug("Request to get all FlashDeals");
        return flashDealRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlashDeal> findOne(Long id) {
        log.debug("Request to get FlashDeal : {}", id);
        return flashDealRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlashDeal : {}", id);
        flashDealRepository.deleteById(id);
    }
}
