package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MerchantDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantDetails.class);
        MerchantDetails merchantDetails1 = new MerchantDetails();
        merchantDetails1.setId(1L);
        MerchantDetails merchantDetails2 = new MerchantDetails();
        merchantDetails2.setId(merchantDetails1.getId());
        assertThat(merchantDetails1).isEqualTo(merchantDetails2);
        merchantDetails2.setId(2L);
        assertThat(merchantDetails1).isNotEqualTo(merchantDetails2);
        merchantDetails1.setId(null);
        assertThat(merchantDetails1).isNotEqualTo(merchantDetails2);
    }
}
