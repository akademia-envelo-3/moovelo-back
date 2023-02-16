package pl.envelo.moovelo.controller.event;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.service.event.InternalEventService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class InternalEventController {

    private static final Long USER_ID = 1L;
    private InternalEventService<InternalEvent> internalEventService;

    @PostMapping("/internalEvents")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<DisplayEventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("InternalEventController - createNewEvent()");

        //TODO do powalczenia z wyborem Rodzaju eventu? albo usunac
        EventMapperInterface eventMapper = new EventMapper();
        InternalEvent internalEvent = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.INTERNAL_EVENT);
        InternalEvent newEvent =  internalEventService.createNewEvent(internalEvent, USER_ID);
        DisplayEventResponseDto displayEventResponseDto = EventMapper.mapInternalEventToEventResponseDto(newEvent);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEvent.getId())
                .toUri();

        log.info("EventController - () return createNewEvent() - dto {}", displayEventResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(displayEventResponseDto);
    }

//    @GetMapping("/internalEvents")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<EventListResponseDto>> getAllInternalEvents() {
//        log.info("InternalEventController - getAllInternalEvents()");
//        List<? extends Event> allInternalEvents = internalEventService.getAllInternalEvents();
//
//        List<EventListResponseDto> internalEventsDto = allInternalEvents.stream().map(internalEvent -> switch (internalEvent.getEventType()) {
//            case INTERNAL_EVENT ->
//                    EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) internalEvent);
//            case CYCLIC_EVENT ->
//                    EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) internalEvent);
//            default -> throw new IllegalEventException("Unexpected value: " + internalEvent.getEventType());
//        }).toList();
//
//        log.info("InternalEventController - getAllInternalEvents() return {}", internalEventsDto);
//        return ResponseEntity.ok(internalEventsDto);
//    }
}
