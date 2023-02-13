package pl.envelo.moovelo.controller.event;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventController eventController;

    @Test
    void getAllEventsPositiveTest() throws Exception {
        mvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].city", is("Warsaw")));
    }

    @Disabled
    void getAllEventsNegativeTest() throws Exception {
        //TODO: check the case when Admin is not logged in.
    }

    @Test
    @Transactional
    void getAllEventsUnitTest() {
        // GIVEN

        // WHEN
        ResponseEntity<List<EventListResponseDto>> result = eventController.getAllEvents();

        // THEN
        assertTrue(result.hasBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertTrue(Objects.requireNonNull(result.getBody()).size() > 0);
    }

    @Test
    void getEventByIdPositiveTest() throws Exception {
        mvc.perform(get("/api/v1/events/{eventId}", "2"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventOwner.userId", is(1)))
                .andExpect(jsonPath("$.eventInfo.name", is("Testowy Internal event")));
    }

    @Test
    @Transactional
    void getEventByIdUnitTest() {
        //given

        //when
        ResponseEntity<DisplayEventResponseDto> result = eventController.getEventById(1L);

        //then
        assertTrue(result.hasBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    //TODO: stworzyć test dla removeEvent kiedy ogarniemy Spring security

    @Test
    @Disabled
    void removeEventByIdPositiveTest() {
    }

    @Test
    @Disabled
    void removeEventByIdNegativeTest() {
    }

    @Test
    @Disabled
    void removeEventByIdUnitTest() {
    }

    @Test
    void getAllEventsByEventOwnerBasicUserIdPositiveTest() throws Exception {
        mvc.perform(get("/api/v1/events/eventOwners/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].eventOwner.userId", is(1)));
    }

    @Disabled
    void getAllEventsByEventOwnerBasicUserIdNegativeTest() throws Exception {
        //TODO: Napisać test kiedy ogarniemy spring security
    }

    @Test
    @Transactional
    void getAllEventsByEventOwnerBasicUserIdUnitTest() {
        // GIVEN

        // WHEN
        ResponseEntity<List<EventListResponseDto>> result = eventController.getAllEventsByEventOwnerBasicUserId(1L);

        // THEN
        assertTrue(result.hasBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertTrue(Objects.requireNonNull(result.getBody()).size() > 0);
        assertEquals(1L, result.getBody().get(0).getEventOwner().getUserId());
    }
}