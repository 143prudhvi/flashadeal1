package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.*; // for static metamodels
import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import com.cloudfen.flashadeal.repository.SideDisplayDealRepository;
import com.cloudfen.flashadeal.service.criteria.SideDisplayDealCriteria;
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
 * Service for executing complex queries for {@link SideDisplayDeal} entities in the database.
 * The main input is a {@link SideDisplayDealCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SideDisplayDeal} or a {@link Page} of {@link SideDisplayDeal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SideDisplayDealQueryService extends QueryService<SideDisplayDeal> {

    private final Logger log = LoggerFactory.getLogger(SideDisplayDealQueryService.class);

    private final SideDisplayDealRepository sideDisplayDealRepository;

    public SideDisplayDealQueryService(SideDisplayDealRepository sideDisplayDealRepository) {
        this.sideDisplayDealRepository = sideDisplayDealRepository;
    }

    /**
     * Return a {@link List} of {@link SideDisplayDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SideDisplayDeal> findByCriteria(SideDisplayDealCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SideDisplayDeal> specification = createSpecification(criteria);
        return sideDisplayDealRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SideDisplayDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SideDisplayDeal> findByCriteria(SideDisplayDealCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SideDisplayDeal> specification = createSpecification(criteria);
        return sideDisplayDealRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SideDisplayDealCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SideDisplayDeal> specification = createSpecification(criteria);
        return sideDisplayDealRepository.count(specification);
    }

    /**
     * Function to convert {@link SideDisplayDealCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SideDisplayDeal> createSpecification(SideDisplayDealCriteria criteria) {
        Specification<SideDisplayDeal> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SideDisplayDeal_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SideDisplayDeal_.type));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), SideDisplayDeal_.title));
            }
            if (criteria.getPostedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedBy(), SideDisplayDeal_.postedBy));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedDate(), SideDisplayDeal_.postedDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDate(), SideDisplayDeal_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDate(), SideDisplayDeal_.endDate));
            }
            if (criteria.getOriginalPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalPrice(), SideDisplayDeal_.originalPrice));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentPrice(), SideDisplayDeal_.currentPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscount(), SideDisplayDeal_.discount));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), SideDisplayDeal_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getPosition(), SideDisplayDeal_.position));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), SideDisplayDeal_.approved));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), SideDisplayDeal_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), SideDisplayDeal_.city));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), SideDisplayDeal_.pinCode));
            }
            if (criteria.getMerchantDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMerchantDetailsId(),
                            root -> root.join(SideDisplayDeal_.merchantDetails, JoinType.LEFT).get(MerchantDetails_.id)
                        )
                    );
            }
            if (criteria.getDealCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealCategoryId(),
                            root -> root.join(SideDisplayDeal_.dealCategory, JoinType.LEFT).get(DealCategory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
