package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventResponseDto;
import pl.envelo.moovelo.controller.mapper.EventMapper;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.exception.IllegalEventException;
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
    public ResponseEntity<List<EventListResponseDto>> getAllEvents() {
        log.info("EventController - getAllEvents()");
        List<? extends Event> allBasicEvents = eventService.getAllEvents();

        List<EventListResponseDto> eventsDto = allBasicEvents.stream().map(event -> {
            return switch (event.getEventType()) {
                case EVENT -> EventMapper.mapBasicEventToEventListResponseDto(event);
                case INTERNAL_EVENT -> EventMapper.mapInternalEventToEventListResponseDto((InternalEvent) event);
                case CYCLIC_EVENT -> EventMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) event);
                case EXTERNAL_EVENT -> EventMapper.mapExternalEventToEventListResponseDto((ExternalEvent) event);
                default -> throw new IllegalEventException("Event does not contain EvenType property");
            };
        }).toList();

        log.info("EventController - getAllEvents() return {}", eventsDto.toString());
        return ResponseEntity.ok(eventsDto);
    }
}
