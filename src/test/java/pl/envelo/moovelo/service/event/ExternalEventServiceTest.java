package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExternalEventServiceTest {

    @Autowired
    private ExternalEventService externalEventService;

    @Test
    void getAllExternalEventsTest() {
        // GIVEN

        // WHEN
        List<? extends Event> allExternalEvents = externalEventService.getAllExternalEvents();

        // THEN
        assertFalse(allExternalEvents.isEmpty());
        assertEquals(allExternalEvents.get(0).getEventType(), EventType.EXTERNAL_EVENT);
    }
}