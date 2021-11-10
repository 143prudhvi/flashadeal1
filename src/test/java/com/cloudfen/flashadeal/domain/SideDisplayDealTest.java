package com.cloudfen.flashadeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cloudfen.flashadeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SideDisplayDealTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SideDisplayDeal.class);
        SideDisplayDeal sideDisplayDeal1 = new SideDisplayDeal();
        sideDisplayDeal1.setId(1L);
        SideDisplayDeal sideDisplayDeal2 = new SideDisplayDeal();
        sideDisplayDeal2.setId(sideDisplayDeal1.getId());
        assertThat(sideDisplayDeal1).isEqualTo(sideDisplayDeal2);
        sideDisplayDeal2.setId(2L);
        assertThat(sideDisplayDeal1).isNotEqualTo(sideDisplayDeal2);
        sideDisplayDeal1.setId(null);
        assertThat(sideDisplayDeal1).isNotEqualTo(sideDisplayDeal2);
    }
}
