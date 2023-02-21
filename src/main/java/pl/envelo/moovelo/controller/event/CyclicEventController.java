package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.service.event.CyclicEventService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class CyclicEventController {
    private static final EventType eventType = EventType.CYCLIC_EVENT;
    private static final Long USER_ID = 1L;
    private CyclicEventService cyclicEventService;

    @Autowired
    public CyclicEventController(CyclicEventService cyclicEventService) {
        this.cyclicEventService = cyclicEventService;
    }

//    @PostMapping("/cyclicEvents")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<EventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
//        log.info("CyclicEventController - createNewEvent()");
//
//        //TODO do powalczenia z wyborem Rodzaju eventu? albo usunac
//        EventMapperManager eventMapperManager = new EventMapperManager();
//        CyclicEvent event = eventMapperManager.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.CYCLIC_EVENT);
//        CyclicEvent newEvent = cyclicEventService.createNewEvent(event, eventType, USER_ID);
//        EventResponseDto eventResponseDto = eventMapperManager.mapCyclicEventToEventResponseDto(newEvent);
//
//        URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newEvent.getId())
//                .toUri();
//
//        log.info("CyclicEventController - () return createNewEvent() - dto {}", eventResponseDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .location(uri)
//                .body(eventResponseDto);
//    }

//    @GetMapping("/cyclicEvents")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<EventListResponseDto>> getAllCyclicEvents() {
//        log.info("CyclicEventController - getAllCyclicEvents()");
//        List<CyclicEvent> allCyclicEvents = cyclicEventService.getAllCyclicEvents();
//
//        List<EventListResponseDto> cyclicEventsDto = allCyclicEvents.stream()
//                .map(EventListResponseMapper::mapCyclicEventToEventListResponseDto).toList();
//
//        log.info("CyclicEventController - getAllCyclicEvents() return {}", cyclicEventsDto);
//        return ResponseEntity.ok(cyclicEventsDto);
//    }
}
