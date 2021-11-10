package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealUserRelationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealUserRelation.class);
        DealUserRelation dealUserRelation1 = new DealUserRelation();
        dealUserRelation1.setId(1L);
        DealUserRelation dealUserRelation2 = new DealUserRelation();
        dealUserRelation2.setId(dealUserRelation1.getId());
        assertThat(dealUserRelation1).isEqualTo(dealUserRelation2);
        dealUserRelation2.setId(2L);
        assertThat(dealUserRelation1).isNotEqualTo(dealUserRelation2);
        dealUserRelation1.setId(null);
        assertThat(dealUserRelation1).isNotEqualTo(dealUserRelation2);
    }
}
