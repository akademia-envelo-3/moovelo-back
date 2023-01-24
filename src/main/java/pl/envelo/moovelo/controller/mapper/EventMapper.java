package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.event.EventResponseDto;
import pl.envelo.moovelo.entity.events.Event;

public class EventMapper {

    public EventResponseDto mapEventToResponseDto(Event event) {
        EventResponseDto eventResponseDto = EventResponseDto.builder()
                .id(event.getId())
                //.isConfirmationRequired(event.getEventInfo().getIsConfirmationRequired())
                //.isPrivate(event) //TODO co w takim przypadku
                .build();

        return eventResponseDto;
    }
}
