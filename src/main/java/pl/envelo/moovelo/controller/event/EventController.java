package pl.envelo.moovelo.controller.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.event.EventService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {

    //    TODO : Tymaczasowa imitacja ID usera wyciaganego z security
    private static final Long USER_ID = 2L;
    private EventService eventService;
    private AuthenticatedUser authenticatedUser;

    @PostMapping("/events")
    public ResponseEntity<DisplayEventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("EventController - createNewEvent()");
        //TODO do powalczenia z wyborem Rodzaju eventu? albo usunac
        EventMapperInterface eventMapper = new EventMapper();
        Event event = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.EVENT);
        Event newEvent = eventService.createNewEvent(event, USER_ID);
        DisplayEventResponseDto displayEventResponseDto = EventMapper.mapEventToEventResponseDto(newEvent);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEvent.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(displayEventResponseDto);
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventListResponseDto>> getAllEvents() {
        log.info("EventController - getAllEvents()");
        List<? extends Event> allEvents = eventService.getAllEvents();

        List<EventListResponseDto> eventsDto = mapEventToEventListResponseDto(allEvents);

        log.info("EventController - getAllEvents() return {}", eventsDto);
        return ResponseEntity.ok(eventsDto);
    }

    @GetMapping("events/eventOwners/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<EventListResponseDto>> getAllEventsByEventOwnerBasicUserId(@PathVariable("userId") Long basicUserId) {
        log.info("EventController - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);

        User user = authenticatedUser.getAuthenticatedUser();
        if (user.getRole().equals(Role.ROLE_USER) && !user.getId().equals(basicUserId)) {
            throw new UnauthorizedRequestException("Access denied");
        }

        List<? extends Event> allEvents = eventService.getAllEventsByEventOwnerBasicUserId(basicUserId);

        List<EventListResponseDto> eventsDto = mapEventToEventListResponseDto(allEvents);

        return ResponseEntity.ok(eventsDto);
    }

    private List<EventListResponseDto> mapEventToEventListResponseDto(List<? extends Event> allEvents) {
        List<EventListResponseDto> eventsDto = allEvents.stream().map(event -> switch (event.getEventType()) {
            case EVENT -> EventListResponseMapper.mapBasicEventToEventListResponseDto(event);
            case INTERNAL_EVENT ->
                    EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) event);
            case CYCLIC_EVENT -> EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) event);
            case EXTERNAL_EVENT ->
                    EventListResponseMapper.mapExternalEventToEventListResponseDto((ExternalEvent) event);
        }).toList();
        return eventsDto;
    }

    //TODO Sprawdzić czy user ma dostęp do tego wydarzenia
    @GetMapping("/events/{eventId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<DisplayEventResponseDto> getEventById(@PathVariable Long eventId) {
        log.info("EventController - getEventById()");
        Event eventById = eventService.getEventById(eventId);
        DisplayEventResponseDto displayEventResponseDto = null;
        switch (eventById.getEventType()) {
            case EVENT -> displayEventResponseDto = EventMapper.mapEventToEventResponseDto(eventById);
            case EXTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapExternalEventToEventResponseDto((ExternalEvent) eventById);
            case INTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapInternalEventToEventResponseDto((InternalEvent) eventById);
            case CYCLIC_EVENT ->
                    displayEventResponseDto = EventMapper.mapCyclicEventToEventResponseDto((CyclicEvent) eventById);
        }
        log.info("EventController - getEventById() return {}", displayEventResponseDto);
        return displayEventResponseDto == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(displayEventResponseDto);
    }
}
