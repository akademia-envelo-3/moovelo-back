package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.service.event.ExternalEventService;
import pl.envelo.moovelo.service.event.InternalEventService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class ExternalEventController {

    private ExternalEventService externalEventService;

    @Autowired
    public ExternalEventController(ExternalEventService externalEventService) {
        this.externalEventService = externalEventService;
    }

    @GetMapping("/externalEvents")
    public ResponseEntity<List<EventListResponseDto>> getAllExternalEvents() {
        log.info("ExternalEventController - getAllExternalEvents()");
        List<ExternalEvent> allExternalEvents = externalEventService.getAllExternalEvents();

        List<EventListResponseDto> externalEventsDto = allExternalEvents.stream()
                .map(EventListResponseMapper::mapExternalEventToEventListResponseDto).toList();

        log.info("ExternalEventController - getAllExternalEvents() return {}", externalEventsDto);
        return ResponseEntity.ok(externalEventsDto);
    }
}
