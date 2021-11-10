package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.*; // for static metamodels
import com.cloudfen.flashadeal.domain.FlashDeal;
import com.cloudfen.flashadeal.repository.FlashDealRepository;
import com.cloudfen.flashadeal.service.criteria.FlashDealCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FlashDeal} entities in the database.
 * The main input is a {@link FlashDealCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FlashDeal} or a {@link Page} of {@link FlashDeal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FlashDealQueryService extends QueryService<FlashDeal> {

    private final Logger log = LoggerFactory.getLogger(FlashDealQueryService.class);

    private final FlashDealRepository flashDealRepository;

    public FlashDealQueryService(FlashDealRepository flashDealRepository) {
        this.flashDealRepository = flashDealRepository;
    }

    /**
     * Return a {@link List} of {@link FlashDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FlashDeal> findByCriteria(FlashDealCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FlashDeal> specification = createSpecification(criteria);
        return flashDealRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FlashDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FlashDeal> findByCriteria(FlashDealCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FlashDeal> specification = createSpecification(criteria);
        return flashDealRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FlashDealCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FlashDeal> specification = createSpecification(criteria);
        return flashDealRepository.count(specification);
    }

    /**
     * Function to convert {@link FlashDealCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FlashDeal> createSpecification(FlashDealCriteria criteria) {
        Specification<FlashDeal> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FlashDeal_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), FlashDeal_.type));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), FlashDeal_.title));
            }
            if (criteria.getPostedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedBy(), FlashDeal_.postedBy));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedDate(), FlashDeal_.postedDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDate(), FlashDeal_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDate(), FlashDeal_.endDate));
            }
            if (criteria.getOriginalPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalPrice(), FlashDeal_.originalPrice));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentPrice(), FlashDeal_.currentPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscount(), FlashDeal_.discount));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), FlashDeal_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getPosition(), FlashDeal_.position));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), FlashDeal_.approved));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), FlashDeal_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), FlashDeal_.city));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), FlashDeal_.pinCode));
            }
            if (criteria.getMerchantDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMerchantDetailsId(),
                            root -> root.join(FlashDeal_.merchantDetails, JoinType.LEFT).get(MerchantDetails_.id)
                        )
                    );
            }
            if (criteria.getDealCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealCategoryId(),
                            root -> root.join(FlashDeal_.dealCategory, JoinType.LEFT).get(DealCategory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
