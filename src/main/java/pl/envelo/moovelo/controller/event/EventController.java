package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.event.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {

    private EventService eventService;
    private AuthenticatedUser authenticatedUser;

    @Autowired
    public EventController(EventService eventService, AuthenticatedUser authenticatedUser) {
        this.eventService = eventService;
        this.authenticatedUser = authenticatedUser;
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

    @DeleteMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeEventById(@PathVariable long eventId) throws IllegalAccessException {
        log.info("EventController - removeEventById() - eventId = {}", eventId);

        User user = authenticatedUser.getAuthenticatedUser();
        Event event = eventService.getEventById(eventId);

        if (!event.getEventOwner().getUserId().equals(user.getId())) {
            throw new UnauthorizedRequestException("Access denied!");
        }
        if (event.getEventInfo().getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalEventException("Can't delete an event that has already taken place!");
        }

        eventService.removeEventById(eventId);
        log.info("EventController - removeEventById() - event with eventId = {} removed", eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
            case EXTERNAL_EVENT ->  displayEventResponseDto = EventMapper.mapExternalEventToEventResponseDto((ExternalEvent) eventById);
            case INTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapInternalEventToEventResponseDto((InternalEvent) eventById);
            case CYCLIC_EVENT ->
                    displayEventResponseDto = EventMapper.mapCyclicEventToEventResponseDto((CyclicEvent) eventById);
        }
        log.info("EventController - getEventById() return {}", displayEventResponseDto);
        return displayEventResponseDto == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(displayEventResponseDto);
    }
}
