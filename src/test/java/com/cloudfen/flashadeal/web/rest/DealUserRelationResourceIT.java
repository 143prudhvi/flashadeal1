package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.DealUserRelation;
import com.cloudfen.flashadeal.repository.DealUserRelationRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DealUserRelationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealUserRelationResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deal-user-relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealUserRelationRepository dealUserRelationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealUserRelationMockMvc;

    private DealUserRelation dealUserRelation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealUserRelation createEntity(EntityManager em) {
        DealUserRelation dealUserRelation = new DealUserRelation().userId(DEFAULT_USER_ID).dealId(DEFAULT_DEAL_ID);
        return dealUserRelation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealUserRelation createUpdatedEntity(EntityManager em) {
        DealUserRelation dealUserRelation = new DealUserRelation().userId(UPDATED_USER_ID).dealId(UPDATED_DEAL_ID);
        return dealUserRelation;
    }

    @BeforeEach
    public void initTest() {
        dealUserRelation = createEntity(em);
    }

    @Test
    @Transactional
    void createDealUserRelation() throws Exception {
        int databaseSizeBeforeCreate = dealUserRelationRepository.findAll().size();
        // Create the DealUserRelation
        restDealUserRelationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isCreated());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeCreate + 1);
        DealUserRelation testDealUserRelation = dealUserRelationList.get(dealUserRelationList.size() - 1);
        assertThat(testDealUserRelation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDealUserRelation.getDealId()).isEqualTo(DEFAULT_DEAL_ID);
    }

    @Test
    @Transactional
    void createDealUserRelationWithExistingId() throws Exception {
        // Create the DealUserRelation with an existing ID
        dealUserRelation.setId(1L);

        int databaseSizeBeforeCreate = dealUserRelationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealUserRelationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDealUserRelations() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        // Get all the dealUserRelationList
        restDealUserRelationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealUserRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].dealId").value(hasItem(DEFAULT_DEAL_ID)));
    }

    @Test
    @Transactional
    void getDealUserRelation() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        // Get the dealUserRelation
        restDealUserRelationMockMvc
            .perform(get(ENTITY_API_URL_ID, dealUserRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealUserRelation.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.dealId").value(DEFAULT_DEAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingDealUserRelation() throws Exception {
        // Get the dealUserRelation
        restDealUserRelationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDealUserRelation() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();

        // Update the dealUserRelation
        DealUserRelation updatedDealUserRelation = dealUserRelationRepository.findById(dealUserRelation.getId()).get();
        // Disconnect from session so that the updates on updatedDealUserRelation are not directly saved in db
        em.detach(updatedDealUserRelation);
        updatedDealUserRelation.userId(UPDATED_USER_ID).dealId(UPDATED_DEAL_ID);

        restDealUserRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealUserRelation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealUserRelation))
            )
            .andExpect(status().isOk());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
        DealUserRelation testDealUserRelation = dealUserRelationList.get(dealUserRelationList.size() - 1);
        assertThat(testDealUserRelation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDealUserRelation.getDealId()).isEqualTo(UPDATED_DEAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealUserRelation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealUserRelationWithPatch() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();

        // Update the dealUserRelation using partial update
        DealUserRelation partialUpdatedDealUserRelation = new DealUserRelation();
        partialUpdatedDealUserRelation.setId(dealUserRelation.getId());

        partialUpdatedDealUserRelation.userId(UPDATED_USER_ID);

        restDealUserRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealUserRelation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealUserRelation))
            )
            .andExpect(status().isOk());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
        DealUserRelation testDealUserRelation = dealUserRelationList.get(dealUserRelationList.size() - 1);
        assertThat(testDealUserRelation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDealUserRelation.getDealId()).isEqualTo(DEFAULT_DEAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateDealUserRelationWithPatch() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();

        // Update the dealUserRelation using partial update
        DealUserRelation partialUpdatedDealUserRelation = new DealUserRelation();
        partialUpdatedDealUserRelation.setId(dealUserRelation.getId());

        partialUpdatedDealUserRelation.userId(UPDATED_USER_ID).dealId(UPDATED_DEAL_ID);

        restDealUserRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealUserRelation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealUserRelation))
            )
            .andExpect(status().isOk());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
        DealUserRelation testDealUserRelation = dealUserRelationList.get(dealUserRelationList.size() - 1);
        assertThat(testDealUserRelation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDealUserRelation.getDealId()).isEqualTo(UPDATED_DEAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealUserRelation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealUserRelation() throws Exception {
        int databaseSizeBeforeUpdate = dealUserRelationRepository.findAll().size();
        dealUserRelation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealUserRelationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealUserRelation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealUserRelation in the database
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealUserRelation() throws Exception {
        // Initialize the database
        dealUserRelationRepository.saveAndFlush(dealUserRelation);

        int databaseSizeBeforeDelete = dealUserRelationRepository.findAll().size();

        // Delete the dealUserRelation
        restDealUserRelationMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealUserRelation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealUserRelation> dealUserRelationList = dealUserRelationRepository.findAll();
        assertThat(dealUserRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
