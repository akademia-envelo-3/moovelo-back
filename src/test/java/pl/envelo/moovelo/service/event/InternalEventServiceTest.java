package pl.envelo.moovelo.service.event;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.event.EventController;
import pl.envelo.moovelo.controller.event.InternalEventController;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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