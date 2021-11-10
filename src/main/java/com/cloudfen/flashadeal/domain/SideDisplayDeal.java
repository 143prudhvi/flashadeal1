package com.cloudfen.flashadeal.domain;

import com.cloudfen.flashadeal.domain.enumeration.DealDisplayPostion;
import com.cloudfen.flashadeal.domain.enumeration.DealType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SideDisplayDeal.
 */
@Entity
@Table(name = "side_display_deal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SideDisplayDeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DealType type;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Lob
    @Column(name = "deal_url")
    private String dealUrl;

    @Column(name = "posted_by")
    private String postedBy;

    @Column(name = "posted_date")
    private String postedDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "original_price")
    private String originalPrice;

    @Column(name = "current_price")
    private String currentPrice;

    @Column(name = "discount")
    private String discount;

    @Column(name = "active")
    private String active;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private DealDisplayPostion position;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "pin_code")
    private String pinCode;

    @ManyToOne
    private MerchantDetails merchantDetails;

    @ManyToOne
    private DealCategory dealCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SideDisplayDeal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DealType getType() {
        return this.type;
    }

    public SideDisplayDeal type(DealType type) {
        this.setType(type);
        return this;
    }

    public void setType(DealType type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public SideDisplayDeal title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public SideDisplayDeal description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public SideDisplayDeal imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDealUrl() {
        return this.dealUrl;
    }

    public SideDisplayDeal dealUrl(String dealUrl) {
        this.setDealUrl(dealUrl);
        return this;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public String getPostedBy() {
        return this.postedBy;
    }

    public SideDisplayDeal postedBy(String postedBy) {
        this.setPostedBy(postedBy);
        return this;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostedDate() {
        return this.postedDate;
    }

    public SideDisplayDeal postedDate(String postedDate) {
        this.setPostedDate(postedDate);
        return this;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public SideDisplayDeal startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public SideDisplayDeal endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOriginalPrice() {
        return this.originalPrice;
    }

    public SideDisplayDeal originalPrice(String originalPrice) {
        this.setOriginalPrice(originalPrice);
        return this;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public SideDisplayDeal currentPrice(String currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDiscount() {
        return this.discount;
    }

    public SideDisplayDeal discount(String discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getActive() {
        return this.active;
    }

    public SideDisplayDeal active(String active) {
        this.setActive(active);
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public DealDisplayPostion getPosition() {
        return this.position;
    }

    public SideDisplayDeal position(DealDisplayPostion position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(DealDisplayPostion position) {
        this.position = position;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public SideDisplayDeal approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getCountry() {
        return this.country;
    }

    public SideDisplayDeal country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public SideDisplayDeal city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public SideDisplayDeal pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public MerchantDetails getMerchantDetails() {
        return this.merchantDetails;
    }

    public void setMerchantDetails(MerchantDetails merchantDetails) {
        this.merchantDetails = merchantDetails;
    }

    public SideDisplayDeal merchantDetails(MerchantDetails merchantDetails) {
        this.setMerchantDetails(merchantDetails);
        return this;
    }

    public DealCategory getDealCategory() {
        return this.dealCategory;
    }

    public void setDealCategory(DealCategory dealCategory) {
        this.dealCategory = dealCategory;
    }

    public SideDisplayDeal dealCategory(DealCategory dealCategory) {
        this.setDealCategory(dealCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SideDisplayDeal)) {
            return false;
        }
        return id != null && id.equals(((SideDisplayDeal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SideDisplayDeal{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", dealUrl='" + getDealUrl() + "'" +
            ", postedBy='" + getPostedBy() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", originalPrice='" + getOriginalPrice() + "'" +
            ", currentPrice='" + getCurrentPrice() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", active='" + getActive() + "'" +
            ", position='" + getPosition() + "'" +
            ", approved='" + getApproved() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            "}";
    }
}
