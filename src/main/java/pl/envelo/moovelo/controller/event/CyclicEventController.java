package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.event.response.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.response.EventResponseDto;
import pl.envelo.moovelo.controller.mapper.event.EventListMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.controller.mapper.event.manager.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.manager.EventMapperManager;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.event.CyclicEventService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class CyclicEventController {
    private static final EventType eventType = EventType.CYCLIC_EVENT;
    EventMapperInterface eventMapperInterface;
    private EventMapperManager eventMapperManager;
    private CyclicEventService cyclicEventService;
    private AuthorizationService authorizationService;

    @Autowired
    public CyclicEventController(EventMapperManager eventMapperManager, CyclicEventService cyclicEventService, AuthorizationService authorizationService) {
        this.eventMapperManager = eventMapperManager;
        this.cyclicEventService = cyclicEventService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/cyclicEvents")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<EventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("CyclicEventController - createNewEvent()");
        eventMapperInterface = new EventMapper();
        Long basicUserId = authorizationService.getLoggedBasicUserId();

        CyclicEvent event = eventMapperManager.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.CYCLIC_EVENT);
        CyclicEvent createdCyclicEvent = cyclicEventService.createNewEvent(event, eventType, basicUserId);
        EventResponseDto eventResponseDto = eventMapperManager.getMappedResponseForSpecificEvent(eventMapperInterface, createdCyclicEvent);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCyclicEvent.getId())
                .toUri();

        log.info("CyclicEventController - () return createNewEvent() - dto {}", eventResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(eventResponseDto);
    }

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

    @GetMapping("/cyclicEvents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<EventListResponseDto>> getAllCyclicEvents(
            String privacy,
            String group,
            String cat,
            Long groupId,
            String sort,
            String sortOrder,
            @RequestParam(defaultValue = "0") Integer page) {
        log.info("InternalEventController - getAllEvents()");
        eventMapperInterface = new EventListMapper();

        Page<CyclicEvent> events = cyclicEventService.getAllEvents(privacy, group, cat, groupId, sort, sortOrder, page, eventType);

        Page<EventListResponseDto> eventsDto = eventMapperManager.mapEventToEventListResponseDto(events, eventMapperInterface);

        log.info("EventController - getAllEvents() return {}", eventsDto);
        return ResponseEntity.ok(eventsDto);
    }
}
