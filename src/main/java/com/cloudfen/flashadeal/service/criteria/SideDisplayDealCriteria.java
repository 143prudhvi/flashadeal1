package com.cloudfen.flashadeal.service.criteria;

import com.cloudfen.flashadeal.domain.enumeration.DealDisplayPostion;
import com.cloudfen.flashadeal.domain.enumeration.DealType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cloudfen.flashadeal.domain.SideDisplayDeal} entity. This class is used
 * in {@link com.cloudfen.flashadeal.web.rest.SideDisplayDealResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /side-display-deals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SideDisplayDealCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DealType
     */
    public static class DealTypeFilter extends Filter<DealType> {

        public DealTypeFilter() {}

        public DealTypeFilter(DealTypeFilter filter) {
            super(filter);
        }

        @Override
        public DealTypeFilter copy() {
            return new DealTypeFilter(this);
        }
    }

    /**
     * Class for filtering DealDisplayPostion
     */
    public static class DealDisplayPostionFilter extends Filter<DealDisplayPostion> {

        public DealDisplayPostionFilter() {}

        public DealDisplayPostionFilter(DealDisplayPostionFilter filter) {
            super(filter);
        }

        @Override
        public DealDisplayPostionFilter copy() {
            return new DealDisplayPostionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DealTypeFilter type;

    private StringFilter title;

    private StringFilter postedBy;

    private StringFilter postedDate;

    private StringFilter startDate;

    private StringFilter endDate;

    private StringFilter originalPrice;

    private StringFilter currentPrice;

    private StringFilter discount;

    private StringFilter active;

    private DealDisplayPostionFilter position;

    private BooleanFilter approved;

    private StringFilter country;

    private StringFilter city;

    private StringFilter pinCode;

    private LongFilter merchantDetailsId;

    private LongFilter dealCategoryId;

    private Boolean distinct;

    public SideDisplayDealCriteria() {}

    public SideDisplayDealCriteria(SideDisplayDealCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.postedBy = other.postedBy == null ? null : other.postedBy.copy();
        this.postedDate = other.postedDate == null ? null : other.postedDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.originalPrice = other.originalPrice == null ? null : other.originalPrice.copy();
        this.currentPrice = other.currentPrice == null ? null : other.currentPrice.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.merchantDetailsId = other.merchantDetailsId == null ? null : other.merchantDetailsId.copy();
        this.dealCategoryId = other.dealCategoryId == null ? null : other.dealCategoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SideDisplayDealCriteria copy() {
        return new SideDisplayDealCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DealTypeFilter getType() {
        return type;
    }

    public DealTypeFilter type() {
        if (type == null) {
            type = new DealTypeFilter();
        }
        return type;
    }

    public void setType(DealTypeFilter type) {
        this.type = type;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getPostedBy() {
        return postedBy;
    }

    public StringFilter postedBy() {
        if (postedBy == null) {
            postedBy = new StringFilter();
        }
        return postedBy;
    }

    public void setPostedBy(StringFilter postedBy) {
        this.postedBy = postedBy;
    }

    public StringFilter getPostedDate() {
        return postedDate;
    }

    public StringFilter postedDate() {
        if (postedDate == null) {
            postedDate = new StringFilter();
        }
        return postedDate;
    }

    public void setPostedDate(StringFilter postedDate) {
        this.postedDate = postedDate;
    }

    public StringFilter getStartDate() {
        return startDate;
    }

    public StringFilter startDate() {
        if (startDate == null) {
            startDate = new StringFilter();
        }
        return startDate;
    }

    public void setStartDate(StringFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getEndDate() {
        return endDate;
    }

    public StringFilter endDate() {
        if (endDate == null) {
            endDate = new StringFilter();
        }
        return endDate;
    }

    public void setEndDate(StringFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getOriginalPrice() {
        return originalPrice;
    }

    public StringFilter originalPrice() {
        if (originalPrice == null) {
            originalPrice = new StringFilter();
        }
        return originalPrice;
    }

    public void setOriginalPrice(StringFilter originalPrice) {
        this.originalPrice = originalPrice;
    }

    public StringFilter getCurrentPrice() {
        return currentPrice;
    }

    public StringFilter currentPrice() {
        if (currentPrice == null) {
            currentPrice = new StringFilter();
        }
        return currentPrice;
    }

    public void setCurrentPrice(StringFilter currentPrice) {
        this.currentPrice = currentPrice;
    }

    public StringFilter getDiscount() {
        return discount;
    }

    public StringFilter discount() {
        if (discount == null) {
            discount = new StringFilter();
        }
        return discount;
    }

    public void setDiscount(StringFilter discount) {
        this.discount = discount;
    }

    public StringFilter getActive() {
        return active;
    }

    public StringFilter active() {
        if (active == null) {
            active = new StringFilter();
        }
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public DealDisplayPostionFilter getPosition() {
        return position;
    }

    public DealDisplayPostionFilter position() {
        if (position == null) {
            position = new DealDisplayPostionFilter();
        }
        return position;
    }

    public void setPosition(DealDisplayPostionFilter position) {
        this.position = position;
    }

    public BooleanFilter getApproved() {
        return approved;
    }

    public BooleanFilter approved() {
        if (approved == null) {
            approved = new BooleanFilter();
        }
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public StringFilter pinCode() {
        if (pinCode == null) {
            pinCode = new StringFilter();
        }
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public LongFilter getMerchantDetailsId() {
        return merchantDetailsId;
    }

    public LongFilter merchantDetailsId() {
        if (merchantDetailsId == null) {
            merchantDetailsId = new LongFilter();
        }
        return merchantDetailsId;
    }

    public void setMerchantDetailsId(LongFilter merchantDetailsId) {
        this.merchantDetailsId = merchantDetailsId;
    }

    public LongFilter getDealCategoryId() {
        return dealCategoryId;
    }

    public LongFilter dealCategoryId() {
        if (dealCategoryId == null) {
            dealCategoryId = new LongFilter();
        }
        return dealCategoryId;
    }

    public void setDealCategoryId(LongFilter dealCategoryId) {
        this.dealCategoryId = dealCategoryId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SideDisplayDealCriteria that = (SideDisplayDealCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(title, that.title) &&
            Objects.equals(postedBy, that.postedBy) &&
            Objects.equals(postedDate, that.postedDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(originalPrice, that.originalPrice) &&
            Objects.equals(currentPrice, that.currentPrice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(active, that.active) &&
            Objects.equals(position, that.position) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(country, that.country) &&
            Objects.equals(city, that.city) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(merchantDetailsId, that.merchantDetailsId) &&
            Objects.equals(dealCategoryId, that.dealCategoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            title,
            postedBy,
            postedDate,
            startDate,
            endDate,
            originalPrice,
            currentPrice,
            discount,
            active,
            position,
            approved,
            country,
            city,
            pinCode,
            merchantDetailsId,
            dealCategoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SideDisplayDealCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (postedBy != null ? "postedBy=" + postedBy + ", " : "") +
            (postedDate != null ? "postedDate=" + postedDate + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (originalPrice != null ? "originalPrice=" + originalPrice + ", " : "") +
            (currentPrice != null ? "currentPrice=" + currentPrice + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (approved != null ? "approved=" + approved + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
            (merchantDetailsId != null ? "merchantDetailsId=" + merchantDetailsId + ", " : "") +
            (dealCategoryId != null ? "dealCategoryId=" + dealCategoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
