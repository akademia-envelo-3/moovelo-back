package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class InternalEventServiceTest {

    @Autowired
    private InternalEventService internalEventService;

    @Test
    void getAllInternalEventsTest() {
        // GIVEN

        // WHEN
        List<? extends Event> allInternalEvents = internalEventService.getAllInternalEvents();

        // THEN
        assertFalse(allInternalEvents.isEmpty());
        assertEquals(allInternalEvents.get(0).getEventType(), EventType.INTERNAL_EVENT);
    }
}