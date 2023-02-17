package pl.envelo.moovelo.controller.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.exception.AvailablePlacesExceededException;
import pl.envelo.moovelo.exception.EventDateException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.VisitorService;
import pl.envelo.moovelo.service.event.ExternalEventService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class ExternalEventController {

    private static final EventType eventType = EventType.EXTERNAL_EVENT;
    private ExternalEventService externalEventService;
    private VisitorService visitorService;
    private AuthenticatedUser authenticatedUser;
    private BasicUserService basicUserService;

    @GetMapping("/externalEvents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventListResponseDto>> getAllExternalEvents() {
        log.info("ExternalEventController - getAllExternalEvents()");
        List<ExternalEvent> allExternalEvents = externalEventService.getAllExternalEvents();

        List<EventListResponseDto> externalEventsDto = allExternalEvents.stream()
                .map(EventListResponseMapper::mapExternalEventToEventListResponseDto).toList();

        log.info("ExternalEventController - getAllExternalEvents() return {}", externalEventsDto);
        return ResponseEntity.ok(externalEventsDto);
    }

    @PostMapping("/externalEvents/{id}/visitors")
    public ResponseEntity<?> sendConfirmationMailToVisitor(@RequestBody @Valid VisitorDto visitorDto, @PathVariable("id") Long externalEventId)
            throws MessagingException {
        log.info("ExternalEventController - sendConfirmationMailToVisitor() - visitorDto = {}, externalEventId = {}", visitorDto, externalEventId);

        visitorService.sendConfirmationLink(visitorDto, externalEventId);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Confirm your participation in the event through the link sent to the email");
        result.put("confirm_link_expire_date", new Date(System.currentTimeMillis() + Constants.VISITOR_CONFIRM_TOKEN_DURATION_TIME).toString());

        log.info("ExternalEventController - sendConfirmationMailToVisitor() - visitorDto = {}, externalEventId = {} - result = {}",
                visitorDto, externalEventId, result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/externalEvents/visitor/{token}")
    public ResponseEntity<?> addVisitorToEventAndSendCancellationLink(@PathVariable String token) throws MessagingException {
        log.info("ExternalEventController - addVisitorToEventAndSendCancellationLink(token = '{}')", token);

        // visitorDetails contains information about visitor name, surname, email and event id
        Map<String, String> visitorDetails = visitorService.getVisitorDetailsFromConfirmationToken(token);
        Long externalEventId = Long.parseLong(visitorDetails.get("externalEventId"));
        ExternalEvent externalEvent = externalEventService.getExternalEventById(externalEventId);

        if (externalEvent.getEventInfo().getStartDate().isBefore(LocalDateTime.now())) {
            throw new EventDateException("Unable to sign up for an event that has taken place");
        }

        if (!externalEventService.checkForAvailablePlaces(externalEvent)) {
            throw new AvailablePlacesExceededException("The limit of places for the event has been exceeded");
        }

        Visitor visitor = visitorService.createOrGetExistingVisitor(visitorDetails);
        externalEventService.addVisitorToExternalEvent(externalEvent, visitor);
        visitorService.sendCancellationLink(visitorDetails.get("email"), visitor.getId(), externalEvent);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Successfully added a visitor to the event");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/externalEvents/visitor/{token}/remove")
    public ResponseEntity<?> removeVisitorFromEvent(@PathVariable String token) throws MessagingException {
        log.info("ExternalEventController - removeVisitorFromEventAndSendInfoOnMail(token = '{}')", token);

        // visitorDetails contains information about visitor id and event id
        Map<String, String> visitorDetails = visitorService.getVisitorDetailsFromCancellationToken(token);
        Long externalEventId = Long.parseLong(visitorDetails.get("externalEventId"));
        Long visitorId = Long.parseLong(visitorDetails.get("visitorId"));
        ExternalEvent externalEvent = externalEventService.getExternalEventById(externalEventId);

        if (externalEvent.getEventInfo().getStartDate().isBefore(LocalDateTime.now())) {
            throw new EventDateException("You cannot sign out of an event that has already occurred");
        }

        Visitor visitor = visitorService.getVisitor(visitorId);
        externalEventService.removeVisitorFromExternalEvent(externalEvent, visitor);
        visitorService.removeVisitorWithNoExternalEvents(visitor);

        Map<String, String> result = new HashMap<>();
        result.put("message", "You were successfully discharged from the event");

        return ResponseEntity.ok(result);
    }

    @PostMapping("/externalEvents/{eventId}/invite")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createInvitationLink(@PathVariable Long eventId) {
        log.info("ExternalEventController - createInvitationLink(eventId = '{}')", eventId);
        ExternalEvent event = externalEventService.getExternalEventById(eventId);
        User user = authenticatedUser.getAuthenticatedUser();

        if (basicUserService.isBasicUserEventOwner(user, event.getEventOwner().getUserId())) {
            throw new UnauthorizedRequestException("You are not the owner of this event!");
        }

        String link = externalEventService.createInvitationLink(event);
        log.info("ExternalEventController - createInvitationLink(eventId = '{}') - return link = '{}'", eventId, link);
        return ResponseEntity.status(HttpStatus.CREATED).body(link);
    }

    @GetMapping("/externalEvents/{eventId}/invite")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getInvitationLink(@PathVariable Long eventId) {
        log.info("ExternalEventController - getInvitationLink(eventId = '{}')", eventId);
        String link = externalEventService.getInvitationLink(eventId);
        log.info("ExternalEventController - getInvitationLink(eventId = '{}') - return link = '{}'", eventId, link);
        return ResponseEntity.ok(link);
    }

    @GetMapping("/externalEvents/{uuid}")
    public void redirectToExternalEventPage(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        log.info("ExternalEventController - redirectToExternalEventPage(uuid = '{}')", uuid);
        Long eventId = externalEventService.getExternalEventIdByUuid(uuid);
        log.info("ExternalEventController - redirectToExternalEventPage(uuid = '{}') - return eventId = {}", uuid, eventId);
        response.sendRedirect("https://develop--moovelo-envelo.netlify.app/events/" + eventId);
    }
}
