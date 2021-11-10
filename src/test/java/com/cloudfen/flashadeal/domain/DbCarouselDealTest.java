package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DbCarouselDealTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DbCarouselDeal.class);
        DbCarouselDeal dbCarouselDeal1 = new DbCarouselDeal();
        dbCarouselDeal1.setId(1L);
        DbCarouselDeal dbCarouselDeal2 = new DbCarouselDeal();
        dbCarouselDeal2.setId(dbCarouselDeal1.getId());
        assertThat(dbCarouselDeal1).isEqualTo(dbCarouselDeal2);
        dbCarouselDeal2.setId(2L);
        assertThat(dbCarouselDeal1).isNotEqualTo(dbCarouselDeal2);
        dbCarouselDeal1.setId(null);
        assertThat(dbCarouselDeal1).isNotEqualTo(dbCarouselDeal2);
    }
}
