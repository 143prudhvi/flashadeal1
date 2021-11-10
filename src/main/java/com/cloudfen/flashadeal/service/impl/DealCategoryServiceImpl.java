package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.repository.DealCategoryRepository;
import com.cloudfen.flashadeal.service.DealCategoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealCategory}.
 */
@Service
@Transactional
public class DealCategoryServiceImpl implements DealCategoryService {

    private final Logger log = LoggerFactory.getLogger(DealCategoryServiceImpl.class);

    private final DealCategoryRepository dealCategoryRepository;

    public DealCategoryServiceImpl(DealCategoryRepository dealCategoryRepository) {
        this.dealCategoryRepository = dealCategoryRepository;
    }

    @Override
    public DealCategory save(DealCategory dealCategory) {
        log.debug("Request to save DealCategory : {}", dealCategory);
        return dealCategoryRepository.save(dealCategory);
    }

    @Override
    public Optional<DealCategory> partialUpdate(DealCategory dealCategory) {
        log.debug("Request to partially update DealCategory : {}", dealCategory);

        return dealCategoryRepository
            .findById(dealCategory.getId())
            .map(existingDealCategory -> {
                if (dealCategory.getName() != null) {
                    existingDealCategory.setName(dealCategory.getName());
                }
                if (dealCategory.getIcon() != null) {
                    existingDealCategory.setIcon(dealCategory.getIcon());
                }
                if (dealCategory.getDescription() != null) {
                    existingDealCategory.setDescription(dealCategory.getDescription());
                }

                return existingDealCategory;
            })
            .map(dealCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DealCategory> findAll() {
        log.debug("Request to get all DealCategories");
        return dealCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DealCategory> findOne(Long id) {
        log.debug("Request to get DealCategory : {}", id);
        return dealCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DealCategory : {}", id);
        dealCategoryRepository.deleteById(id);
    }
}
