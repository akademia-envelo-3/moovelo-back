package pl.envelo.moovelo.controller.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnershipRequestDto;
import pl.envelo.moovelo.controller.dto.survey.EventSurveyDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.controller.mapper.survey.EventSurveyMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.entity.surveys.EventSurvey;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.event.EventService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {
    private EventService eventService;
    private AuthorizationService authorizationService;

    @PostMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<DisplayEventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("EventController - createNewEvent()");
        Long basicUserId = authorizationService.getLoggedBasicUserId();
        //TODO do powalczenia z wyborem Rodzaju eventu? albo usunac
        EventMapperInterface eventMapper = new EventMapper();
        Event event = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.EVENT);
        Event newEvent = eventService.createNewEvent(event, basicUserId);
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

        if (!authorizationService.authorizeGetByOwnerBasicUserId(basicUserId) && !authorizationService.isLoggedUserAdmin()) {
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

        Event event = eventService.getEventById(eventId);

        if (!authorizationService.isLoggedUserEventOwner(eventId)) {
            throw new UnauthorizedRequestException("Access denied!");
        }
        if (event.getEventInfo().getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalEventException("Can't delete an event that has already taken place!");
        }

        eventService.removeEventById(eventId);
        log.info("EventController - removeEventById() - event with eventId = {} removed", eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/events/{eventId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<DisplayEventResponseDto> getEventById(@PathVariable Long eventId) {
        log.info("EventController - getEventById()");
        if (authorizationService.isLoggedUserEventOwner(eventId) || authorizationService.isLoggedUserAdmin()) {
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
                default ->
                        throw new IllegalEventException("Not supported event with type = '" + eventById.getEventType() + "'");
            }
            log.info("EventController - getEventById() return {}", displayEventResponseDto);
            return ResponseEntity.ok(displayEventResponseDto);

        }
        throw new UnauthorizedRequestException("Access denied!");
    }

    @PatchMapping("/events/{eventId}/ownership")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> updateEventOwnershipById(@RequestBody EventOwnershipRequestDto eventOwnershipRequestDto,
                                                           @PathVariable Long eventId) {
        log.info("EventController - updateEventOwnershipById(), - eventId = {}", eventId);
        if (authorizationService.isLoggedUserEventOwner(eventId) || authorizationService.isLoggedUserAdmin()) {
            Long newOwnerUserId = eventOwnershipRequestDto.getNewOwnerUserId();
            if (authorizationService.checkIfBasicUserExistsById(newOwnerUserId)) {
                eventService.updateEventOwnershipByEventId(eventId, newOwnerUserId);
            } else {
                log.error("EventController - updateEventOwnershipById()", new UnauthorizedRequestException("Unauthorized request"));
                throw new UnauthorizedRequestException("The id of the new event owner does not belong to any basic user account");
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
            if (authorizationService.isLoggedUserEventOwner(eventId)) {
                EventMapperInterface eventMapper = new EventMapper();
                Event eventFromDto = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.EVENT);
                eventService.updateEventById(eventId, eventFromDto, authorizationService.getLoggedUserId());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged in user is not authorized to update the event with id: " + eventId);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No event with id: " + eventId);
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

    @GetMapping("/events/{eventId}/surveys")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<EventSurvey>> getEventSurveysByEventId(@PathVariable Long eventId) {
        log.info("EventController - getEventSurveysByEventId");
        List<EventSurvey> surveys = eventService.getEventSurveysByEventId(eventId);

        List<EventSurveyDto> surveysDto = surveys
                .stream()
                .map(EventSurveyMapper::mapEventSurveyToEventSurveyDto)
                .collect(Collectors.toList());


        log.info("EventController - getEventSurveysByEventId() return {}");
        return ResponseEntity.ok().build();
    }
}
