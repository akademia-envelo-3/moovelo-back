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
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.event.InternalEventService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class InternalEventController {
    private static final EventType eventType = EventType.INTERNAL_EVENT;
    EventMapperInterface eventMapperInterface;
    private EventMapperManager eventMapperManager;
    private InternalEventService<InternalEvent> internalEventService;
    private AuthorizationService authorizationService;

    @Autowired
    public InternalEventController(EventMapperManager eventMapperManager,
                                   InternalEventService<InternalEvent> internalEventService,
                                   AuthorizationService authorizationService) {
        this.eventMapperManager = eventMapperManager;
        this.internalEventService = internalEventService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/internalEvents")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<EventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("InternalEventController - createNewEvent()");
        eventMapperInterface = new EventMapper();
        Long basicUserId = authorizationService.getLoggedBasicUserId();

        InternalEvent mappedInternalEventFromRequest = eventMapperManager.mapEventRequestDtoToEventByEventType(eventRequestDto, eventType);
        InternalEvent createdInternalEvent = internalEventService.createNewEvent(mappedInternalEventFromRequest, eventType, basicUserId);
        EventResponseDto eventResponseDto = eventMapperManager.getMappedResponseForSpecificEvent(eventMapperInterface, createdInternalEvent);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdInternalEvent.getId())
                .toUri();

        log.info("EventController - () return createNewEvent() - dto {}", eventResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(eventResponseDto);
    }

    @GetMapping("/internalEvents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<EventListResponseDto>> getAllInternalEvents(
            String privacy,
            String group,
            String cat,
            Long groupId,
            String sort,
            String sortOrder,
            @RequestParam(defaultValue = "0") Integer page) {
        log.info("InternalEventController - getAllInternalEvents()");
        eventMapperInterface = new EventListMapper();

        Page<? extends InternalEvent> events = internalEventService.getAllEvents(privacy, group, cat, groupId, sort, sortOrder, page, eventType);

        Page<EventListResponseDto> eventsDto = eventMapperManager.mapEventToEventListResponseDto(events, eventMapperInterface);

        log.info("EventController - getAllEvents() return {}", eventsDto);
        return ResponseEntity.ok(eventsDto);
    }
}
