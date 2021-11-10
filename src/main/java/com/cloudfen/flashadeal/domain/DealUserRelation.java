package com.cloudfen.flashadeal.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DealUserRelation.
 */
@Entity
@Table(name = "deal_user_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DealUserRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "deal_id")
    private String dealId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealUserRelation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public DealUserRelation userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealId() {
        return this.dealId;
    }

    public DealUserRelation dealId(String dealId) {
        this.setDealId(dealId);
        return this;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealUserRelation)) {
            return false;
        }
        return id != null && id.equals(((DealUserRelation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealUserRelation{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", dealId='" + getDealId() + "'" +
            "}";
    }
}
