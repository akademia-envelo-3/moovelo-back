package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerDto;
import pl.envelo.moovelo.entity.events.EventOwner;

import java.util.stream.Collectors;

public class EventOwnerMapper {

    public static EventOwnerDto mapEventOwnerToEventOwnerDto(EventOwner eventOwner, BasicUserDto basicUserDto) {
        return EventOwnerDto.builder()
                .id(eventOwner.getId())
                .userId(eventOwner.getUserId())
                .firstname(basicUserDto.getFirstname())
                .lastname(basicUserDto.getLastname())
                .events(eventOwner.getEvents().stream().map(EventMapper::mapEventToEventIdDto).collect(Collectors.toList()))
                .build();
    }
//todo - sprawdzić, czy potrzebna metoda
//    public static EventOwner mapEventOwnerDtoToEventOwner(EventOwnerDto eventOwnerDto) {


}
