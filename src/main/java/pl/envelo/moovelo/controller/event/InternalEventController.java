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
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.exception.IllegalEventException;
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
    public ResponseEntity<List<EventListResponseDto>> getAllInternalEvents() {
        log.info("InternalEventController - getAllInternalEvents()");
        List<? extends Event> allInternalEvents = internalEventService.getAllInternalEvents();

        List<EventListResponseDto> internalEventsDto = allInternalEvents.stream().map(internalEvent -> switch (internalEvent.getEventType()) {
            case INTERNAL_EVENT -> EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) internalEvent);
            case CYCLIC_EVENT -> EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) internalEvent);
            default -> throw new IllegalEventException("Unexpected value: " + internalEvent.getEventType());
        }).toList();

        log.info("InternalEventController - getAllInternalEvents() return {}", internalEventsDto);
        return ResponseEntity.ok(internalEventsDto);
    }

        @GetMapping("/internalEvents/groups/{groupId}")
        public ResponseEntity<List<EventListResponseDto>> getAllInternalEventsByGroupId(Long groupId) {
            log.info("InternalEventController - getAllInternalEventsByGroupId()");
            List<? extends Event> allInternalEvents = internalEventService.getAllInternalEventsByGroupId(groupId);

            List<EventListResponseDto> internalEventsDto = allInternalEvents.stream().map(internalEvent -> switch (internalEvent.getEventType()) {
                case INTERNAL_EVENT -> EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) internalEvent);
                case CYCLIC_EVENT -> EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) internalEvent);
                default -> throw new IllegalEventException("Unexpected value: " + internalEvent.getEventType());
            }).toList();

            log.info("InternalEventController - getAllInternalEventsByGroupId() return {}", internalEventsDto);
            return ResponseEntity.ok(internalEventsDto);
        }
    }

