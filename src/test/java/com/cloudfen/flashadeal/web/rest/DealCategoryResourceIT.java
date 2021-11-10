package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.repository.DealCategoryRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DealCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deal-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealCategoryRepository dealCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealCategoryMockMvc;

    private DealCategory dealCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealCategory createEntity(EntityManager em) {
        DealCategory dealCategory = new DealCategory().name(DEFAULT_NAME).icon(DEFAULT_ICON).description(DEFAULT_DESCRIPTION);
        return dealCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealCategory createUpdatedEntity(EntityManager em) {
        DealCategory dealCategory = new DealCategory().name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);
        return dealCategory;
    }

    @BeforeEach
    public void initTest() {
        dealCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createDealCategory() throws Exception {
        int databaseSizeBeforeCreate = dealCategoryRepository.findAll().size();
        // Create the DealCategory
        restDealCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealCategory)))
            .andExpect(status().isCreated());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        DealCategory testDealCategory = dealCategoryList.get(dealCategoryList.size() - 1);
        assertThat(testDealCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDealCategory.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDealCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createDealCategoryWithExistingId() throws Exception {
        // Create the DealCategory with an existing ID
        dealCategory.setId(1L);

        int databaseSizeBeforeCreate = dealCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealCategory)))
            .andExpect(status().isBadRequest());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDealCategories() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        // Get all the dealCategoryList
        restDealCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getDealCategory() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        // Get the dealCategory
        restDealCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, dealCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDealCategory() throws Exception {
        // Get the dealCategory
        restDealCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDealCategory() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();

        // Update the dealCategory
        DealCategory updatedDealCategory = dealCategoryRepository.findById(dealCategory.getId()).get();
        // Disconnect from session so that the updates on updatedDealCategory are not directly saved in db
        em.detach(updatedDealCategory);
        updatedDealCategory.name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);

        restDealCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealCategory))
            )
            .andExpect(status().isOk());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
        DealCategory testDealCategory = dealCategoryList.get(dealCategoryList.size() - 1);
        assertThat(testDealCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDealCategory.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDealCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealCategoryWithPatch() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();

        // Update the dealCategory using partial update
        DealCategory partialUpdatedDealCategory = new DealCategory();
        partialUpdatedDealCategory.setId(dealCategory.getId());

        restDealCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealCategory))
            )
            .andExpect(status().isOk());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
        DealCategory testDealCategory = dealCategoryList.get(dealCategoryList.size() - 1);
        assertThat(testDealCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDealCategory.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDealCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateDealCategoryWithPatch() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();

        // Update the dealCategory using partial update
        DealCategory partialUpdatedDealCategory = new DealCategory();
        partialUpdatedDealCategory.setId(dealCategory.getId());

        partialUpdatedDealCategory.name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);

        restDealCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealCategory))
            )
            .andExpect(status().isOk());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
        DealCategory testDealCategory = dealCategoryList.get(dealCategoryList.size() - 1);
        assertThat(testDealCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDealCategory.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDealCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealCategory() throws Exception {
        int databaseSizeBeforeUpdate = dealCategoryRepository.findAll().size();
        dealCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dealCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealCategory in the database
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealCategory() throws Exception {
        // Initialize the database
        dealCategoryRepository.saveAndFlush(dealCategory);

        int databaseSizeBeforeDelete = dealCategoryRepository.findAll().size();

        // Delete the dealCategory
        restDealCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealCategory> dealCategoryList = dealCategoryRepository.findAll();
        assertThat(dealCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
