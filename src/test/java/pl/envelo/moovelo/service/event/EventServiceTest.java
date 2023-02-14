package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.exception.NoContentException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    void getAllEventsTest() {
        // GIVEN

        // WHEN
        List<? extends Event> allEvents = eventService.getAllEvents();

        // THEN
        assertFalse(allEvents.isEmpty());
        assertEquals(allEvents.get(0).getEventType(), EventType.EVENT);
    }

    @Test
    @Transactional
    void removeEventByIdTest() {
        // GIVEN
        long eventId = 1;

        // WHEN
        eventService.removeEventById(eventId);

        // THEN
        assertThrows(NoSuchElementException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    @Transactional
    void removeEventByIdWhenEventDoesNotExistTest() {
        // GIVEN
        long eventId = 1;

        // WHEN
        eventService.removeEventById(eventId);

        // THEN
        assertThrows(NoContentException.class, () -> eventService.removeEventById(eventId));
    }

    void getAllEventsByEventOwnerBasicUserIdTest() {
        // GIVEN
        Long userId = 1L;

        // WHEN
        List<? extends Event> allEventOwnerEvents = eventService.getAllEventsByEventOwnerBasicUserId(userId);

        // THEN
        assertFalse(allEventOwnerEvents.isEmpty());
        assertEquals(allEventOwnerEvents.get(0).getEventOwner().getUserId(), userId);
    }
}