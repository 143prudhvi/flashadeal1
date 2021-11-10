package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlashDealTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlashDeal.class);
        FlashDeal flashDeal1 = new FlashDeal();
        flashDeal1.setId(1L);
        FlashDeal flashDeal2 = new FlashDeal();
        flashDeal2.setId(flashDeal1.getId());
        assertThat(flashDeal1).isEqualTo(flashDeal2);
        flashDeal2.setId(2L);
        assertThat(flashDeal1).isNotEqualTo(flashDeal2);
        flashDeal1.setId(null);
        assertThat(flashDeal1).isNotEqualTo(flashDeal2);
    }
}
