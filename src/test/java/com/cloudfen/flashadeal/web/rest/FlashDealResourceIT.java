package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.domain.FlashDeal;
import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.domain.enumeration.DealDisplayPostion;
import com.cloudfen.flashadeal.domain.enumeration.DealType;
import com.cloudfen.flashadeal.repository.FlashDealRepository;
import com.cloudfen.flashadeal.service.criteria.FlashDealCriteria;
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
 * Integration tests for the {@link FlashDealResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlashDealResourceIT {

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

    private static final String ENTITY_API_URL = "/api/flash-deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlashDealRepository flashDealRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlashDealMockMvc;

    private FlashDeal flashDeal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlashDeal createEntity(EntityManager em) {
        FlashDeal flashDeal = new FlashDeal()
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
        return flashDeal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlashDeal createUpdatedEntity(EntityManager em) {
        FlashDeal flashDeal = new FlashDeal()
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
        return flashDeal;
    }

    @BeforeEach
    public void initTest() {
        flashDeal = createEntity(em);
    }

    @Test
    @Transactional
    void createFlashDeal() throws Exception {
        int databaseSizeBeforeCreate = flashDealRepository.findAll().size();
        // Create the FlashDeal
        restFlashDealMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flashDeal)))
            .andExpect(status().isCreated());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeCreate + 1);
        FlashDeal testFlashDeal = flashDealList.get(flashDealList.size() - 1);
        assertThat(testFlashDeal.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFlashDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFlashDeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFlashDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testFlashDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testFlashDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testFlashDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testFlashDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testFlashDeal.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testFlashDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testFlashDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testFlashDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testFlashDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testFlashDeal.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testFlashDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testFlashDeal.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testFlashDeal.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testFlashDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    void createFlashDealWithExistingId() throws Exception {
        // Create the FlashDeal with an existing ID
        flashDeal.setId(1L);

        int databaseSizeBeforeCreate = flashDealRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlashDealMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flashDeal)))
            .andExpect(status().isBadRequest());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFlashDeals() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flashDeal.getId().intValue())))
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
    void getFlashDeal() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get the flashDeal
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL_ID, flashDeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flashDeal.getId().intValue()))
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
    void getFlashDealsByIdFiltering() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        Long id = flashDeal.getId();

        defaultFlashDealShouldBeFound("id.equals=" + id);
        defaultFlashDealShouldNotBeFound("id.notEquals=" + id);

        defaultFlashDealShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFlashDealShouldNotBeFound("id.greaterThan=" + id);

        defaultFlashDealShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFlashDealShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where type equals to DEFAULT_TYPE
        defaultFlashDealShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the flashDealList where type equals to UPDATED_TYPE
        defaultFlashDealShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where type not equals to DEFAULT_TYPE
        defaultFlashDealShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the flashDealList where type not equals to UPDATED_TYPE
        defaultFlashDealShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultFlashDealShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the flashDealList where type equals to UPDATED_TYPE
        defaultFlashDealShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where type is not null
        defaultFlashDealShouldBeFound("type.specified=true");

        // Get all the flashDealList where type is null
        defaultFlashDealShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title equals to DEFAULT_TITLE
        defaultFlashDealShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the flashDealList where title equals to UPDATED_TITLE
        defaultFlashDealShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title not equals to DEFAULT_TITLE
        defaultFlashDealShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the flashDealList where title not equals to UPDATED_TITLE
        defaultFlashDealShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultFlashDealShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the flashDealList where title equals to UPDATED_TITLE
        defaultFlashDealShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title is not null
        defaultFlashDealShouldBeFound("title.specified=true");

        // Get all the flashDealList where title is null
        defaultFlashDealShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title contains DEFAULT_TITLE
        defaultFlashDealShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the flashDealList where title contains UPDATED_TITLE
        defaultFlashDealShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where title does not contain DEFAULT_TITLE
        defaultFlashDealShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the flashDealList where title does not contain UPDATED_TITLE
        defaultFlashDealShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy equals to DEFAULT_POSTED_BY
        defaultFlashDealShouldBeFound("postedBy.equals=" + DEFAULT_POSTED_BY);

        // Get all the flashDealList where postedBy equals to UPDATED_POSTED_BY
        defaultFlashDealShouldNotBeFound("postedBy.equals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy not equals to DEFAULT_POSTED_BY
        defaultFlashDealShouldNotBeFound("postedBy.notEquals=" + DEFAULT_POSTED_BY);

        // Get all the flashDealList where postedBy not equals to UPDATED_POSTED_BY
        defaultFlashDealShouldBeFound("postedBy.notEquals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy in DEFAULT_POSTED_BY or UPDATED_POSTED_BY
        defaultFlashDealShouldBeFound("postedBy.in=" + DEFAULT_POSTED_BY + "," + UPDATED_POSTED_BY);

        // Get all the flashDealList where postedBy equals to UPDATED_POSTED_BY
        defaultFlashDealShouldNotBeFound("postedBy.in=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy is not null
        defaultFlashDealShouldBeFound("postedBy.specified=true");

        // Get all the flashDealList where postedBy is null
        defaultFlashDealShouldNotBeFound("postedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy contains DEFAULT_POSTED_BY
        defaultFlashDealShouldBeFound("postedBy.contains=" + DEFAULT_POSTED_BY);

        // Get all the flashDealList where postedBy contains UPDATED_POSTED_BY
        defaultFlashDealShouldNotBeFound("postedBy.contains=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedByNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedBy does not contain DEFAULT_POSTED_BY
        defaultFlashDealShouldNotBeFound("postedBy.doesNotContain=" + DEFAULT_POSTED_BY);

        // Get all the flashDealList where postedBy does not contain UPDATED_POSTED_BY
        defaultFlashDealShouldBeFound("postedBy.doesNotContain=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate equals to DEFAULT_POSTED_DATE
        defaultFlashDealShouldBeFound("postedDate.equals=" + DEFAULT_POSTED_DATE);

        // Get all the flashDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultFlashDealShouldNotBeFound("postedDate.equals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate not equals to DEFAULT_POSTED_DATE
        defaultFlashDealShouldNotBeFound("postedDate.notEquals=" + DEFAULT_POSTED_DATE);

        // Get all the flashDealList where postedDate not equals to UPDATED_POSTED_DATE
        defaultFlashDealShouldBeFound("postedDate.notEquals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate in DEFAULT_POSTED_DATE or UPDATED_POSTED_DATE
        defaultFlashDealShouldBeFound("postedDate.in=" + DEFAULT_POSTED_DATE + "," + UPDATED_POSTED_DATE);

        // Get all the flashDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultFlashDealShouldNotBeFound("postedDate.in=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate is not null
        defaultFlashDealShouldBeFound("postedDate.specified=true");

        // Get all the flashDealList where postedDate is null
        defaultFlashDealShouldNotBeFound("postedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate contains DEFAULT_POSTED_DATE
        defaultFlashDealShouldBeFound("postedDate.contains=" + DEFAULT_POSTED_DATE);

        // Get all the flashDealList where postedDate contains UPDATED_POSTED_DATE
        defaultFlashDealShouldNotBeFound("postedDate.contains=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPostedDateNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where postedDate does not contain DEFAULT_POSTED_DATE
        defaultFlashDealShouldNotBeFound("postedDate.doesNotContain=" + DEFAULT_POSTED_DATE);

        // Get all the flashDealList where postedDate does not contain UPDATED_POSTED_DATE
        defaultFlashDealShouldBeFound("postedDate.doesNotContain=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate equals to DEFAULT_START_DATE
        defaultFlashDealShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the flashDealList where startDate equals to UPDATED_START_DATE
        defaultFlashDealShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate not equals to DEFAULT_START_DATE
        defaultFlashDealShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the flashDealList where startDate not equals to UPDATED_START_DATE
        defaultFlashDealShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultFlashDealShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the flashDealList where startDate equals to UPDATED_START_DATE
        defaultFlashDealShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate is not null
        defaultFlashDealShouldBeFound("startDate.specified=true");

        // Get all the flashDealList where startDate is null
        defaultFlashDealShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate contains DEFAULT_START_DATE
        defaultFlashDealShouldBeFound("startDate.contains=" + DEFAULT_START_DATE);

        // Get all the flashDealList where startDate contains UPDATED_START_DATE
        defaultFlashDealShouldNotBeFound("startDate.contains=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where startDate does not contain DEFAULT_START_DATE
        defaultFlashDealShouldNotBeFound("startDate.doesNotContain=" + DEFAULT_START_DATE);

        // Get all the flashDealList where startDate does not contain UPDATED_START_DATE
        defaultFlashDealShouldBeFound("startDate.doesNotContain=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate equals to DEFAULT_END_DATE
        defaultFlashDealShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the flashDealList where endDate equals to UPDATED_END_DATE
        defaultFlashDealShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate not equals to DEFAULT_END_DATE
        defaultFlashDealShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the flashDealList where endDate not equals to UPDATED_END_DATE
        defaultFlashDealShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultFlashDealShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the flashDealList where endDate equals to UPDATED_END_DATE
        defaultFlashDealShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate is not null
        defaultFlashDealShouldBeFound("endDate.specified=true");

        // Get all the flashDealList where endDate is null
        defaultFlashDealShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate contains DEFAULT_END_DATE
        defaultFlashDealShouldBeFound("endDate.contains=" + DEFAULT_END_DATE);

        // Get all the flashDealList where endDate contains UPDATED_END_DATE
        defaultFlashDealShouldNotBeFound("endDate.contains=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where endDate does not contain DEFAULT_END_DATE
        defaultFlashDealShouldNotBeFound("endDate.doesNotContain=" + DEFAULT_END_DATE);

        // Get all the flashDealList where endDate does not contain UPDATED_END_DATE
        defaultFlashDealShouldBeFound("endDate.doesNotContain=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultFlashDealShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the flashDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice not equals to DEFAULT_ORIGINAL_PRICE
        defaultFlashDealShouldNotBeFound("originalPrice.notEquals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the flashDealList where originalPrice not equals to UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldBeFound("originalPrice.notEquals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the flashDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice is not null
        defaultFlashDealShouldBeFound("originalPrice.specified=true");

        // Get all the flashDealList where originalPrice is null
        defaultFlashDealShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice contains DEFAULT_ORIGINAL_PRICE
        defaultFlashDealShouldBeFound("originalPrice.contains=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the flashDealList where originalPrice contains UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldNotBeFound("originalPrice.contains=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByOriginalPriceNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where originalPrice does not contain DEFAULT_ORIGINAL_PRICE
        defaultFlashDealShouldNotBeFound("originalPrice.doesNotContain=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the flashDealList where originalPrice does not contain UPDATED_ORIGINAL_PRICE
        defaultFlashDealShouldBeFound("originalPrice.doesNotContain=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultFlashDealShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the flashDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultFlashDealShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice not equals to DEFAULT_CURRENT_PRICE
        defaultFlashDealShouldNotBeFound("currentPrice.notEquals=" + DEFAULT_CURRENT_PRICE);

        // Get all the flashDealList where currentPrice not equals to UPDATED_CURRENT_PRICE
        defaultFlashDealShouldBeFound("currentPrice.notEquals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultFlashDealShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the flashDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultFlashDealShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice is not null
        defaultFlashDealShouldBeFound("currentPrice.specified=true");

        // Get all the flashDealList where currentPrice is null
        defaultFlashDealShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice contains DEFAULT_CURRENT_PRICE
        defaultFlashDealShouldBeFound("currentPrice.contains=" + DEFAULT_CURRENT_PRICE);

        // Get all the flashDealList where currentPrice contains UPDATED_CURRENT_PRICE
        defaultFlashDealShouldNotBeFound("currentPrice.contains=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCurrentPriceNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where currentPrice does not contain DEFAULT_CURRENT_PRICE
        defaultFlashDealShouldNotBeFound("currentPrice.doesNotContain=" + DEFAULT_CURRENT_PRICE);

        // Get all the flashDealList where currentPrice does not contain UPDATED_CURRENT_PRICE
        defaultFlashDealShouldBeFound("currentPrice.doesNotContain=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount equals to DEFAULT_DISCOUNT
        defaultFlashDealShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the flashDealList where discount equals to UPDATED_DISCOUNT
        defaultFlashDealShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount not equals to DEFAULT_DISCOUNT
        defaultFlashDealShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the flashDealList where discount not equals to UPDATED_DISCOUNT
        defaultFlashDealShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultFlashDealShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the flashDealList where discount equals to UPDATED_DISCOUNT
        defaultFlashDealShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount is not null
        defaultFlashDealShouldBeFound("discount.specified=true");

        // Get all the flashDealList where discount is null
        defaultFlashDealShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount contains DEFAULT_DISCOUNT
        defaultFlashDealShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the flashDealList where discount contains UPDATED_DISCOUNT
        defaultFlashDealShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllFlashDealsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where discount does not contain DEFAULT_DISCOUNT
        defaultFlashDealShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the flashDealList where discount does not contain UPDATED_DISCOUNT
        defaultFlashDealShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active equals to DEFAULT_ACTIVE
        defaultFlashDealShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the flashDealList where active equals to UPDATED_ACTIVE
        defaultFlashDealShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active not equals to DEFAULT_ACTIVE
        defaultFlashDealShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the flashDealList where active not equals to UPDATED_ACTIVE
        defaultFlashDealShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultFlashDealShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the flashDealList where active equals to UPDATED_ACTIVE
        defaultFlashDealShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active is not null
        defaultFlashDealShouldBeFound("active.specified=true");

        // Get all the flashDealList where active is null
        defaultFlashDealShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active contains DEFAULT_ACTIVE
        defaultFlashDealShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the flashDealList where active contains UPDATED_ACTIVE
        defaultFlashDealShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where active does not contain DEFAULT_ACTIVE
        defaultFlashDealShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the flashDealList where active does not contain UPDATED_ACTIVE
        defaultFlashDealShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where position equals to DEFAULT_POSITION
        defaultFlashDealShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the flashDealList where position equals to UPDATED_POSITION
        defaultFlashDealShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where position not equals to DEFAULT_POSITION
        defaultFlashDealShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the flashDealList where position not equals to UPDATED_POSITION
        defaultFlashDealShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultFlashDealShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the flashDealList where position equals to UPDATED_POSITION
        defaultFlashDealShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where position is not null
        defaultFlashDealShouldBeFound("position.specified=true");

        // Get all the flashDealList where position is null
        defaultFlashDealShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where approved equals to DEFAULT_APPROVED
        defaultFlashDealShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the flashDealList where approved equals to UPDATED_APPROVED
        defaultFlashDealShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllFlashDealsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where approved not equals to DEFAULT_APPROVED
        defaultFlashDealShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the flashDealList where approved not equals to UPDATED_APPROVED
        defaultFlashDealShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllFlashDealsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultFlashDealShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the flashDealList where approved equals to UPDATED_APPROVED
        defaultFlashDealShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllFlashDealsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where approved is not null
        defaultFlashDealShouldBeFound("approved.specified=true");

        // Get all the flashDealList where approved is null
        defaultFlashDealShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country equals to DEFAULT_COUNTRY
        defaultFlashDealShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the flashDealList where country equals to UPDATED_COUNTRY
        defaultFlashDealShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country not equals to DEFAULT_COUNTRY
        defaultFlashDealShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the flashDealList where country not equals to UPDATED_COUNTRY
        defaultFlashDealShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultFlashDealShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the flashDealList where country equals to UPDATED_COUNTRY
        defaultFlashDealShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country is not null
        defaultFlashDealShouldBeFound("country.specified=true");

        // Get all the flashDealList where country is null
        defaultFlashDealShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country contains DEFAULT_COUNTRY
        defaultFlashDealShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the flashDealList where country contains UPDATED_COUNTRY
        defaultFlashDealShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where country does not contain DEFAULT_COUNTRY
        defaultFlashDealShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the flashDealList where country does not contain UPDATED_COUNTRY
        defaultFlashDealShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city equals to DEFAULT_CITY
        defaultFlashDealShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the flashDealList where city equals to UPDATED_CITY
        defaultFlashDealShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city not equals to DEFAULT_CITY
        defaultFlashDealShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the flashDealList where city not equals to UPDATED_CITY
        defaultFlashDealShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city in DEFAULT_CITY or UPDATED_CITY
        defaultFlashDealShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the flashDealList where city equals to UPDATED_CITY
        defaultFlashDealShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city is not null
        defaultFlashDealShouldBeFound("city.specified=true");

        // Get all the flashDealList where city is null
        defaultFlashDealShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city contains DEFAULT_CITY
        defaultFlashDealShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the flashDealList where city contains UPDATED_CITY
        defaultFlashDealShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where city does not contain DEFAULT_CITY
        defaultFlashDealShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the flashDealList where city does not contain UPDATED_CITY
        defaultFlashDealShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode equals to DEFAULT_PIN_CODE
        defaultFlashDealShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the flashDealList where pinCode equals to UPDATED_PIN_CODE
        defaultFlashDealShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode not equals to DEFAULT_PIN_CODE
        defaultFlashDealShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the flashDealList where pinCode not equals to UPDATED_PIN_CODE
        defaultFlashDealShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultFlashDealShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the flashDealList where pinCode equals to UPDATED_PIN_CODE
        defaultFlashDealShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode is not null
        defaultFlashDealShouldBeFound("pinCode.specified=true");

        // Get all the flashDealList where pinCode is null
        defaultFlashDealShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode contains DEFAULT_PIN_CODE
        defaultFlashDealShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the flashDealList where pinCode contains UPDATED_PIN_CODE
        defaultFlashDealShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        // Get all the flashDealList where pinCode does not contain DEFAULT_PIN_CODE
        defaultFlashDealShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the flashDealList where pinCode does not contain UPDATED_PIN_CODE
        defaultFlashDealShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllFlashDealsByMerchantDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);
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
        flashDeal.setMerchantDetails(merchantDetails);
        flashDealRepository.saveAndFlush(flashDeal);
        Long merchantDetailsId = merchantDetails.getId();

        // Get all the flashDealList where merchantDetails equals to merchantDetailsId
        defaultFlashDealShouldBeFound("merchantDetailsId.equals=" + merchantDetailsId);

        // Get all the flashDealList where merchantDetails equals to (merchantDetailsId + 1)
        defaultFlashDealShouldNotBeFound("merchantDetailsId.equals=" + (merchantDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllFlashDealsByDealCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);
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
        flashDeal.setDealCategory(dealCategory);
        flashDealRepository.saveAndFlush(flashDeal);
        Long dealCategoryId = dealCategory.getId();

        // Get all the flashDealList where dealCategory equals to dealCategoryId
        defaultFlashDealShouldBeFound("dealCategoryId.equals=" + dealCategoryId);

        // Get all the flashDealList where dealCategory equals to (dealCategoryId + 1)
        defaultFlashDealShouldNotBeFound("dealCategoryId.equals=" + (dealCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFlashDealShouldBeFound(String filter) throws Exception {
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flashDeal.getId().intValue())))
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
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFlashDealShouldNotBeFound(String filter) throws Exception {
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFlashDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFlashDeal() throws Exception {
        // Get the flashDeal
        restFlashDealMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFlashDeal() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();

        // Update the flashDeal
        FlashDeal updatedFlashDeal = flashDealRepository.findById(flashDeal.getId()).get();
        // Disconnect from session so that the updates on updatedFlashDeal are not directly saved in db
        em.detach(updatedFlashDeal);
        updatedFlashDeal
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

        restFlashDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlashDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFlashDeal))
            )
            .andExpect(status().isOk());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
        FlashDeal testFlashDeal = flashDealList.get(flashDealList.size() - 1);
        assertThat(testFlashDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFlashDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFlashDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlashDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testFlashDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testFlashDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testFlashDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testFlashDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testFlashDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testFlashDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testFlashDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testFlashDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testFlashDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testFlashDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testFlashDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testFlashDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testFlashDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testFlashDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flashDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flashDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flashDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flashDeal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlashDealWithPatch() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();

        // Update the flashDeal using partial update
        FlashDeal partialUpdatedFlashDeal = new FlashDeal();
        partialUpdatedFlashDeal.setId(flashDeal.getId());

        partialUpdatedFlashDeal
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .endDate(UPDATED_END_DATE)
            .country(UPDATED_COUNTRY);

        restFlashDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlashDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlashDeal))
            )
            .andExpect(status().isOk());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
        FlashDeal testFlashDeal = flashDealList.get(flashDealList.size() - 1);
        assertThat(testFlashDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFlashDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFlashDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlashDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testFlashDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testFlashDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testFlashDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testFlashDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testFlashDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testFlashDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testFlashDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testFlashDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testFlashDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testFlashDeal.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testFlashDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testFlashDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testFlashDeal.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testFlashDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFlashDealWithPatch() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();

        // Update the flashDeal using partial update
        FlashDeal partialUpdatedFlashDeal = new FlashDeal();
        partialUpdatedFlashDeal.setId(flashDeal.getId());

        partialUpdatedFlashDeal
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

        restFlashDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlashDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlashDeal))
            )
            .andExpect(status().isOk());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
        FlashDeal testFlashDeal = flashDealList.get(flashDealList.size() - 1);
        assertThat(testFlashDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFlashDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFlashDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlashDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testFlashDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testFlashDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testFlashDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testFlashDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testFlashDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testFlashDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testFlashDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testFlashDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testFlashDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testFlashDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testFlashDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testFlashDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testFlashDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testFlashDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flashDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flashDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flashDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlashDeal() throws Exception {
        int databaseSizeBeforeUpdate = flashDealRepository.findAll().size();
        flashDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlashDealMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(flashDeal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlashDeal in the database
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlashDeal() throws Exception {
        // Initialize the database
        flashDealRepository.saveAndFlush(flashDeal);

        int databaseSizeBeforeDelete = flashDealRepository.findAll().size();

        // Delete the flashDeal
        restFlashDealMockMvc
            .perform(delete(ENTITY_API_URL_ID, flashDeal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FlashDeal> flashDealList = flashDealRepository.findAll();
        assertThat(flashDealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
