package pl.envelo.moovelo.controller.mapper.event;

import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.*;

public interface EventMapperInterface {

    <T> T mapEventToEventResponseDto(Event event);

    <T> T mapInternalEventToEventResponseDto(InternalEvent internalEvent);

    <T> T mapCyclicEventToEventResponseDto(CyclicEvent cyclicEvent);

    <T> T mapExternalEventToEventResponseDto(ExternalEvent externalEvent);
}
