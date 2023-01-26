package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerListResponseDto;
import pl.envelo.moovelo.entity.events.EventOwner;

public class EventOwnerListResponseMapper {

    public static EventOwnerListResponseDto mapEventOwnerToEventOwnerListResponseDto(EventOwner eventOwner) {
        return EventOwnerListResponseDto.builder()
                .userId(eventOwner.getUserId())
                .build();
    }
}
