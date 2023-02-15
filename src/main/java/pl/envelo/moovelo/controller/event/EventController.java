package pl.envelo.moovelo.controller.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnershipRequestDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.event.EventService;

import java.net.URI;
import java.time.LocalDateTime;
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
    private BasicUserService basicUserService;

    @PostMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER')")
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

        log.info("EventController - () return createNewEvent() - dto {}", displayEventResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(displayEventResponseDto);
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<EventListResponseDto>> getAllEvents(
            String privacy,
            String group,
            String cat,
            String sort,
            String sortOrder,
            @RequestParam(defaultValue = "0") Integer page) {
        log.info("EventController - getAllEvents()");

        Page<? extends Event> events = eventService.getAllEvents(privacy, group, cat, sort, sortOrder, page);

        Page<EventListResponseDto> eventsDto = mapEventToEventListResponseDto(events);

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

    private Page<EventListResponseDto> mapEventToEventListResponseDto(Page<? extends Event> allEvents) {
        Page<EventListResponseDto> eventsDto = allEvents.map(event -> switch (event.getEventType()) {
            case EVENT -> EventListResponseMapper.mapBasicEventToEventListResponseDto(event);
            case INTERNAL_EVENT ->
                    EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) event);
            case CYCLIC_EVENT -> EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) event);
            case EXTERNAL_EVENT ->
                    EventListResponseMapper.mapExternalEventToEventListResponseDto((ExternalEvent) event);
        });
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
            case EVENT ->
                    displayEventResponseDto = EventMapper.mapEventToEventResponseDto(eventById);
            case EXTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapExternalEventToEventResponseDto((ExternalEvent) eventById);
            case INTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapInternalEventToEventResponseDto((InternalEvent) eventById);
            case CYCLIC_EVENT ->
                    displayEventResponseDto = EventMapper.mapCyclicEventToEventResponseDto((CyclicEvent) eventById);
            default ->
                    throw new IllegalEventException("Not supported event with type = '" + eventById.getEventType() + "'");
        }
        log.info("EventController - getEventById() return {}", displayEventResponseDto);
        return displayEventResponseDto == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(displayEventResponseDto);
    }

    @PatchMapping("/events/{eventId}/ownership")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> updateEventOwnershipById(@RequestBody EventOwnershipRequestDto eventOwnershipRequestDto,
                                                           @PathVariable Long eventId) {
        log.info("EventController - updateEventOwnershipById()");
        Long currentEventOwnerUserId = eventService.getEventOwnerUserIdByEventId(eventId);
        User loggedInUser = authenticatedUser.getAuthenticatedUser();
        if (basicUserService.isBasicUserEventOwner(loggedInUser, currentEventOwnerUserId)
                || loggedInUser.getRole().name().equals("ROLE_ADMIN")) {
            Long newOwnerUserId = eventOwnershipRequestDto.getNewOwnerUserId();
            if (basicUserService.checkIfBasicUserExistsById(newOwnerUserId)) {
                EventOwner eventOwner = eventService.getEventOwnerByUserId(newOwnerUserId);
                eventService.updateEventOwnershipByEventId(eventId, eventOwner, currentEventOwnerUserId);
            } else {
                log.error("EventController - updateEventOwnershipById()", new UnauthorizedRequestException("Unauthorized request"));
                throw new UnauthorizedRequestException("The id of the new event owner does not belong to any user account");
            }
        } else {
            log.error("EventController - updateEventOwnershipById()", new UnauthorizedRequestException("Unauthorized request"));
            throw new UnauthorizedRequestException("Logged in user is not authorized to change the event owner of the event with id: " + eventId);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> updateEventById(@PathVariable Long eventId, @RequestBody EventRequestDto eventRequestDto) {
        log.info("EventController - updateEventById() - eventId = {}", eventId);
        if (eventService.checkIfEventExistsById(eventId)) {
            Long currentEventOwnerUserId = eventService.getEventOwnerUserIdByEventId(eventId);
            User loggedInUser = authenticatedUser.getAuthenticatedUser();
            if (basicUserService.isBasicUserEventOwner(loggedInUser, currentEventOwnerUserId)) {
                EventMapperInterface eventMapper = new EventMapper();
                Event eventFromDto = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.EVENT);
                eventService.updateEventById(eventId, eventFromDto, loggedInUser.getId());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged in user is not authorized to update the  with id: " + eventId);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with id " + eventId + " does not exist");
        }
        log.info("EventController - updateEventById() - event with eventId = {} updated", eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/events/{eventId}/users")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Page<BasicUserDto>> getUsersWithAccess(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("EventController - getUsersWithAccess");
        Page<BasicUser> usersWithAccess = eventService.getUsersWithAccess(eventId, page, size);

        Page<BasicUserDto> usersWithAccessDto = usersWithAccess.map(BasicUserMapper::map);

        log.info("EventController - getUsersWithAccess() return {}", usersWithAccessDto);
        return ResponseEntity.ok(usersWithAccessDto);
    }
}
