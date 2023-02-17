package pl.envelo.moovelo.controller.mapper.event.manager;

import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.exception.IllegalEventException;

import static pl.envelo.moovelo.controller.mapper.event.manager.EventMapper.mapEventRequestDtoToEvent;

public class EventMapperManager {
    public <T extends Event> T getMappedEventByEventType(EventRequestDto eventRequestDto, EventType eventType) {
        Event event = switch (eventType) {
            case EVENT -> returnMappedEvent(eventRequestDto, eventType);
            case INTERNAL_EVENT -> returnMappedInternalEvent(eventRequestDto, eventType);
            case CYCLIC_EVENT -> returnMappedCyclicEvent(eventRequestDto, eventType);
            case EXTERNAL_EVENT ->  returnMappedExternalEvent(eventRequestDto, eventType);
            default -> throw new IllegalEventException("Not supported event with type = '" + eventType + "'");
        };
        return (T) event;
    }

    private static Event returnMappedEvent(EventRequestDto eventRequestDto, EventType eventType) {
        Event event = new Event();
        EventMapper.mapEventRequestDtoToEvent(eventRequestDto, eventType, event);
        return event;
    }

    private InternalEvent returnMappedInternalEvent(EventRequestDto eventRequestDto, EventType eventType) {
        InternalEvent internalEvent = new InternalEvent();
        EventMapper.mapEventRequestDtoToInternalEvent(eventRequestDto, eventType, internalEvent);
        return internalEvent;
    }

    private CyclicEvent returnMappedCyclicEvent(EventRequestDto eventRequestDto, EventType eventType) {
        CyclicEvent cyclicEvent = new CyclicEvent();
        EventMapper.mapEventRequestDtoToCyclicEvent(eventRequestDto, eventType, cyclicEvent);
        return cyclicEvent;
    }

    private ExternalEvent returnMappedExternalEvent(EventRequestDto eventRequestDto, EventType eventType) {
        ExternalEvent externalEvent = new ExternalEvent();
        mapEventRequestDtoToEvent(eventRequestDto, eventType, externalEvent);
        return externalEvent;
    }


}
