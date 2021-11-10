package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.LoginProfile;
import com.cloudfen.flashadeal.domain.enumeration.MemberType;
import com.cloudfen.flashadeal.repository.LoginProfileRepository;
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
 * Integration tests for the {@link LoginProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoginProfileResourceIT {

    private static final MemberType DEFAULT_MEMBER_TYPE = MemberType.MERCHANT;
    private static final MemberType UPDATED_MEMBER_TYPE = MemberType.BUYER;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/login-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoginProfileRepository loginProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoginProfileMockMvc;

    private LoginProfile loginProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .memberType(DEFAULT_MEMBER_TYPE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailId(DEFAULT_EMAIL_ID)
            .password(DEFAULT_PASSWORD)
            .activationCode(DEFAULT_ACTIVATION_CODE);
        return loginProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createUpdatedEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .memberType(UPDATED_MEMBER_TYPE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationCode(UPDATED_ACTIVATION_CODE);
        return loginProfile;
    }

    @BeforeEach
    public void initTest() {
        loginProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createLoginProfile() throws Exception {
        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();
        // Create the LoginProfile
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isCreated());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate + 1);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(DEFAULT_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void createLoginProfileWithExistingId() throws Exception {
        // Create the LoginProfile with an existing ID
        loginProfile.setId(1L);

        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoginProfiles() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].activationCode").value(hasItem(DEFAULT_ACTIVATION_CODE)));
    }

    @Test
    @Transactional
    void getLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get the loginProfile
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, loginProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginProfile.getId().intValue()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.activationCode").value(DEFAULT_ACTIVATION_CODE));
    }

    @Test
    @Transactional
    void getNonExistingLoginProfile() throws Exception {
        // Get the loginProfile
        restLoginProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile
        LoginProfile updatedLoginProfile = loginProfileRepository.findById(loginProfile.getId()).get();
        // Disconnect from session so that the updates on updatedLoginProfile are not directly saved in db
        em.detach(updatedLoginProfile);
        updatedLoginProfile
            .memberType(UPDATED_MEMBER_TYPE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void putNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile.memberType(UPDATED_MEMBER_TYPE).password(UPDATED_PASSWORD);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(DEFAULT_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void fullUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile
            .memberType(UPDATED_MEMBER_TYPE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeDelete = loginProfileRepository.findAll().size();

        // Delete the loginProfile
        restLoginProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, loginProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
