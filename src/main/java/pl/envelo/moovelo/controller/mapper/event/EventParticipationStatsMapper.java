package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.participation.EventParticipationStatsDto;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import java.util.stream.Collectors;

public class EventParticipationStatsMapper {
    public static EventParticipationStatsDto mapEventToEventParticipationStatsDto(Event event) {
        return EventParticipationStatsDto.builder()
                .accepted(event.getAcceptedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .pending(event.getPendingStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .rejected(event.getRejectedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .build();
    }

    public static EventParticipationStatsDto mapExternalEventToEventParticipationStatsDto(ExternalEvent externalEvent) {
        return EventParticipationStatsDto.builder()
                .accepted(externalEvent.getAcceptedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .pending(externalEvent.getPendingStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .rejected(externalEvent.getRejectedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toList()))
                .visitors(externalEvent.getVisitors().stream().map(visitor -> VisitorMapper.mapVisitorToVisitorDto(visitor)))
                .build();
    }
}
