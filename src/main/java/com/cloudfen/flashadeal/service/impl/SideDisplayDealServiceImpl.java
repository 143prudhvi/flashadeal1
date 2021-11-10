package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import com.cloudfen.flashadeal.repository.SideDisplayDealRepository;
import com.cloudfen.flashadeal.service.SideDisplayDealService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SideDisplayDeal}.
 */
@Service
@Transactional
public class SideDisplayDealServiceImpl implements SideDisplayDealService {

    private final Logger log = LoggerFactory.getLogger(SideDisplayDealServiceImpl.class);

    private final SideDisplayDealRepository sideDisplayDealRepository;

    public SideDisplayDealServiceImpl(SideDisplayDealRepository sideDisplayDealRepository) {
        this.sideDisplayDealRepository = sideDisplayDealRepository;
    }

    @Override
    public SideDisplayDeal save(SideDisplayDeal sideDisplayDeal) {
        log.debug("Request to save SideDisplayDeal : {}", sideDisplayDeal);
        return sideDisplayDealRepository.save(sideDisplayDeal);
    }

    @Override
    public Optional<SideDisplayDeal> partialUpdate(SideDisplayDeal sideDisplayDeal) {
        log.debug("Request to partially update SideDisplayDeal : {}", sideDisplayDeal);

        return sideDisplayDealRepository
            .findById(sideDisplayDeal.getId())
            .map(existingSideDisplayDeal -> {
                if (sideDisplayDeal.getType() != null) {
                    existingSideDisplayDeal.setType(sideDisplayDeal.getType());
                }
                if (sideDisplayDeal.getTitle() != null) {
                    existingSideDisplayDeal.setTitle(sideDisplayDeal.getTitle());
                }
                if (sideDisplayDeal.getDescription() != null) {
                    existingSideDisplayDeal.setDescription(sideDisplayDeal.getDescription());
                }
                if (sideDisplayDeal.getImageUrl() != null) {
                    existingSideDisplayDeal.setImageUrl(sideDisplayDeal.getImageUrl());
                }
                if (sideDisplayDeal.getDealUrl() != null) {
                    existingSideDisplayDeal.setDealUrl(sideDisplayDeal.getDealUrl());
                }
                if (sideDisplayDeal.getPostedBy() != null) {
                    existingSideDisplayDeal.setPostedBy(sideDisplayDeal.getPostedBy());
                }
                if (sideDisplayDeal.getPostedDate() != null) {
                    existingSideDisplayDeal.setPostedDate(sideDisplayDeal.getPostedDate());
                }
                if (sideDisplayDeal.getStartDate() != null) {
                    existingSideDisplayDeal.setStartDate(sideDisplayDeal.getStartDate());
                }
                if (sideDisplayDeal.getEndDate() != null) {
                    existingSideDisplayDeal.setEndDate(sideDisplayDeal.getEndDate());
                }
                if (sideDisplayDeal.getOriginalPrice() != null) {
                    existingSideDisplayDeal.setOriginalPrice(sideDisplayDeal.getOriginalPrice());
                }
                if (sideDisplayDeal.getCurrentPrice() != null) {
                    existingSideDisplayDeal.setCurrentPrice(sideDisplayDeal.getCurrentPrice());
                }
                if (sideDisplayDeal.getDiscount() != null) {
                    existingSideDisplayDeal.setDiscount(sideDisplayDeal.getDiscount());
                }
                if (sideDisplayDeal.getActive() != null) {
                    existingSideDisplayDeal.setActive(sideDisplayDeal.getActive());
                }
                if (sideDisplayDeal.getPosition() != null) {
                    existingSideDisplayDeal.setPosition(sideDisplayDeal.getPosition());
                }
                if (sideDisplayDeal.getApproved() != null) {
                    existingSideDisplayDeal.setApproved(sideDisplayDeal.getApproved());
                }
                if (sideDisplayDeal.getCountry() != null) {
                    existingSideDisplayDeal.setCountry(sideDisplayDeal.getCountry());
                }
                if (sideDisplayDeal.getCity() != null) {
                    existingSideDisplayDeal.setCity(sideDisplayDeal.getCity());
                }
                if (sideDisplayDeal.getPinCode() != null) {
                    existingSideDisplayDeal.setPinCode(sideDisplayDeal.getPinCode());
                }

                return existingSideDisplayDeal;
            })
            .map(sideDisplayDealRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SideDisplayDeal> findAll(Pageable pageable) {
        log.debug("Request to get all SideDisplayDeals");
        return sideDisplayDealRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SideDisplayDeal> findOne(Long id) {
        log.debug("Request to get SideDisplayDeal : {}", id);
        return sideDisplayDealRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SideDisplayDeal : {}", id);
        sideDisplayDealRepository.deleteById(id);
    }
}
