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

    @Test
    void getAllEventsByEventNameContainsTest() {

        //GIVEN
        String name = "ex";

        //WHEN
        List<? extends Event> eventsByNameContains = eventService.getAllEventsByEventNameContains(name);

        System.out.println("tests print");
        System.out.println(eventService.getAllEvents());
        System.out.println(eventService.getAllEventsByEventNameContains(name));

        //THEN
        assertFalse(eventsByNameContains.isEmpty());
        assertEquals(eventsByNameContains.size(), 1);
    }
}