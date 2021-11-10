package com.cloudfen.flashadeal.service;

import com.cloudfen.flashadeal.domain.*; // for static metamodels
import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import com.cloudfen.flashadeal.repository.DbCarouselDealRepository;
import com.cloudfen.flashadeal.service.criteria.DbCarouselDealCriteria;
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
 * Service for executing complex queries for {@link DbCarouselDeal} entities in the database.
 * The main input is a {@link DbCarouselDealCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DbCarouselDeal} or a {@link Page} of {@link DbCarouselDeal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DbCarouselDealQueryService extends QueryService<DbCarouselDeal> {

    private final Logger log = LoggerFactory.getLogger(DbCarouselDealQueryService.class);

    private final DbCarouselDealRepository dbCarouselDealRepository;

    public DbCarouselDealQueryService(DbCarouselDealRepository dbCarouselDealRepository) {
        this.dbCarouselDealRepository = dbCarouselDealRepository;
    }

    /**
     * Return a {@link List} of {@link DbCarouselDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DbCarouselDeal> findByCriteria(DbCarouselDealCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DbCarouselDeal> specification = createSpecification(criteria);
        return dbCarouselDealRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DbCarouselDeal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DbCarouselDeal> findByCriteria(DbCarouselDealCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DbCarouselDeal> specification = createSpecification(criteria);
        return dbCarouselDealRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DbCarouselDealCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DbCarouselDeal> specification = createSpecification(criteria);
        return dbCarouselDealRepository.count(specification);
    }

    /**
     * Function to convert {@link DbCarouselDealCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DbCarouselDeal> createSpecification(DbCarouselDealCriteria criteria) {
        Specification<DbCarouselDeal> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DbCarouselDeal_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), DbCarouselDeal_.type));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), DbCarouselDeal_.title));
            }
            if (criteria.getPostedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedBy(), DbCarouselDeal_.postedBy));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedDate(), DbCarouselDeal_.postedDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDate(), DbCarouselDeal_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDate(), DbCarouselDeal_.endDate));
            }
            if (criteria.getOriginalPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalPrice(), DbCarouselDeal_.originalPrice));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentPrice(), DbCarouselDeal_.currentPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscount(), DbCarouselDeal_.discount));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), DbCarouselDeal_.active));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildSpecification(criteria.getPosition(), DbCarouselDeal_.position));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), DbCarouselDeal_.approved));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), DbCarouselDeal_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), DbCarouselDeal_.city));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), DbCarouselDeal_.pinCode));
            }
            if (criteria.getMerchantDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMerchantDetailsId(),
                            root -> root.join(DbCarouselDeal_.merchantDetails, JoinType.LEFT).get(MerchantDetails_.id)
                        )
                    );
            }
            if (criteria.getDealCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDealCategoryId(),
                            root -> root.join(DbCarouselDeal_.dealCategory, JoinType.LEFT).get(DealCategory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
