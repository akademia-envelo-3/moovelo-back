package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;
import pl.envelo.moovelo.controller.dto.comment.CommentResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.OwnershipRequestDto;
import pl.envelo.moovelo.controller.dto.event.response.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.response.EventResponseDto;
import pl.envelo.moovelo.controller.dto.survey.EventSurveyDto;
import pl.envelo.moovelo.controller.dto.survey.EventSurveyRequestDto;
import pl.envelo.moovelo.controller.mapper.CommentMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.controller.mapper.event.EventListMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.controller.mapper.event.manager.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.manager.EventMapperManager;
import pl.envelo.moovelo.controller.mapper.survey.EventSurveyMapper;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.surveys.EventSurvey;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.model.EventsForUserCriteria;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.event.EventService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {
    private static final EventType eventType = EventType.EVENT;
    EventMapperInterface eventMapperInterface;
    private EventMapperManager eventMapperManager;
    private EventService<Event> eventService;
    private AuthorizationService authorizationService;

    @Autowired
    public EventController(EventMapperManager eventMapperManager, EventService<Event> eventService,
                           AuthorizationService authorizationService) {
        this.eventMapperManager = eventMapperManager;
        this.eventService = eventService;
        this.authorizationService = authorizationService;

    }

    @PostMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<EventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("EventController - createNewEvent()");
        eventMapperInterface = new EventMapper();
        Long basicUserId = authorizationService.getLoggedBasicUserId();

        Event mappedEventFromRequest = eventMapperManager.mapEventRequestDtoToEventByEventType(eventRequestDto, eventType);
        Event createdEvent = eventService.createNewEvent(mappedEventFromRequest, eventType, basicUserId, null);
        EventResponseDto eventResponseDto = eventMapperManager.getMappedResponseForSpecificEvent(eventMapperInterface, createdEvent);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEvent.getId())
                .toUri();

        log.info("EventController - () return createNewEvent() - dto {}", eventResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(eventResponseDto);
    }

    @PutMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> updateEventById(@PathVariable Long eventId, @RequestBody EventRequestDto eventRequestDto) {
        log.info("EventController - updateEventById() - eventId = {}", eventId);
        eventMapperInterface = new EventMapper();

        if (eventService.checkIfEventExistsById(eventId, eventType)) {
            if (authorizationService.isLoggedUserEventOwner(eventId)) {
                Event mappedEventFromRequest = eventMapperManager.mapEventRequestDtoToEventByEventType(eventRequestDto, eventType);
                eventService.updateEventById(eventId, mappedEventFromRequest, eventType, authorizationService.getLoggedUserId());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged in user is not authorized to update the  with id: " + eventId);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with id " + eventId + " does not exist");
        }
        log.info("EventController - updateEventById() - event with eventId = {} updated", eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @GetMapping("events/eventOwners/{userId}")
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
//    public ResponseEntity<Page<EventListResponseDto>> getAllEventsByEventOwnerBasicUserId(@PathVariable("userId") Long basicUserId) {
//        log.info("EventController - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);
//
//        if (!authorizationService.authorizeGetByOwnerBasicUserId(basicUserId) && !authorizationService.isLoggedUserAdmin()) {
//            throw new UnauthorizedRequestException("Access denied");
//        }
//        Page<? extends Event> allEvents = eventService.getAllEventsByEventOwnerBasicUserId(basicUserId, eventType);
//        Page<EventListResponseDto> eventsDto = eventMapperManager.mapEventToEventListResponseDto(allEvents, eventMapperInterface);
//        return ResponseEntity.ok(eventsDto);
//    }

    @DeleteMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeEventById(@PathVariable long eventId) {
        log.info("EventController - removeEventById() - eventId = {}", eventId);

        Event event = eventService.getEventById(eventId, eventType);

        if (!authorizationService.isLoggedUserEventOwner(eventId)) {
            throw new UnauthorizedRequestException("Access denied!");
        }
        if (event.getEventInfo().getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalEventException("Can't delete an event that has already taken place!");
        }

        eventService.removeEventById(eventId, eventType);
        log.info("EventController - removeEventById() - event with eventId = {} removed", eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<EventListResponseDto>> getAllEvents(
            String privacy,
            String group,
            String cat,
            Long groupId,
            String sort,
            String sortOrder,
            @RequestParam(defaultValue = "0") Integer page) {
        log.info("EventController - getAllEvents()");
        eventMapperInterface = new EventListMapper();

        Page<? extends Event> events = eventService.getAllEvents(privacy, group, cat, groupId, sort, sortOrder, page, eventType);

        Page<EventListResponseDto> eventsDto = eventMapperManager.mapEventToEventListResponseDto(events, eventMapperInterface);

        log.info("EventController - getAllEvents() return {}", eventsDto);
        return ResponseEntity.ok(eventsDto);
    }

    @GetMapping("/events/users/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<?>> getAllEventsAvailableForUser(
            @PathVariable Long userId,
            EventsForUserCriteria filterCriteria,
            SortingAndPagingCriteria sortingAndPagingCriteria
    ) {
        log.info("EventController - getAllEventsAvailableForUser - userId = '{}', filterCriteria = '{}', sortingAndPagingCriteria = '{}'",
                userId, filterCriteria, sortingAndPagingCriteria);
        eventMapperInterface = new EventListMapper();

        if (!authorizationService.isLoggedUserIdEqualToBasicUserIdParam(userId)) {
            throw new UnauthorizedRequestException("You do not have access to view this user's events");
        }

        Page<? extends Event> eventsAvailableForUser = eventService.getEventsForUser(userId, filterCriteria, sortingAndPagingCriteria, eventType);

        Page<EventListResponseDto> eventsAvailableForUserDto = eventMapperManager.mapEventToEventListResponseDto(eventsAvailableForUser, eventMapperInterface);

        log.info("EventController - getAllEventsAvailableForUser - userId = '{}', filterCriteria = '{}', sortingAndPagingCriteria = '{}' - return = '{}'",
                userId, filterCriteria, sortingAndPagingCriteria, eventsAvailableForUserDto);
        return ResponseEntity.ok(eventsAvailableForUserDto);
    }

    @GetMapping("/events/{eventId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long eventId) {
        log.info("EventController - getEventById()");
        if (authorizationService.isLoggedUserEventOwner(eventId) || authorizationService.isLoggedUserAdmin()) {
            Event eventById = eventService.getEventById(eventId, eventType);
            eventMapperInterface = new EventMapper();
            EventResponseDto eventResponseDto = eventMapperManager.getMappedResponseForSpecificEvent(eventMapperInterface, eventById);
            log.info("EventController - getEventById() return {}", eventResponseDto);
            return ResponseEntity.ok(eventResponseDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    // TODO: 22.02.2023 sprawdzic, czy User ma dostep do Eventu
    @GetMapping("/events/{eventId}/users")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Page<BasicUserDto>> getUsersWithAccess(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("EventController - getUsersWithAccess");
        Page<BasicUser> usersWithAccess = eventService.getUsersWithAccess(eventId, page, size, eventType);

        Page<BasicUserDto> usersWithAccessDto = usersWithAccess.map(BasicUserMapper::map);

        log.info("EventController - getUsersWithAccess() return {}", usersWithAccessDto);
        return ResponseEntity.ok(usersWithAccessDto);
    }

    @PatchMapping("/events/{eventId}/ownership")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> updateEventOwnershipById(@RequestBody OwnershipRequestDto ownershipRequestDto,
                                                           @PathVariable Long eventId) {
        log.info("EventController - updateEventOwnershipById(), - eventId = {}", eventId);
        if (authorizationService.isLoggedUserEventOwner(eventId) || authorizationService.isLoggedUserAdmin()) {
            Long newOwnerUserId = ownershipRequestDto.getNewOwnerUserId();
            if (authorizationService.checkIfBasicUserExistsById(newOwnerUserId)) {
                eventService.updateEventOwnershipByEventId(eventId, newOwnerUserId, eventType);
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

    @PatchMapping("events/{eventId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> setStatus(
            @PathVariable Long eventId,
            @PathVariable Long userId,
            @RequestParam String status) {

        log.info("EventController - setStatus()");

        //TODO: 24.02.2023 zmiana na metodę authorizationService.isLoggedUserIdEqualToBasicUserIdParam()
        authorizationService.checkIfLoggedUserHasAccessToEvent(eventId, eventType);

        if (authorizationService.getLoggedBasicUserId().equals(userId)) {
            eventService.setStatus(eventId, userId, status, eventType);
        } else {
            log.error("EventController - setStatus()", new UnauthorizedRequestException("Unauthorized request"));
            throw new UnauthorizedRequestException("Logged in user is not authorized to change status of other users");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events/{eventId}/surveys")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<EventSurveyDto>> getEventSurveysByEventId(@PathVariable Long eventId) {
        log.info("EventController - getEventSurveysByEventId");
        authorizationService.checkIfLoggedUserHasAccessToEvent(eventId, eventType);

        List<EventSurvey> surveys = eventService.getEventSurveysByEventId(eventId, eventType);

        List<EventSurveyDto> surveysDto = surveys
                .stream()
                .map(surveyDto -> {
                    if (authorizationService.isLoggedUserAdmin() ||
                            authorizationService.isLoggedUserEventOwner(eventId)) {
                        return EventSurveyMapper.mapEventSurveyToEventSurveyDto(surveyDto);
                    } else {
                        //TODO: 24.02.2023 zmiana na metodę authorizationService.getLoggedBasicUser()
                        BasicUser basicUser = (BasicUser) authorizationService.getLoggedUser();
                        return EventSurveyMapper.mapEventSurveyToEventSurveyDto(surveyDto, basicUser);
                    }
                })
                .toList();

        log.info("EventController - getEventSurveysByEventId() return {}", surveysDto);
        return ResponseEntity.ok(surveysDto);
    }

    @PostMapping("events/{eventId}/surveys")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<EventSurveyDto> createEventSurvey(@RequestBody EventSurveyRequestDto eventSurveyRequestDto, @PathVariable Long eventId) {
        log.info("EventController - createNewSurvey");
        if (!authorizationService.isLoggedUserEventOwner(eventId)) {
            throw new UnauthorizedRequestException("Logged in user is not authorized to create a survey in event with id " + eventId);
        }

        EventSurvey eventSurvey = EventSurveyMapper.mapEventSurveyRequestDtoToEventSurvey(eventSurveyRequestDto);
        EventSurvey newEventSurvey = eventService.createEventSurvey(eventSurvey, eventId);
        EventSurveyDto newEventSurveyDto = EventSurveyMapper.mapEventSurveyToEventSurveyDto(newEventSurvey);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEventSurvey.getId())
                .toUri();

        log.info("EventController - () return createNewEventSurvey() - dto {}", newEventSurveyDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(newEventSurveyDto);
    }

    @GetMapping("events/{eventId}/files")
    public ResponseEntity<List<AttachmentResponseDto>> getEventAttachments(
            @PathVariable Long eventId
    ) {
        log.info("EventController - getEventAttachments(eventId = '{}')", eventId);
        List<Attachment> attachments = eventService.getEventAttachments(eventId);
        List<AttachmentResponseDto> attachmentResponseDtos = attachments
                .stream()
                .map(AttachmentMapper::mapAttachmentToAttachmentResponseDto)
                .toList();

        attachmentResponseDtos.forEach(attachmentResponseDto -> attachmentResponseDto.setDownloadLink(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v1/files/{id}")
                        .buildAndExpand(attachmentResponseDto.getId())
                        .toUri()
                        .toString()
        ));

        log.info("\"EventController - getEventAttachments(eventId = '{}') - return attachmentResponseDtos = '{}'",
                eventId, attachmentResponseDtos);
        return ResponseEntity.ok(attachmentResponseDtos);
    }

    @PostMapping("events/{eventId}/files")
    public ResponseEntity<AttachmentResponseDto> addAttachmentToEvent(
            @PathVariable Long eventId,
            @RequestParam MultipartFile file
    ) {
        log.info("EventController - addAttachmentToEvent(eventId = '{}', file = '{}'", eventId, file.getName());
        Attachment attachment = AttachmentMapper.mapMultipartFileToAttachment(file);
        attachment = eventService.addAttachmentToEvent(eventId, attachment);
        AttachmentResponseDto attachmentResponseDto = AttachmentMapper.mapAttachmentToAttachmentResponseDto(attachment);
        log.info("EventController - addAttachmentToEvent(eventId = '{}', file = '{}') - return attachmentResponseDto = '{}'",
                eventId, file.getName(), attachmentResponseDto);
        return ResponseEntity.ok(attachmentResponseDto);
    }
}