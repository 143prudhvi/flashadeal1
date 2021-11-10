package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.domain.SideDisplayDeal;
import com.cloudfen.flashadeal.domain.enumeration.DealDisplayPostion;
import com.cloudfen.flashadeal.domain.enumeration.DealType;
import com.cloudfen.flashadeal.repository.SideDisplayDealRepository;
import com.cloudfen.flashadeal.service.criteria.SideDisplayDealCriteria;
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
 * Integration tests for the {@link SideDisplayDealResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SideDisplayDealResourceIT {

    private static final DealType DEFAULT_TYPE = DealType.USA_FREEDEAL;
    private static final DealType UPDATED_TYPE = DealType.USA_PAIDDEAL;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_POSTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_POSTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_POSTED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE = "BBBBBBBBBB";

    private static final DealDisplayPostion DEFAULT_POSITION = DealDisplayPostion.DASHBOARD_DEAL_TOP_LIST;
    private static final DealDisplayPostion UPDATED_POSITION = DealDisplayPostion.DASHBOARD_DEAL_MIDDLE_LIST;

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/side-display-deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SideDisplayDealRepository sideDisplayDealRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSideDisplayDealMockMvc;

    private SideDisplayDeal sideDisplayDeal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SideDisplayDeal createEntity(EntityManager em) {
        SideDisplayDeal sideDisplayDeal = new SideDisplayDeal()
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .dealUrl(DEFAULT_DEAL_URL)
            .postedBy(DEFAULT_POSTED_BY)
            .postedDate(DEFAULT_POSTED_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .active(DEFAULT_ACTIVE)
            .position(DEFAULT_POSITION)
            .approved(DEFAULT_APPROVED)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .pinCode(DEFAULT_PIN_CODE);
        return sideDisplayDeal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SideDisplayDeal createUpdatedEntity(EntityManager em) {
        SideDisplayDeal sideDisplayDeal = new SideDisplayDeal()
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE);
        return sideDisplayDeal;
    }

    @BeforeEach
    public void initTest() {
        sideDisplayDeal = createEntity(em);
    }

    @Test
    @Transactional
    void createSideDisplayDeal() throws Exception {
        int databaseSizeBeforeCreate = sideDisplayDealRepository.findAll().size();
        // Create the SideDisplayDeal
        restSideDisplayDealMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isCreated());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeCreate + 1);
        SideDisplayDeal testSideDisplayDeal = sideDisplayDealList.get(sideDisplayDealList.size() - 1);
        assertThat(testSideDisplayDeal.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSideDisplayDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSideDisplayDeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSideDisplayDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSideDisplayDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testSideDisplayDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testSideDisplayDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testSideDisplayDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSideDisplayDeal.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSideDisplayDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testSideDisplayDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testSideDisplayDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testSideDisplayDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testSideDisplayDeal.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testSideDisplayDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testSideDisplayDeal.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSideDisplayDeal.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSideDisplayDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    void createSideDisplayDealWithExistingId() throws Exception {
        // Create the SideDisplayDeal with an existing ID
        sideDisplayDeal.setId(1L);

        int databaseSizeBeforeCreate = sideDisplayDealRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSideDisplayDealMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSideDisplayDeals() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sideDisplayDeal.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].postedBy").value(hasItem(DEFAULT_POSTED_BY)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)));
    }

    @Test
    @Transactional
    void getSideDisplayDeal() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get the sideDisplayDeal
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL_ID, sideDisplayDeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sideDisplayDeal.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.dealUrl").value(DEFAULT_DEAL_URL.toString()))
            .andExpect(jsonPath("$.postedBy").value(DEFAULT_POSTED_BY))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE));
    }

    @Test
    @Transactional
    void getSideDisplayDealsByIdFiltering() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        Long id = sideDisplayDeal.getId();

        defaultSideDisplayDealShouldBeFound("id.equals=" + id);
        defaultSideDisplayDealShouldNotBeFound("id.notEquals=" + id);

        defaultSideDisplayDealShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSideDisplayDealShouldNotBeFound("id.greaterThan=" + id);

        defaultSideDisplayDealShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSideDisplayDealShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where type equals to DEFAULT_TYPE
        defaultSideDisplayDealShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the sideDisplayDealList where type equals to UPDATED_TYPE
        defaultSideDisplayDealShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where type not equals to DEFAULT_TYPE
        defaultSideDisplayDealShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the sideDisplayDealList where type not equals to UPDATED_TYPE
        defaultSideDisplayDealShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSideDisplayDealShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the sideDisplayDealList where type equals to UPDATED_TYPE
        defaultSideDisplayDealShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where type is not null
        defaultSideDisplayDealShouldBeFound("type.specified=true");

        // Get all the sideDisplayDealList where type is null
        defaultSideDisplayDealShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title equals to DEFAULT_TITLE
        defaultSideDisplayDealShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the sideDisplayDealList where title equals to UPDATED_TITLE
        defaultSideDisplayDealShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title not equals to DEFAULT_TITLE
        defaultSideDisplayDealShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the sideDisplayDealList where title not equals to UPDATED_TITLE
        defaultSideDisplayDealShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSideDisplayDealShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the sideDisplayDealList where title equals to UPDATED_TITLE
        defaultSideDisplayDealShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title is not null
        defaultSideDisplayDealShouldBeFound("title.specified=true");

        // Get all the sideDisplayDealList where title is null
        defaultSideDisplayDealShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title contains DEFAULT_TITLE
        defaultSideDisplayDealShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the sideDisplayDealList where title contains UPDATED_TITLE
        defaultSideDisplayDealShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where title does not contain DEFAULT_TITLE
        defaultSideDisplayDealShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the sideDisplayDealList where title does not contain UPDATED_TITLE
        defaultSideDisplayDealShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy equals to DEFAULT_POSTED_BY
        defaultSideDisplayDealShouldBeFound("postedBy.equals=" + DEFAULT_POSTED_BY);

        // Get all the sideDisplayDealList where postedBy equals to UPDATED_POSTED_BY
        defaultSideDisplayDealShouldNotBeFound("postedBy.equals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy not equals to DEFAULT_POSTED_BY
        defaultSideDisplayDealShouldNotBeFound("postedBy.notEquals=" + DEFAULT_POSTED_BY);

        // Get all the sideDisplayDealList where postedBy not equals to UPDATED_POSTED_BY
        defaultSideDisplayDealShouldBeFound("postedBy.notEquals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy in DEFAULT_POSTED_BY or UPDATED_POSTED_BY
        defaultSideDisplayDealShouldBeFound("postedBy.in=" + DEFAULT_POSTED_BY + "," + UPDATED_POSTED_BY);

        // Get all the sideDisplayDealList where postedBy equals to UPDATED_POSTED_BY
        defaultSideDisplayDealShouldNotBeFound("postedBy.in=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy is not null
        defaultSideDisplayDealShouldBeFound("postedBy.specified=true");

        // Get all the sideDisplayDealList where postedBy is null
        defaultSideDisplayDealShouldNotBeFound("postedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy contains DEFAULT_POSTED_BY
        defaultSideDisplayDealShouldBeFound("postedBy.contains=" + DEFAULT_POSTED_BY);

        // Get all the sideDisplayDealList where postedBy contains UPDATED_POSTED_BY
        defaultSideDisplayDealShouldNotBeFound("postedBy.contains=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedByNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedBy does not contain DEFAULT_POSTED_BY
        defaultSideDisplayDealShouldNotBeFound("postedBy.doesNotContain=" + DEFAULT_POSTED_BY);

        // Get all the sideDisplayDealList where postedBy does not contain UPDATED_POSTED_BY
        defaultSideDisplayDealShouldBeFound("postedBy.doesNotContain=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate equals to DEFAULT_POSTED_DATE
        defaultSideDisplayDealShouldBeFound("postedDate.equals=" + DEFAULT_POSTED_DATE);

        // Get all the sideDisplayDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldNotBeFound("postedDate.equals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate not equals to DEFAULT_POSTED_DATE
        defaultSideDisplayDealShouldNotBeFound("postedDate.notEquals=" + DEFAULT_POSTED_DATE);

        // Get all the sideDisplayDealList where postedDate not equals to UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldBeFound("postedDate.notEquals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate in DEFAULT_POSTED_DATE or UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldBeFound("postedDate.in=" + DEFAULT_POSTED_DATE + "," + UPDATED_POSTED_DATE);

        // Get all the sideDisplayDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldNotBeFound("postedDate.in=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate is not null
        defaultSideDisplayDealShouldBeFound("postedDate.specified=true");

        // Get all the sideDisplayDealList where postedDate is null
        defaultSideDisplayDealShouldNotBeFound("postedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate contains DEFAULT_POSTED_DATE
        defaultSideDisplayDealShouldBeFound("postedDate.contains=" + DEFAULT_POSTED_DATE);

        // Get all the sideDisplayDealList where postedDate contains UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldNotBeFound("postedDate.contains=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPostedDateNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where postedDate does not contain DEFAULT_POSTED_DATE
        defaultSideDisplayDealShouldNotBeFound("postedDate.doesNotContain=" + DEFAULT_POSTED_DATE);

        // Get all the sideDisplayDealList where postedDate does not contain UPDATED_POSTED_DATE
        defaultSideDisplayDealShouldBeFound("postedDate.doesNotContain=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate equals to DEFAULT_START_DATE
        defaultSideDisplayDealShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the sideDisplayDealList where startDate equals to UPDATED_START_DATE
        defaultSideDisplayDealShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate not equals to DEFAULT_START_DATE
        defaultSideDisplayDealShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the sideDisplayDealList where startDate not equals to UPDATED_START_DATE
        defaultSideDisplayDealShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultSideDisplayDealShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the sideDisplayDealList where startDate equals to UPDATED_START_DATE
        defaultSideDisplayDealShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate is not null
        defaultSideDisplayDealShouldBeFound("startDate.specified=true");

        // Get all the sideDisplayDealList where startDate is null
        defaultSideDisplayDealShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate contains DEFAULT_START_DATE
        defaultSideDisplayDealShouldBeFound("startDate.contains=" + DEFAULT_START_DATE);

        // Get all the sideDisplayDealList where startDate contains UPDATED_START_DATE
        defaultSideDisplayDealShouldNotBeFound("startDate.contains=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where startDate does not contain DEFAULT_START_DATE
        defaultSideDisplayDealShouldNotBeFound("startDate.doesNotContain=" + DEFAULT_START_DATE);

        // Get all the sideDisplayDealList where startDate does not contain UPDATED_START_DATE
        defaultSideDisplayDealShouldBeFound("startDate.doesNotContain=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate equals to DEFAULT_END_DATE
        defaultSideDisplayDealShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the sideDisplayDealList where endDate equals to UPDATED_END_DATE
        defaultSideDisplayDealShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate not equals to DEFAULT_END_DATE
        defaultSideDisplayDealShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the sideDisplayDealList where endDate not equals to UPDATED_END_DATE
        defaultSideDisplayDealShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultSideDisplayDealShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the sideDisplayDealList where endDate equals to UPDATED_END_DATE
        defaultSideDisplayDealShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate is not null
        defaultSideDisplayDealShouldBeFound("endDate.specified=true");

        // Get all the sideDisplayDealList where endDate is null
        defaultSideDisplayDealShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate contains DEFAULT_END_DATE
        defaultSideDisplayDealShouldBeFound("endDate.contains=" + DEFAULT_END_DATE);

        // Get all the sideDisplayDealList where endDate contains UPDATED_END_DATE
        defaultSideDisplayDealShouldNotBeFound("endDate.contains=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where endDate does not contain DEFAULT_END_DATE
        defaultSideDisplayDealShouldNotBeFound("endDate.doesNotContain=" + DEFAULT_END_DATE);

        // Get all the sideDisplayDealList where endDate does not contain UPDATED_END_DATE
        defaultSideDisplayDealShouldBeFound("endDate.doesNotContain=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultSideDisplayDealShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the sideDisplayDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice not equals to DEFAULT_ORIGINAL_PRICE
        defaultSideDisplayDealShouldNotBeFound("originalPrice.notEquals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the sideDisplayDealList where originalPrice not equals to UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldBeFound("originalPrice.notEquals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the sideDisplayDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice is not null
        defaultSideDisplayDealShouldBeFound("originalPrice.specified=true");

        // Get all the sideDisplayDealList where originalPrice is null
        defaultSideDisplayDealShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice contains DEFAULT_ORIGINAL_PRICE
        defaultSideDisplayDealShouldBeFound("originalPrice.contains=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the sideDisplayDealList where originalPrice contains UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldNotBeFound("originalPrice.contains=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByOriginalPriceNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where originalPrice does not contain DEFAULT_ORIGINAL_PRICE
        defaultSideDisplayDealShouldNotBeFound("originalPrice.doesNotContain=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the sideDisplayDealList where originalPrice does not contain UPDATED_ORIGINAL_PRICE
        defaultSideDisplayDealShouldBeFound("originalPrice.doesNotContain=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultSideDisplayDealShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the sideDisplayDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice not equals to DEFAULT_CURRENT_PRICE
        defaultSideDisplayDealShouldNotBeFound("currentPrice.notEquals=" + DEFAULT_CURRENT_PRICE);

        // Get all the sideDisplayDealList where currentPrice not equals to UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldBeFound("currentPrice.notEquals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the sideDisplayDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice is not null
        defaultSideDisplayDealShouldBeFound("currentPrice.specified=true");

        // Get all the sideDisplayDealList where currentPrice is null
        defaultSideDisplayDealShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice contains DEFAULT_CURRENT_PRICE
        defaultSideDisplayDealShouldBeFound("currentPrice.contains=" + DEFAULT_CURRENT_PRICE);

        // Get all the sideDisplayDealList where currentPrice contains UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldNotBeFound("currentPrice.contains=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCurrentPriceNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where currentPrice does not contain DEFAULT_CURRENT_PRICE
        defaultSideDisplayDealShouldNotBeFound("currentPrice.doesNotContain=" + DEFAULT_CURRENT_PRICE);

        // Get all the sideDisplayDealList where currentPrice does not contain UPDATED_CURRENT_PRICE
        defaultSideDisplayDealShouldBeFound("currentPrice.doesNotContain=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount equals to DEFAULT_DISCOUNT
        defaultSideDisplayDealShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the sideDisplayDealList where discount equals to UPDATED_DISCOUNT
        defaultSideDisplayDealShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount not equals to DEFAULT_DISCOUNT
        defaultSideDisplayDealShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the sideDisplayDealList where discount not equals to UPDATED_DISCOUNT
        defaultSideDisplayDealShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultSideDisplayDealShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the sideDisplayDealList where discount equals to UPDATED_DISCOUNT
        defaultSideDisplayDealShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount is not null
        defaultSideDisplayDealShouldBeFound("discount.specified=true");

        // Get all the sideDisplayDealList where discount is null
        defaultSideDisplayDealShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount contains DEFAULT_DISCOUNT
        defaultSideDisplayDealShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the sideDisplayDealList where discount contains UPDATED_DISCOUNT
        defaultSideDisplayDealShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where discount does not contain DEFAULT_DISCOUNT
        defaultSideDisplayDealShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the sideDisplayDealList where discount does not contain UPDATED_DISCOUNT
        defaultSideDisplayDealShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active equals to DEFAULT_ACTIVE
        defaultSideDisplayDealShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the sideDisplayDealList where active equals to UPDATED_ACTIVE
        defaultSideDisplayDealShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active not equals to DEFAULT_ACTIVE
        defaultSideDisplayDealShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the sideDisplayDealList where active not equals to UPDATED_ACTIVE
        defaultSideDisplayDealShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultSideDisplayDealShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the sideDisplayDealList where active equals to UPDATED_ACTIVE
        defaultSideDisplayDealShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active is not null
        defaultSideDisplayDealShouldBeFound("active.specified=true");

        // Get all the sideDisplayDealList where active is null
        defaultSideDisplayDealShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active contains DEFAULT_ACTIVE
        defaultSideDisplayDealShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the sideDisplayDealList where active contains UPDATED_ACTIVE
        defaultSideDisplayDealShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where active does not contain DEFAULT_ACTIVE
        defaultSideDisplayDealShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the sideDisplayDealList where active does not contain UPDATED_ACTIVE
        defaultSideDisplayDealShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where position equals to DEFAULT_POSITION
        defaultSideDisplayDealShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the sideDisplayDealList where position equals to UPDATED_POSITION
        defaultSideDisplayDealShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where position not equals to DEFAULT_POSITION
        defaultSideDisplayDealShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the sideDisplayDealList where position not equals to UPDATED_POSITION
        defaultSideDisplayDealShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultSideDisplayDealShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the sideDisplayDealList where position equals to UPDATED_POSITION
        defaultSideDisplayDealShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where position is not null
        defaultSideDisplayDealShouldBeFound("position.specified=true");

        // Get all the sideDisplayDealList where position is null
        defaultSideDisplayDealShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where approved equals to DEFAULT_APPROVED
        defaultSideDisplayDealShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the sideDisplayDealList where approved equals to UPDATED_APPROVED
        defaultSideDisplayDealShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where approved not equals to DEFAULT_APPROVED
        defaultSideDisplayDealShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the sideDisplayDealList where approved not equals to UPDATED_APPROVED
        defaultSideDisplayDealShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultSideDisplayDealShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the sideDisplayDealList where approved equals to UPDATED_APPROVED
        defaultSideDisplayDealShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where approved is not null
        defaultSideDisplayDealShouldBeFound("approved.specified=true");

        // Get all the sideDisplayDealList where approved is null
        defaultSideDisplayDealShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country equals to DEFAULT_COUNTRY
        defaultSideDisplayDealShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the sideDisplayDealList where country equals to UPDATED_COUNTRY
        defaultSideDisplayDealShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country not equals to DEFAULT_COUNTRY
        defaultSideDisplayDealShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the sideDisplayDealList where country not equals to UPDATED_COUNTRY
        defaultSideDisplayDealShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultSideDisplayDealShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the sideDisplayDealList where country equals to UPDATED_COUNTRY
        defaultSideDisplayDealShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country is not null
        defaultSideDisplayDealShouldBeFound("country.specified=true");

        // Get all the sideDisplayDealList where country is null
        defaultSideDisplayDealShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country contains DEFAULT_COUNTRY
        defaultSideDisplayDealShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the sideDisplayDealList where country contains UPDATED_COUNTRY
        defaultSideDisplayDealShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where country does not contain DEFAULT_COUNTRY
        defaultSideDisplayDealShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the sideDisplayDealList where country does not contain UPDATED_COUNTRY
        defaultSideDisplayDealShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city equals to DEFAULT_CITY
        defaultSideDisplayDealShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the sideDisplayDealList where city equals to UPDATED_CITY
        defaultSideDisplayDealShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city not equals to DEFAULT_CITY
        defaultSideDisplayDealShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the sideDisplayDealList where city not equals to UPDATED_CITY
        defaultSideDisplayDealShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city in DEFAULT_CITY or UPDATED_CITY
        defaultSideDisplayDealShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the sideDisplayDealList where city equals to UPDATED_CITY
        defaultSideDisplayDealShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city is not null
        defaultSideDisplayDealShouldBeFound("city.specified=true");

        // Get all the sideDisplayDealList where city is null
        defaultSideDisplayDealShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city contains DEFAULT_CITY
        defaultSideDisplayDealShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the sideDisplayDealList where city contains UPDATED_CITY
        defaultSideDisplayDealShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where city does not contain DEFAULT_CITY
        defaultSideDisplayDealShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the sideDisplayDealList where city does not contain UPDATED_CITY
        defaultSideDisplayDealShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode equals to DEFAULT_PIN_CODE
        defaultSideDisplayDealShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the sideDisplayDealList where pinCode equals to UPDATED_PIN_CODE
        defaultSideDisplayDealShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode not equals to DEFAULT_PIN_CODE
        defaultSideDisplayDealShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the sideDisplayDealList where pinCode not equals to UPDATED_PIN_CODE
        defaultSideDisplayDealShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultSideDisplayDealShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the sideDisplayDealList where pinCode equals to UPDATED_PIN_CODE
        defaultSideDisplayDealShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode is not null
        defaultSideDisplayDealShouldBeFound("pinCode.specified=true");

        // Get all the sideDisplayDealList where pinCode is null
        defaultSideDisplayDealShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode contains DEFAULT_PIN_CODE
        defaultSideDisplayDealShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the sideDisplayDealList where pinCode contains UPDATED_PIN_CODE
        defaultSideDisplayDealShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        // Get all the sideDisplayDealList where pinCode does not contain DEFAULT_PIN_CODE
        defaultSideDisplayDealShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the sideDisplayDealList where pinCode does not contain UPDATED_PIN_CODE
        defaultSideDisplayDealShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByMerchantDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);
        MerchantDetails merchantDetails;
        if (TestUtil.findAll(em, MerchantDetails.class).isEmpty()) {
            merchantDetails = MerchantDetailsResourceIT.createEntity(em);
            em.persist(merchantDetails);
            em.flush();
        } else {
            merchantDetails = TestUtil.findAll(em, MerchantDetails.class).get(0);
        }
        em.persist(merchantDetails);
        em.flush();
        sideDisplayDeal.setMerchantDetails(merchantDetails);
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);
        Long merchantDetailsId = merchantDetails.getId();

        // Get all the sideDisplayDealList where merchantDetails equals to merchantDetailsId
        defaultSideDisplayDealShouldBeFound("merchantDetailsId.equals=" + merchantDetailsId);

        // Get all the sideDisplayDealList where merchantDetails equals to (merchantDetailsId + 1)
        defaultSideDisplayDealShouldNotBeFound("merchantDetailsId.equals=" + (merchantDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllSideDisplayDealsByDealCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);
        DealCategory dealCategory;
        if (TestUtil.findAll(em, DealCategory.class).isEmpty()) {
            dealCategory = DealCategoryResourceIT.createEntity(em);
            em.persist(dealCategory);
            em.flush();
        } else {
            dealCategory = TestUtil.findAll(em, DealCategory.class).get(0);
        }
        em.persist(dealCategory);
        em.flush();
        sideDisplayDeal.setDealCategory(dealCategory);
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);
        Long dealCategoryId = dealCategory.getId();

        // Get all the sideDisplayDealList where dealCategory equals to dealCategoryId
        defaultSideDisplayDealShouldBeFound("dealCategoryId.equals=" + dealCategoryId);

        // Get all the sideDisplayDealList where dealCategory equals to (dealCategoryId + 1)
        defaultSideDisplayDealShouldNotBeFound("dealCategoryId.equals=" + (dealCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSideDisplayDealShouldBeFound(String filter) throws Exception {
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sideDisplayDeal.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].postedBy").value(hasItem(DEFAULT_POSTED_BY)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)));

        // Check, that the count call also returns 1
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSideDisplayDealShouldNotBeFound(String filter) throws Exception {
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSideDisplayDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSideDisplayDeal() throws Exception {
        // Get the sideDisplayDeal
        restSideDisplayDealMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSideDisplayDeal() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();

        // Update the sideDisplayDeal
        SideDisplayDeal updatedSideDisplayDeal = sideDisplayDealRepository.findById(sideDisplayDeal.getId()).get();
        // Disconnect from session so that the updates on updatedSideDisplayDeal are not directly saved in db
        em.detach(updatedSideDisplayDeal);
        updatedSideDisplayDeal
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE);

        restSideDisplayDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSideDisplayDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSideDisplayDeal))
            )
            .andExpect(status().isOk());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
        SideDisplayDeal testSideDisplayDeal = sideDisplayDealList.get(sideDisplayDealList.size() - 1);
        assertThat(testSideDisplayDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSideDisplayDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSideDisplayDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSideDisplayDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSideDisplayDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testSideDisplayDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testSideDisplayDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testSideDisplayDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSideDisplayDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSideDisplayDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testSideDisplayDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testSideDisplayDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSideDisplayDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testSideDisplayDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testSideDisplayDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testSideDisplayDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSideDisplayDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSideDisplayDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void putNonExistingSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sideDisplayDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSideDisplayDealWithPatch() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();

        // Update the sideDisplayDeal using partial update
        SideDisplayDeal partialUpdatedSideDisplayDeal = new SideDisplayDeal();
        partialUpdatedSideDisplayDeal.setId(sideDisplayDeal.getId());

        partialUpdatedSideDisplayDeal
            .type(UPDATED_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .position(UPDATED_POSITION)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY);

        restSideDisplayDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSideDisplayDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSideDisplayDeal))
            )
            .andExpect(status().isOk());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
        SideDisplayDeal testSideDisplayDeal = sideDisplayDealList.get(sideDisplayDealList.size() - 1);
        assertThat(testSideDisplayDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSideDisplayDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSideDisplayDeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSideDisplayDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSideDisplayDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testSideDisplayDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testSideDisplayDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testSideDisplayDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSideDisplayDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSideDisplayDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testSideDisplayDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testSideDisplayDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSideDisplayDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testSideDisplayDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testSideDisplayDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testSideDisplayDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSideDisplayDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSideDisplayDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    void fullUpdateSideDisplayDealWithPatch() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();

        // Update the sideDisplayDeal using partial update
        SideDisplayDeal partialUpdatedSideDisplayDeal = new SideDisplayDeal();
        partialUpdatedSideDisplayDeal.setId(sideDisplayDeal.getId());

        partialUpdatedSideDisplayDeal
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE);

        restSideDisplayDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSideDisplayDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSideDisplayDeal))
            )
            .andExpect(status().isOk());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
        SideDisplayDeal testSideDisplayDeal = sideDisplayDealList.get(sideDisplayDealList.size() - 1);
        assertThat(testSideDisplayDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSideDisplayDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSideDisplayDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSideDisplayDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSideDisplayDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testSideDisplayDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testSideDisplayDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testSideDisplayDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSideDisplayDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSideDisplayDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testSideDisplayDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testSideDisplayDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSideDisplayDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testSideDisplayDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testSideDisplayDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testSideDisplayDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSideDisplayDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSideDisplayDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sideDisplayDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSideDisplayDeal() throws Exception {
        int databaseSizeBeforeUpdate = sideDisplayDealRepository.findAll().size();
        sideDisplayDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSideDisplayDealMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sideDisplayDeal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SideDisplayDeal in the database
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSideDisplayDeal() throws Exception {
        // Initialize the database
        sideDisplayDealRepository.saveAndFlush(sideDisplayDeal);

        int databaseSizeBeforeDelete = sideDisplayDealRepository.findAll().size();

        // Delete the sideDisplayDeal
        restSideDisplayDealMockMvc
            .perform(delete(ENTITY_API_URL_ID, sideDisplayDeal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SideDisplayDeal> sideDisplayDealList = sideDisplayDealRepository.findAll();
        assertThat(sideDisplayDealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
