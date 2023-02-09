package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.service.event.CyclicEventService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class CyclicEventController {

    private CyclicEventService cyclicEventService;

    @Autowired
    public CyclicEventController(CyclicEventService cyclicEventService) {
        this.cyclicEventService = cyclicEventService;
    }

    @GetMapping("/cyclicEvents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EventListResponseDto>> getAllCyclicEvents() {
        log.info("CyclicEventController - getAllCyclicEvents()");
        List<CyclicEvent> allCyclicEvents = cyclicEventService.getAllCyclicEvents();

        List<EventListResponseDto> cyclicEventsDto = allCyclicEvents.stream()
                .map(EventListResponseMapper::mapCyclicEventToEventListResponseDto).toList();

        log.info("CyclicEventController - getAllCyclicEvents() return {}", cyclicEventsDto);
        return ResponseEntity.ok(cyclicEventsDto);
    }
}
