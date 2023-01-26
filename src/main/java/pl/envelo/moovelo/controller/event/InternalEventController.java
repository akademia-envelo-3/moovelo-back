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
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.service.event.InternalEventService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class InternalEventController {

    private InternalEventService internalEventService;

    @Autowired
    public InternalEventController(InternalEventService internalEventService) {
        this.internalEventService = internalEventService;
    }

    @GetMapping("/internalEvents")
    public ResponseEntity<List<EventListResponseDto>> getAllEvents() {
        log.info("InternalEventController - getAllInternalEvents()");
        List<? extends Event> allInternalEvents = internalEventService.getAllInternalEvents();

        List<EventListResponseDto> internalEventsDto = allInternalEvents.stream().map(internalEvent ->
                EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) internalEvent)).toList();

        log.info("InternalEventController - getAllInternalEvents() return {}", internalEventsDto);
        return ResponseEntity.ok(internalEventsDto);
    }
}
