package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealCategory.class);
        DealCategory dealCategory1 = new DealCategory();
        dealCategory1.setId(1L);
        DealCategory dealCategory2 = new DealCategory();
        dealCategory2.setId(dealCategory1.getId());
        assertThat(dealCategory1).isEqualTo(dealCategory2);
        dealCategory2.setId(2L);
        assertThat(dealCategory1).isNotEqualTo(dealCategory2);
        dealCategory1.setId(null);
        assertThat(dealCategory1).isNotEqualTo(dealCategory2);
    }
}
