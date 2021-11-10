package com.cloudfen.flashadeal.service.impl;

import com.cloudfen.flashadeal.domain.DealUserRelation;
import com.cloudfen.flashadeal.repository.DealUserRelationRepository;
import com.cloudfen.flashadeal.service.DealUserRelationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealUserRelation}.
 */
@Service
@Transactional
public class DealUserRelationServiceImpl implements DealUserRelationService {

    private final Logger log = LoggerFactory.getLogger(DealUserRelationServiceImpl.class);

    private final DealUserRelationRepository dealUserRelationRepository;

    public DealUserRelationServiceImpl(DealUserRelationRepository dealUserRelationRepository) {
        this.dealUserRelationRepository = dealUserRelationRepository;
    }

    @Override
    public DealUserRelation save(DealUserRelation dealUserRelation) {
        log.debug("Request to save DealUserRelation : {}", dealUserRelation);
        return dealUserRelationRepository.save(dealUserRelation);
    }

    @Override
    public Optional<DealUserRelation> partialUpdate(DealUserRelation dealUserRelation) {
        log.debug("Request to partially update DealUserRelation : {}", dealUserRelation);

        return dealUserRelationRepository
            .findById(dealUserRelation.getId())
            .map(existingDealUserRelation -> {
                if (dealUserRelation.getUserId() != null) {
                    existingDealUserRelation.setUserId(dealUserRelation.getUserId());
                }
                if (dealUserRelation.getDealId() != null) {
                    existingDealUserRelation.setDealId(dealUserRelation.getDealId());
                }

                return existingDealUserRelation;
            })
            .map(dealUserRelationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DealUserRelation> findAll() {
        log.debug("Request to get all DealUserRelations");
        return dealUserRelationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DealUserRelation> findOne(Long id) {
        log.debug("Request to get DealUserRelation : {}", id);
        return dealUserRelationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DealUserRelation : {}", id);
        dealUserRelationRepository.deleteById(id);
    }
}
