package com.cloudfen.flashadeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cloudfen.flashadeal.IntegrationTest;
import com.cloudfen.flashadeal.domain.DbCarouselDeal;
import com.cloudfen.flashadeal.domain.DealCategory;
import com.cloudfen.flashadeal.domain.MerchantDetails;
import com.cloudfen.flashadeal.domain.enumeration.DealDisplayPostion;
import com.cloudfen.flashadeal.domain.enumeration.DealType;
import com.cloudfen.flashadeal.repository.DbCarouselDealRepository;
import com.cloudfen.flashadeal.service.criteria.DbCarouselDealCriteria;
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
 * Integration tests for the {@link DbCarouselDealResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DbCarouselDealResourceIT {

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

    private static final String ENTITY_API_URL = "/api/db-carousel-deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DbCarouselDealRepository dbCarouselDealRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDbCarouselDealMockMvc;

    private DbCarouselDeal dbCarouselDeal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DbCarouselDeal createEntity(EntityManager em) {
        DbCarouselDeal dbCarouselDeal = new DbCarouselDeal()
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
        return dbCarouselDeal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DbCarouselDeal createUpdatedEntity(EntityManager em) {
        DbCarouselDeal dbCarouselDeal = new DbCarouselDeal()
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
        return dbCarouselDeal;
    }

    @BeforeEach
    public void initTest() {
        dbCarouselDeal = createEntity(em);
    }

    @Test
    @Transactional
    void createDbCarouselDeal() throws Exception {
        int databaseSizeBeforeCreate = dbCarouselDealRepository.findAll().size();
        // Create the DbCarouselDeal
        restDbCarouselDealMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isCreated());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeCreate + 1);
        DbCarouselDeal testDbCarouselDeal = dbCarouselDealList.get(dbCarouselDealList.size() - 1);
        assertThat(testDbCarouselDeal.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDbCarouselDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDbCarouselDeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDbCarouselDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testDbCarouselDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testDbCarouselDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testDbCarouselDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testDbCarouselDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDbCarouselDeal.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDbCarouselDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testDbCarouselDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testDbCarouselDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDbCarouselDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDbCarouselDeal.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testDbCarouselDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testDbCarouselDeal.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDbCarouselDeal.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDbCarouselDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
    }

    @Test
    @Transactional
    void createDbCarouselDealWithExistingId() throws Exception {
        // Create the DbCarouselDeal with an existing ID
        dbCarouselDeal.setId(1L);

        int databaseSizeBeforeCreate = dbCarouselDealRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDbCarouselDealMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDbCarouselDeals() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dbCarouselDeal.getId().intValue())))
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
    void getDbCarouselDeal() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get the dbCarouselDeal
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL_ID, dbCarouselDeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dbCarouselDeal.getId().intValue()))
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
    void getDbCarouselDealsByIdFiltering() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        Long id = dbCarouselDeal.getId();

        defaultDbCarouselDealShouldBeFound("id.equals=" + id);
        defaultDbCarouselDealShouldNotBeFound("id.notEquals=" + id);

        defaultDbCarouselDealShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDbCarouselDealShouldNotBeFound("id.greaterThan=" + id);

        defaultDbCarouselDealShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDbCarouselDealShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where type equals to DEFAULT_TYPE
        defaultDbCarouselDealShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dbCarouselDealList where type equals to UPDATED_TYPE
        defaultDbCarouselDealShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where type not equals to DEFAULT_TYPE
        defaultDbCarouselDealShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the dbCarouselDealList where type not equals to UPDATED_TYPE
        defaultDbCarouselDealShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDbCarouselDealShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dbCarouselDealList where type equals to UPDATED_TYPE
        defaultDbCarouselDealShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where type is not null
        defaultDbCarouselDealShouldBeFound("type.specified=true");

        // Get all the dbCarouselDealList where type is null
        defaultDbCarouselDealShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title equals to DEFAULT_TITLE
        defaultDbCarouselDealShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dbCarouselDealList where title equals to UPDATED_TITLE
        defaultDbCarouselDealShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title not equals to DEFAULT_TITLE
        defaultDbCarouselDealShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the dbCarouselDealList where title not equals to UPDATED_TITLE
        defaultDbCarouselDealShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDbCarouselDealShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dbCarouselDealList where title equals to UPDATED_TITLE
        defaultDbCarouselDealShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title is not null
        defaultDbCarouselDealShouldBeFound("title.specified=true");

        // Get all the dbCarouselDealList where title is null
        defaultDbCarouselDealShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title contains DEFAULT_TITLE
        defaultDbCarouselDealShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the dbCarouselDealList where title contains UPDATED_TITLE
        defaultDbCarouselDealShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where title does not contain DEFAULT_TITLE
        defaultDbCarouselDealShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the dbCarouselDealList where title does not contain UPDATED_TITLE
        defaultDbCarouselDealShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy equals to DEFAULT_POSTED_BY
        defaultDbCarouselDealShouldBeFound("postedBy.equals=" + DEFAULT_POSTED_BY);

        // Get all the dbCarouselDealList where postedBy equals to UPDATED_POSTED_BY
        defaultDbCarouselDealShouldNotBeFound("postedBy.equals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy not equals to DEFAULT_POSTED_BY
        defaultDbCarouselDealShouldNotBeFound("postedBy.notEquals=" + DEFAULT_POSTED_BY);

        // Get all the dbCarouselDealList where postedBy not equals to UPDATED_POSTED_BY
        defaultDbCarouselDealShouldBeFound("postedBy.notEquals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy in DEFAULT_POSTED_BY or UPDATED_POSTED_BY
        defaultDbCarouselDealShouldBeFound("postedBy.in=" + DEFAULT_POSTED_BY + "," + UPDATED_POSTED_BY);

        // Get all the dbCarouselDealList where postedBy equals to UPDATED_POSTED_BY
        defaultDbCarouselDealShouldNotBeFound("postedBy.in=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy is not null
        defaultDbCarouselDealShouldBeFound("postedBy.specified=true");

        // Get all the dbCarouselDealList where postedBy is null
        defaultDbCarouselDealShouldNotBeFound("postedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy contains DEFAULT_POSTED_BY
        defaultDbCarouselDealShouldBeFound("postedBy.contains=" + DEFAULT_POSTED_BY);

        // Get all the dbCarouselDealList where postedBy contains UPDATED_POSTED_BY
        defaultDbCarouselDealShouldNotBeFound("postedBy.contains=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedByNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedBy does not contain DEFAULT_POSTED_BY
        defaultDbCarouselDealShouldNotBeFound("postedBy.doesNotContain=" + DEFAULT_POSTED_BY);

        // Get all the dbCarouselDealList where postedBy does not contain UPDATED_POSTED_BY
        defaultDbCarouselDealShouldBeFound("postedBy.doesNotContain=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate equals to DEFAULT_POSTED_DATE
        defaultDbCarouselDealShouldBeFound("postedDate.equals=" + DEFAULT_POSTED_DATE);

        // Get all the dbCarouselDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldNotBeFound("postedDate.equals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate not equals to DEFAULT_POSTED_DATE
        defaultDbCarouselDealShouldNotBeFound("postedDate.notEquals=" + DEFAULT_POSTED_DATE);

        // Get all the dbCarouselDealList where postedDate not equals to UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldBeFound("postedDate.notEquals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate in DEFAULT_POSTED_DATE or UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldBeFound("postedDate.in=" + DEFAULT_POSTED_DATE + "," + UPDATED_POSTED_DATE);

        // Get all the dbCarouselDealList where postedDate equals to UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldNotBeFound("postedDate.in=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate is not null
        defaultDbCarouselDealShouldBeFound("postedDate.specified=true");

        // Get all the dbCarouselDealList where postedDate is null
        defaultDbCarouselDealShouldNotBeFound("postedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate contains DEFAULT_POSTED_DATE
        defaultDbCarouselDealShouldBeFound("postedDate.contains=" + DEFAULT_POSTED_DATE);

        // Get all the dbCarouselDealList where postedDate contains UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldNotBeFound("postedDate.contains=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPostedDateNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where postedDate does not contain DEFAULT_POSTED_DATE
        defaultDbCarouselDealShouldNotBeFound("postedDate.doesNotContain=" + DEFAULT_POSTED_DATE);

        // Get all the dbCarouselDealList where postedDate does not contain UPDATED_POSTED_DATE
        defaultDbCarouselDealShouldBeFound("postedDate.doesNotContain=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate equals to DEFAULT_START_DATE
        defaultDbCarouselDealShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the dbCarouselDealList where startDate equals to UPDATED_START_DATE
        defaultDbCarouselDealShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate not equals to DEFAULT_START_DATE
        defaultDbCarouselDealShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the dbCarouselDealList where startDate not equals to UPDATED_START_DATE
        defaultDbCarouselDealShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultDbCarouselDealShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the dbCarouselDealList where startDate equals to UPDATED_START_DATE
        defaultDbCarouselDealShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate is not null
        defaultDbCarouselDealShouldBeFound("startDate.specified=true");

        // Get all the dbCarouselDealList where startDate is null
        defaultDbCarouselDealShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate contains DEFAULT_START_DATE
        defaultDbCarouselDealShouldBeFound("startDate.contains=" + DEFAULT_START_DATE);

        // Get all the dbCarouselDealList where startDate contains UPDATED_START_DATE
        defaultDbCarouselDealShouldNotBeFound("startDate.contains=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where startDate does not contain DEFAULT_START_DATE
        defaultDbCarouselDealShouldNotBeFound("startDate.doesNotContain=" + DEFAULT_START_DATE);

        // Get all the dbCarouselDealList where startDate does not contain UPDATED_START_DATE
        defaultDbCarouselDealShouldBeFound("startDate.doesNotContain=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate equals to DEFAULT_END_DATE
        defaultDbCarouselDealShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the dbCarouselDealList where endDate equals to UPDATED_END_DATE
        defaultDbCarouselDealShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate not equals to DEFAULT_END_DATE
        defaultDbCarouselDealShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the dbCarouselDealList where endDate not equals to UPDATED_END_DATE
        defaultDbCarouselDealShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultDbCarouselDealShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the dbCarouselDealList where endDate equals to UPDATED_END_DATE
        defaultDbCarouselDealShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate is not null
        defaultDbCarouselDealShouldBeFound("endDate.specified=true");

        // Get all the dbCarouselDealList where endDate is null
        defaultDbCarouselDealShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate contains DEFAULT_END_DATE
        defaultDbCarouselDealShouldBeFound("endDate.contains=" + DEFAULT_END_DATE);

        // Get all the dbCarouselDealList where endDate contains UPDATED_END_DATE
        defaultDbCarouselDealShouldNotBeFound("endDate.contains=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where endDate does not contain DEFAULT_END_DATE
        defaultDbCarouselDealShouldNotBeFound("endDate.doesNotContain=" + DEFAULT_END_DATE);

        // Get all the dbCarouselDealList where endDate does not contain UPDATED_END_DATE
        defaultDbCarouselDealShouldBeFound("endDate.doesNotContain=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultDbCarouselDealShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dbCarouselDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice not equals to DEFAULT_ORIGINAL_PRICE
        defaultDbCarouselDealShouldNotBeFound("originalPrice.notEquals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dbCarouselDealList where originalPrice not equals to UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldBeFound("originalPrice.notEquals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the dbCarouselDealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice is not null
        defaultDbCarouselDealShouldBeFound("originalPrice.specified=true");

        // Get all the dbCarouselDealList where originalPrice is null
        defaultDbCarouselDealShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice contains DEFAULT_ORIGINAL_PRICE
        defaultDbCarouselDealShouldBeFound("originalPrice.contains=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dbCarouselDealList where originalPrice contains UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldNotBeFound("originalPrice.contains=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByOriginalPriceNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where originalPrice does not contain DEFAULT_ORIGINAL_PRICE
        defaultDbCarouselDealShouldNotBeFound("originalPrice.doesNotContain=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dbCarouselDealList where originalPrice does not contain UPDATED_ORIGINAL_PRICE
        defaultDbCarouselDealShouldBeFound("originalPrice.doesNotContain=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultDbCarouselDealShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the dbCarouselDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice not equals to DEFAULT_CURRENT_PRICE
        defaultDbCarouselDealShouldNotBeFound("currentPrice.notEquals=" + DEFAULT_CURRENT_PRICE);

        // Get all the dbCarouselDealList where currentPrice not equals to UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldBeFound("currentPrice.notEquals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the dbCarouselDealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice is not null
        defaultDbCarouselDealShouldBeFound("currentPrice.specified=true");

        // Get all the dbCarouselDealList where currentPrice is null
        defaultDbCarouselDealShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice contains DEFAULT_CURRENT_PRICE
        defaultDbCarouselDealShouldBeFound("currentPrice.contains=" + DEFAULT_CURRENT_PRICE);

        // Get all the dbCarouselDealList where currentPrice contains UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldNotBeFound("currentPrice.contains=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCurrentPriceNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where currentPrice does not contain DEFAULT_CURRENT_PRICE
        defaultDbCarouselDealShouldNotBeFound("currentPrice.doesNotContain=" + DEFAULT_CURRENT_PRICE);

        // Get all the dbCarouselDealList where currentPrice does not contain UPDATED_CURRENT_PRICE
        defaultDbCarouselDealShouldBeFound("currentPrice.doesNotContain=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount equals to DEFAULT_DISCOUNT
        defaultDbCarouselDealShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the dbCarouselDealList where discount equals to UPDATED_DISCOUNT
        defaultDbCarouselDealShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount not equals to DEFAULT_DISCOUNT
        defaultDbCarouselDealShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the dbCarouselDealList where discount not equals to UPDATED_DISCOUNT
        defaultDbCarouselDealShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultDbCarouselDealShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the dbCarouselDealList where discount equals to UPDATED_DISCOUNT
        defaultDbCarouselDealShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount is not null
        defaultDbCarouselDealShouldBeFound("discount.specified=true");

        // Get all the dbCarouselDealList where discount is null
        defaultDbCarouselDealShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount contains DEFAULT_DISCOUNT
        defaultDbCarouselDealShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the dbCarouselDealList where discount contains UPDATED_DISCOUNT
        defaultDbCarouselDealShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where discount does not contain DEFAULT_DISCOUNT
        defaultDbCarouselDealShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the dbCarouselDealList where discount does not contain UPDATED_DISCOUNT
        defaultDbCarouselDealShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active equals to DEFAULT_ACTIVE
        defaultDbCarouselDealShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the dbCarouselDealList where active equals to UPDATED_ACTIVE
        defaultDbCarouselDealShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active not equals to DEFAULT_ACTIVE
        defaultDbCarouselDealShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the dbCarouselDealList where active not equals to UPDATED_ACTIVE
        defaultDbCarouselDealShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultDbCarouselDealShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the dbCarouselDealList where active equals to UPDATED_ACTIVE
        defaultDbCarouselDealShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active is not null
        defaultDbCarouselDealShouldBeFound("active.specified=true");

        // Get all the dbCarouselDealList where active is null
        defaultDbCarouselDealShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active contains DEFAULT_ACTIVE
        defaultDbCarouselDealShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the dbCarouselDealList where active contains UPDATED_ACTIVE
        defaultDbCarouselDealShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where active does not contain DEFAULT_ACTIVE
        defaultDbCarouselDealShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the dbCarouselDealList where active does not contain UPDATED_ACTIVE
        defaultDbCarouselDealShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where position equals to DEFAULT_POSITION
        defaultDbCarouselDealShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the dbCarouselDealList where position equals to UPDATED_POSITION
        defaultDbCarouselDealShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where position not equals to DEFAULT_POSITION
        defaultDbCarouselDealShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the dbCarouselDealList where position not equals to UPDATED_POSITION
        defaultDbCarouselDealShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultDbCarouselDealShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the dbCarouselDealList where position equals to UPDATED_POSITION
        defaultDbCarouselDealShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where position is not null
        defaultDbCarouselDealShouldBeFound("position.specified=true");

        // Get all the dbCarouselDealList where position is null
        defaultDbCarouselDealShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where approved equals to DEFAULT_APPROVED
        defaultDbCarouselDealShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the dbCarouselDealList where approved equals to UPDATED_APPROVED
        defaultDbCarouselDealShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where approved not equals to DEFAULT_APPROVED
        defaultDbCarouselDealShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the dbCarouselDealList where approved not equals to UPDATED_APPROVED
        defaultDbCarouselDealShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultDbCarouselDealShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the dbCarouselDealList where approved equals to UPDATED_APPROVED
        defaultDbCarouselDealShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where approved is not null
        defaultDbCarouselDealShouldBeFound("approved.specified=true");

        // Get all the dbCarouselDealList where approved is null
        defaultDbCarouselDealShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country equals to DEFAULT_COUNTRY
        defaultDbCarouselDealShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the dbCarouselDealList where country equals to UPDATED_COUNTRY
        defaultDbCarouselDealShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country not equals to DEFAULT_COUNTRY
        defaultDbCarouselDealShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the dbCarouselDealList where country not equals to UPDATED_COUNTRY
        defaultDbCarouselDealShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultDbCarouselDealShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the dbCarouselDealList where country equals to UPDATED_COUNTRY
        defaultDbCarouselDealShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country is not null
        defaultDbCarouselDealShouldBeFound("country.specified=true");

        // Get all the dbCarouselDealList where country is null
        defaultDbCarouselDealShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country contains DEFAULT_COUNTRY
        defaultDbCarouselDealShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the dbCarouselDealList where country contains UPDATED_COUNTRY
        defaultDbCarouselDealShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where country does not contain DEFAULT_COUNTRY
        defaultDbCarouselDealShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the dbCarouselDealList where country does not contain UPDATED_COUNTRY
        defaultDbCarouselDealShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city equals to DEFAULT_CITY
        defaultDbCarouselDealShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the dbCarouselDealList where city equals to UPDATED_CITY
        defaultDbCarouselDealShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city not equals to DEFAULT_CITY
        defaultDbCarouselDealShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the dbCarouselDealList where city not equals to UPDATED_CITY
        defaultDbCarouselDealShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city in DEFAULT_CITY or UPDATED_CITY
        defaultDbCarouselDealShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the dbCarouselDealList where city equals to UPDATED_CITY
        defaultDbCarouselDealShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city is not null
        defaultDbCarouselDealShouldBeFound("city.specified=true");

        // Get all the dbCarouselDealList where city is null
        defaultDbCarouselDealShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city contains DEFAULT_CITY
        defaultDbCarouselDealShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the dbCarouselDealList where city contains UPDATED_CITY
        defaultDbCarouselDealShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where city does not contain DEFAULT_CITY
        defaultDbCarouselDealShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the dbCarouselDealList where city does not contain UPDATED_CITY
        defaultDbCarouselDealShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode equals to DEFAULT_PIN_CODE
        defaultDbCarouselDealShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the dbCarouselDealList where pinCode equals to UPDATED_PIN_CODE
        defaultDbCarouselDealShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode not equals to DEFAULT_PIN_CODE
        defaultDbCarouselDealShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the dbCarouselDealList where pinCode not equals to UPDATED_PIN_CODE
        defaultDbCarouselDealShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultDbCarouselDealShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the dbCarouselDealList where pinCode equals to UPDATED_PIN_CODE
        defaultDbCarouselDealShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode is not null
        defaultDbCarouselDealShouldBeFound("pinCode.specified=true");

        // Get all the dbCarouselDealList where pinCode is null
        defaultDbCarouselDealShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode contains DEFAULT_PIN_CODE
        defaultDbCarouselDealShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the dbCarouselDealList where pinCode contains UPDATED_PIN_CODE
        defaultDbCarouselDealShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        // Get all the dbCarouselDealList where pinCode does not contain DEFAULT_PIN_CODE
        defaultDbCarouselDealShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the dbCarouselDealList where pinCode does not contain UPDATED_PIN_CODE
        defaultDbCarouselDealShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByMerchantDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);
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
        dbCarouselDeal.setMerchantDetails(merchantDetails);
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);
        Long merchantDetailsId = merchantDetails.getId();

        // Get all the dbCarouselDealList where merchantDetails equals to merchantDetailsId
        defaultDbCarouselDealShouldBeFound("merchantDetailsId.equals=" + merchantDetailsId);

        // Get all the dbCarouselDealList where merchantDetails equals to (merchantDetailsId + 1)
        defaultDbCarouselDealShouldNotBeFound("merchantDetailsId.equals=" + (merchantDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllDbCarouselDealsByDealCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);
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
        dbCarouselDeal.setDealCategory(dealCategory);
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);
        Long dealCategoryId = dealCategory.getId();

        // Get all the dbCarouselDealList where dealCategory equals to dealCategoryId
        defaultDbCarouselDealShouldBeFound("dealCategoryId.equals=" + dealCategoryId);

        // Get all the dbCarouselDealList where dealCategory equals to (dealCategoryId + 1)
        defaultDbCarouselDealShouldNotBeFound("dealCategoryId.equals=" + (dealCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDbCarouselDealShouldBeFound(String filter) throws Exception {
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dbCarouselDeal.getId().intValue())))
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
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDbCarouselDealShouldNotBeFound(String filter) throws Exception {
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDbCarouselDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDbCarouselDeal() throws Exception {
        // Get the dbCarouselDeal
        restDbCarouselDealMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDbCarouselDeal() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();

        // Update the dbCarouselDeal
        DbCarouselDeal updatedDbCarouselDeal = dbCarouselDealRepository.findById(dbCarouselDeal.getId()).get();
        // Disconnect from session so that the updates on updatedDbCarouselDeal are not directly saved in db
        em.detach(updatedDbCarouselDeal);
        updatedDbCarouselDeal
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

        restDbCarouselDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDbCarouselDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDbCarouselDeal))
            )
            .andExpect(status().isOk());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
        DbCarouselDeal testDbCarouselDeal = dbCarouselDealList.get(dbCarouselDealList.size() - 1);
        assertThat(testDbCarouselDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDbCarouselDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDbCarouselDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDbCarouselDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testDbCarouselDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testDbCarouselDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testDbCarouselDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testDbCarouselDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDbCarouselDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDbCarouselDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testDbCarouselDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testDbCarouselDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDbCarouselDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDbCarouselDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testDbCarouselDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testDbCarouselDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDbCarouselDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDbCarouselDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void putNonExistingDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dbCarouselDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDbCarouselDealWithPatch() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();

        // Update the dbCarouselDeal using partial update
        DbCarouselDeal partialUpdatedDbCarouselDeal = new DbCarouselDeal();
        partialUpdatedDbCarouselDeal.setId(dbCarouselDeal.getId());

        partialUpdatedDbCarouselDeal
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .position(UPDATED_POSITION)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE);

        restDbCarouselDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDbCarouselDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDbCarouselDeal))
            )
            .andExpect(status().isOk());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
        DbCarouselDeal testDbCarouselDeal = dbCarouselDealList.get(dbCarouselDealList.size() - 1);
        assertThat(testDbCarouselDeal.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDbCarouselDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDbCarouselDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDbCarouselDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testDbCarouselDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testDbCarouselDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testDbCarouselDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testDbCarouselDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDbCarouselDeal.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDbCarouselDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testDbCarouselDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testDbCarouselDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDbCarouselDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDbCarouselDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testDbCarouselDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testDbCarouselDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDbCarouselDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDbCarouselDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void fullUpdateDbCarouselDealWithPatch() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();

        // Update the dbCarouselDeal using partial update
        DbCarouselDeal partialUpdatedDbCarouselDeal = new DbCarouselDeal();
        partialUpdatedDbCarouselDeal.setId(dbCarouselDeal.getId());

        partialUpdatedDbCarouselDeal
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

        restDbCarouselDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDbCarouselDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDbCarouselDeal))
            )
            .andExpect(status().isOk());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
        DbCarouselDeal testDbCarouselDeal = dbCarouselDealList.get(dbCarouselDealList.size() - 1);
        assertThat(testDbCarouselDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDbCarouselDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDbCarouselDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDbCarouselDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testDbCarouselDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testDbCarouselDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testDbCarouselDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testDbCarouselDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDbCarouselDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDbCarouselDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testDbCarouselDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testDbCarouselDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDbCarouselDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDbCarouselDeal.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testDbCarouselDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testDbCarouselDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDbCarouselDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDbCarouselDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dbCarouselDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isBadRequest());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDbCarouselDeal() throws Exception {
        int databaseSizeBeforeUpdate = dbCarouselDealRepository.findAll().size();
        dbCarouselDeal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDbCarouselDealMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dbCarouselDeal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DbCarouselDeal in the database
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDbCarouselDeal() throws Exception {
        // Initialize the database
        dbCarouselDealRepository.saveAndFlush(dbCarouselDeal);

        int databaseSizeBeforeDelete = dbCarouselDealRepository.findAll().size();

        // Delete the dbCarouselDeal
        restDbCarouselDealMockMvc
            .perform(delete(ENTITY_API_URL_ID, dbCarouselDeal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DbCarouselDeal> dbCarouselDealList = dbCarouselDealRepository.findAll();
        assertThat(dbCarouselDealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
