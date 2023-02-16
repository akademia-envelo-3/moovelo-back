package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.service.event.CyclicEventService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class CyclicEventController {
    private static final Long USER_ID = 1L;
    private CyclicEventService cyclicEventService;

    @Autowired
    public CyclicEventController(CyclicEventService cyclicEventService) {
        this.cyclicEventService = cyclicEventService;
    }

//    @PostMapping("/cyclicEvents")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<DisplayEventResponseDto> createNewEvent(@RequestBody EventRequestDto eventRequestDto) {
//        log.info("CyclicEventController - createNewEvent()");
//
//        //TODO do powalczenia z wyborem Rodzaju eventu? albo usunac
//        EventMapperInterface eventMapper = new EventMapper();
//        Event event = eventMapper.mapEventRequestDtoToEventByEventType(eventRequestDto, EventType.CYCLIC_EVENT);
//        Event newEvent = cyclicEventService.createNewEvent(event, USER_ID);
//        DisplayEventResponseDto displayEventResponseDto = EventMapper.mapEventToEventResponseDto(newEvent);
//
//        URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newEvent.getId())
//                .toUri();
//
//        log.info("EventController - () return createNewEvent() - dto {}", displayEventResponseDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .location(uri)
//                .body(displayEventResponseDto);
//    }



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
