package com.amudhan.schedulerservice.web.rest;

import com.amudhan.schedulerservice.SchedulerserviceApp;

import com.amudhan.schedulerservice.domain.Event;
import com.amudhan.schedulerservice.repository.EventRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = SchedulerserviceApp.class)

public class EventResourceIntTest {
    private static final String DEFAULT_WORKER = "AAAAA";
    private static final String UPDATED_WORKER = "BBBBB";
    private static final String DEFAULT_ROLE = "AAAAA";
    private static final String UPDATED_ROLE = "BBBBB";
    private static final String DEFAULT_TEAM = "AAAAA";
    private static final String UPDATED_TEAM = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";
    private static final String DEFAULT_OUTCOME = "AAAAA";
    private static final String UPDATED_OUTCOME = "BBBBB";

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private EventRepository eventRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEventMockMvc;

    private Event event;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventResource eventResource = new EventResource();
        ReflectionTestUtils.setField(eventResource, "eventRepository", eventRepository);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
                .worker(DEFAULT_WORKER)
                .role(DEFAULT_ROLE)
                .team(DEFAULT_TEAM)
                .location(DEFAULT_LOCATION)
                .status(DEFAULT_STATUS)
                .outcome(DEFAULT_OUTCOME)
                .start(DEFAULT_START)
                .end(DEFAULT_END);
        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event

        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(event)))
                .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getWorker()).isEqualTo(DEFAULT_WORKER);
        assertThat(testEvent.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testEvent.getTeam()).isEqualTo(DEFAULT_TEAM);
        assertThat(testEvent.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEvent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEvent.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testEvent.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testEvent.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the events
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
                .andExpect(jsonPath("$.[*].worker").value(hasItem(DEFAULT_WORKER.toString())))
                .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
                .andExpect(jsonPath("$.[*].team").value(hasItem(DEFAULT_TEAM.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME.toString())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.worker").value(DEFAULT_WORKER.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.team").value(DEFAULT_TEAM.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findOne(event.getId());
        updatedEvent
                .worker(UPDATED_WORKER)
                .role(UPDATED_ROLE)
                .team(UPDATED_TEAM)
                .location(UPDATED_LOCATION)
                .status(UPDATED_STATUS)
                .outcome(UPDATED_OUTCOME)
                .start(UPDATED_START)
                .end(UPDATED_END);

        restEventMockMvc.perform(put("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
                .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getWorker()).isEqualTo(UPDATED_WORKER);
        assertThat(testEvent.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testEvent.getTeam()).isEqualTo(UPDATED_TEAM);
        assertThat(testEvent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testEvent.getStart()).isEqualTo(UPDATED_START);
        assertThat(testEvent.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeDelete - 1);
    }
}
