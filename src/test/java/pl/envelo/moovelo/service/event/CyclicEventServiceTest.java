package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CyclicEventServiceTest {

    @Autowired
    private CyclicEventService cyclicEventService;

    @Test
    void getAllCyclicEventsTest() {
        // GIVEN

        // WHEN
        List<? extends Event> allCyclicEvents = cyclicEventService.getAllCyclicEvents();

        // THEN
        assertFalse(allCyclicEvents.isEmpty());
        assertEquals(allCyclicEvents.get(0).getEventType(), EventType.CYCLIC_EVENT);
    }
}