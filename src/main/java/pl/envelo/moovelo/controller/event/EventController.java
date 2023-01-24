package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.service.event.EventService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<?>> getAllEvents() {
        log.info("EventController - getAllEvents()");
        List<?> allEvents = eventService.getAllEvents();

        log.info("EventController - getAllEvents() return {}", allEvents.toString());
        return ResponseEntity.ok(allEvents);
    }
}
