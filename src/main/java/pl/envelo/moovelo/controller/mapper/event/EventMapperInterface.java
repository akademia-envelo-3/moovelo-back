package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;

public interface EventMapperInterface {

    <T> T mapEventToEventResponseDto(Event event);

    <T> T mapInternalEventToEventResponseDto(InternalEvent internalEvent);

    <T> T mapCyclicEventToEventResponseDto(CyclicEvent cyclicEvent);

    <T> T mapExternalEventToEventResponseDto(ExternalEvent externalEvent);
}
