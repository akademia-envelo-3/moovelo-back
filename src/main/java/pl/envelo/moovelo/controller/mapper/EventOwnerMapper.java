package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerDto;
import pl.envelo.moovelo.entity.events.EventOwner;

public class EventOwnerMapper {

    public static EventOwnerDto mapEventOwnerToEventOwnerDto(EventOwner eventOwner) {
        return EventOwnerDto.builder()
                .basicUserId(eventOwner.getBasicUserId())
                .build();
    }
}
