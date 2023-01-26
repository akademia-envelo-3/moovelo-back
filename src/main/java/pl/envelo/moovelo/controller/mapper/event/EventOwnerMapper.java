package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.EventOwnerDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.EventOwner;

import java.util.stream.Collectors;

public class EventOwnerMapper {

    public static EventOwnerDto mapEventOwnerToEventOwnerDto(EventOwner eventOwner, BasicUser basicUser) {
        return EventOwnerDto.builder()
                .id(eventOwner.getId())
                .userId(eventOwner.getUserId())
                .firstname(basicUser.getFirstname())
                .lastname(basicUser.getLastname())
                .events(eventOwner.getEvents().stream().map(EventMapper::mapEventToEventIdDto).collect(Collectors.toList()))
                .build();
    }

    public static EventOwner mapEventOwnerDtoToEventOwner(EventOwnerDto eventOwnerDto) {
        EventOwner eventOwner = new EventOwner();
        eventOwner.setUserId(eventOwnerDto.getUserId());
        return eventOwner;
    }
}
