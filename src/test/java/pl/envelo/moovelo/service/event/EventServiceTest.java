package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

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
}