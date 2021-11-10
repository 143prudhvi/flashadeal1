package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.domain.enumeration.MerchantLocationType;
import com.cloudfen.flashadeal.repository.MerchantDetailsRepository;
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
 * Integration tests for the {@link MerchantDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MerchantDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_ICON = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final MerchantLocationType DEFAULT_LOCATION = MerchantLocationType.INSTORE;
    private static final MerchantLocationType UPDATED_LOCATION = MerchantLocationType.ONLINE;

    private static final String ENTITY_API_URL = "/api/merchant-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MerchantDetailsRepository merchantDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMerchantDetailsMockMvc;

    private MerchantDetails merchantDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MerchantDetails createEntity(EntityManager em) {
        MerchantDetails merchantDetails = new MerchantDetails()
            .name(DEFAULT_NAME)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .storeIcon(DEFAULT_STORE_ICON)
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION);
        return merchantDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MerchantDetails createUpdatedEntity(EntityManager em) {
        MerchantDetails merchantDetails = new MerchantDetails()
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION);
        return merchantDetails;
    }

    @BeforeEach
    public void initTest() {
        merchantDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createMerchantDetails() throws Exception {
        int databaseSizeBeforeCreate = merchantDetailsRepository.findAll().size();
        // Create the MerchantDetails
        restMerchantDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isCreated());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantDetails testMerchantDetails = merchantDetailsList.get(merchantDetailsList.size() - 1);
        assertThat(testMerchantDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchantDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchantDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchantDetails.getStoreIcon()).isEqualTo(DEFAULT_STORE_ICON);
        assertThat(testMerchantDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMerchantDetails.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void createMerchantDetailsWithExistingId() throws Exception {
        // Create the MerchantDetails with an existing ID
        merchantDetails.setId(1L);

        int databaseSizeBeforeCreate = merchantDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMerchantDetails() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        // Get all the merchantDetailsList
        restMerchantDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].storeIcon").value(hasItem(DEFAULT_STORE_ICON.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    void getMerchantDetails() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        // Get the merchantDetails
        restMerchantDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, merchantDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(merchantDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.storeIcon").value(DEFAULT_STORE_ICON.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMerchantDetails() throws Exception {
        // Get the merchantDetails
        restMerchantDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMerchantDetails() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();

        // Update the merchantDetails
        MerchantDetails updatedMerchantDetails = merchantDetailsRepository.findById(merchantDetails.getId()).get();
        // Disconnect from session so that the updates on updatedMerchantDetails are not directly saved in db
        em.detach(updatedMerchantDetails);
        updatedMerchantDetails
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION);

        restMerchantDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMerchantDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMerchantDetails))
            )
            .andExpect(status().isOk());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
        MerchantDetails testMerchantDetails = merchantDetailsList.get(merchantDetailsList.size() - 1);
        assertThat(testMerchantDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchantDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchantDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchantDetails.getStoreIcon()).isEqualTo(UPDATED_STORE_ICON);
        assertThat(testMerchantDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchantDetails.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void putNonExistingMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, merchantDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMerchantDetailsWithPatch() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();

        // Update the merchantDetails using partial update
        MerchantDetails partialUpdatedMerchantDetails = new MerchantDetails();
        partialUpdatedMerchantDetails.setId(merchantDetails.getId());

        partialUpdatedMerchantDetails.name(UPDATED_NAME).type(UPDATED_TYPE);

        restMerchantDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMerchantDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchantDetails))
            )
            .andExpect(status().isOk());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
        MerchantDetails testMerchantDetails = merchantDetailsList.get(merchantDetailsList.size() - 1);
        assertThat(testMerchantDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchantDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchantDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchantDetails.getStoreIcon()).isEqualTo(DEFAULT_STORE_ICON);
        assertThat(testMerchantDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchantDetails.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void fullUpdateMerchantDetailsWithPatch() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();

        // Update the merchantDetails using partial update
        MerchantDetails partialUpdatedMerchantDetails = new MerchantDetails();
        partialUpdatedMerchantDetails.setId(merchantDetails.getId());

        partialUpdatedMerchantDetails
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION);

        restMerchantDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMerchantDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchantDetails))
            )
            .andExpect(status().isOk());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
        MerchantDetails testMerchantDetails = merchantDetailsList.get(merchantDetailsList.size() - 1);
        assertThat(testMerchantDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchantDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchantDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchantDetails.getStoreIcon()).isEqualTo(UPDATED_STORE_ICON);
        assertThat(testMerchantDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchantDetails.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void patchNonExistingMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, merchantDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMerchantDetails() throws Exception {
        int databaseSizeBeforeUpdate = merchantDetailsRepository.findAll().size();
        merchantDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(merchantDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MerchantDetails in the database
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMerchantDetails() throws Exception {
        // Initialize the database
        merchantDetailsRepository.saveAndFlush(merchantDetails);

        int databaseSizeBeforeDelete = merchantDetailsRepository.findAll().size();

        // Delete the merchantDetails
        restMerchantDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, merchantDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MerchantDetails> merchantDetailsList = merchantDetailsRepository.findAll();
        assertThat(merchantDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
