package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import com.cloudfen.flashadeal.repository.DbCarouselDealRepository;
import com.cloudfen.flashadeal.service.DbCarouselDealService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DbCarouselDeal}.
 */
@Service
@Transactional
public class DbCarouselDealServiceImpl implements DbCarouselDealService {

    private final Logger log = LoggerFactory.getLogger(DbCarouselDealServiceImpl.class);

    private final DbCarouselDealRepository dbCarouselDealRepository;

    public DbCarouselDealServiceImpl(DbCarouselDealRepository dbCarouselDealRepository) {
        this.dbCarouselDealRepository = dbCarouselDealRepository;
    }

    @Override
    public DbCarouselDeal save(DbCarouselDeal dbCarouselDeal) {
        log.debug("Request to save DbCarouselDeal : {}", dbCarouselDeal);
        return dbCarouselDealRepository.save(dbCarouselDeal);
    }

    @Override
    public Optional<DbCarouselDeal> partialUpdate(DbCarouselDeal dbCarouselDeal) {
        log.debug("Request to partially update DbCarouselDeal : {}", dbCarouselDeal);

        return dbCarouselDealRepository
            .findById(dbCarouselDeal.getId())
            .map(existingDbCarouselDeal -> {
                if (dbCarouselDeal.getType() != null) {
                    existingDbCarouselDeal.setType(dbCarouselDeal.getType());
                }
                if (dbCarouselDeal.getTitle() != null) {
                    existingDbCarouselDeal.setTitle(dbCarouselDeal.getTitle());
                }
                if (dbCarouselDeal.getDescription() != null) {
                    existingDbCarouselDeal.setDescription(dbCarouselDeal.getDescription());
                }
                if (dbCarouselDeal.getImageUrl() != null) {
                    existingDbCarouselDeal.setImageUrl(dbCarouselDeal.getImageUrl());
                }
                if (dbCarouselDeal.getDealUrl() != null) {
                    existingDbCarouselDeal.setDealUrl(dbCarouselDeal.getDealUrl());
                }
                if (dbCarouselDeal.getPostedBy() != null) {
                    existingDbCarouselDeal.setPostedBy(dbCarouselDeal.getPostedBy());
                }
                if (dbCarouselDeal.getPostedDate() != null) {
                    existingDbCarouselDeal.setPostedDate(dbCarouselDeal.getPostedDate());
                }
                if (dbCarouselDeal.getStartDate() != null) {
                    existingDbCarouselDeal.setStartDate(dbCarouselDeal.getStartDate());
                }
                if (dbCarouselDeal.getEndDate() != null) {
                    existingDbCarouselDeal.setEndDate(dbCarouselDeal.getEndDate());
                }
                if (dbCarouselDeal.getOriginalPrice() != null) {
                    existingDbCarouselDeal.setOriginalPrice(dbCarouselDeal.getOriginalPrice());
                }
                if (dbCarouselDeal.getCurrentPrice() != null) {
                    existingDbCarouselDeal.setCurrentPrice(dbCarouselDeal.getCurrentPrice());
                }
                if (dbCarouselDeal.getDiscount() != null) {
                    existingDbCarouselDeal.setDiscount(dbCarouselDeal.getDiscount());
                }
                if (dbCarouselDeal.getActive() != null) {
                    existingDbCarouselDeal.setActive(dbCarouselDeal.getActive());
                }
                if (dbCarouselDeal.getPosition() != null) {
                    existingDbCarouselDeal.setPosition(dbCarouselDeal.getPosition());
                }
                if (dbCarouselDeal.getApproved() != null) {
                    existingDbCarouselDeal.setApproved(dbCarouselDeal.getApproved());
                }
                if (dbCarouselDeal.getCountry() != null) {
                    existingDbCarouselDeal.setCountry(dbCarouselDeal.getCountry());
                }
                if (dbCarouselDeal.getCity() != null) {
                    existingDbCarouselDeal.setCity(dbCarouselDeal.getCity());
                }
                if (dbCarouselDeal.getPinCode() != null) {
                    existingDbCarouselDeal.setPinCode(dbCarouselDeal.getPinCode());
                }

                return existingDbCarouselDeal;
            })
            .map(dbCarouselDealRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DbCarouselDeal> findAll(Pageable pageable) {
        log.debug("Request to get all DbCarouselDeals");
        return dbCarouselDealRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DbCarouselDeal> findOne(Long id) {
        log.debug("Request to get DbCarouselDeal : {}", id);
        return dbCarouselDealRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DbCarouselDeal : {}", id);
        dbCarouselDealRepository.deleteById(id);
    }
}
