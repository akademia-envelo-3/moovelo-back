package pl.envelo.moovelo.controller.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.service.actors.VisitorService;
import pl.envelo.moovelo.service.event.ExternalEventService;
import pl.envelo.moovelo.service.event.InternalEventService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class ExternalEventController {

    private ExternalEventService externalEventService;
    private VisitorService visitorService;

    @Autowired
    public ExternalEventController(ExternalEventService externalEventService, VisitorService visitorService) {
        this.externalEventService = externalEventService;
        this.visitorService = visitorService;
    }

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
    public ResponseEntity<?> sendConfirmationMailToVisitor(@RequestBody VisitorDto visitorDto, @PathVariable("id") Long externalEventId) {
        log.info("ExternalEventController - sendConfirmationMailToVisitor() - visitorDto = {}, externalEventId = {}", visitorDto, externalEventId);

        visitorService.sendMailWithConfirmationLink(visitorDto, externalEventId);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Confirm your participation in the event through the link sent to the email");
        result.put("confirm_link_expire_date", new Date(System.currentTimeMillis() + Constants.VISITOR_CONFIRM_TOKEN_DURATION_TIME).toString());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/externalEvent/visitor/{token}")
    public ResponseEntity<?> addVisitorToEventAndSendCancellationLink(@PathVariable String token) {
        log.info("ExternalEventController - sendConfirmationMailToVisitor() - token = {}", token);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
