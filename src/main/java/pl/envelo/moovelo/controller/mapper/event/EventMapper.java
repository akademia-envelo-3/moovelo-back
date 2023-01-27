package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.EventOwnerListResponseMapper;
import pl.envelo.moovelo.controller.mapper.HashtagListResponseMapper;
import pl.envelo.moovelo.controller.mapper.group.GroupResponseMapper;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class EventMapper {

    public static EventIdDto mapEventToEventIdDto(Event event) {
        return new EventIdDto(event.getId());
    }

    public static DisplayEventResponseDto mapEventToEventResponseDto(Event event) {
        return DisplayEventResponseDto.builder()
                .id(event.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()))
                .eventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(event.getEventInfo()))
                .limitedPlaces(event.getLimitedPlaces())
                .eventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(event))
                .isPrivate(false)
                .group(null)
                .frequencyInDays(0)
                .numberOfRepeats(0)
                .hashtags(event.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static DisplayEventResponseDto mapInternalEventToEventResponseDto(InternalEvent internalEvent) {
        return DisplayEventResponseDto.builder()
                .id(internalEvent.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(internalEvent.getEventOwner()))
                .eventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(internalEvent.getEventInfo()))
                .limitedPlaces(internalEvent.getLimitedPlaces())
                .eventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(internalEvent))
                .isPrivate(internalEvent.isPrivate())
                .group(getGroupResponseDto(internalEvent.getGroup()))
                .frequencyInDays(0)
                .numberOfRepeats(0)
                .hashtags(internalEvent.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private static GroupResponseDto getGroupResponseDto(Group group) {
        return group != null ? GroupResponseMapper.mapGroupToGroupResponseMapper(group) : null;
}

    public static DisplayEventResponseDto mapCyclicEventToEventResponseDto(CyclicEvent cyclicEvent) {
        return DisplayEventResponseDto.builder()
                .id(cyclicEvent.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(cyclicEvent.getEventOwner()))
                .eventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(cyclicEvent.getEventInfo()))
                .limitedPlaces(cyclicEvent.getLimitedPlaces())
                .eventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(cyclicEvent))
                .isPrivate(cyclicEvent.isPrivate())
                .group(GroupResponseMapper.mapGroupToGroupResponseMapper(cyclicEvent.getGroup()))
                .frequencyInDays(cyclicEvent.getFrequencyInDays())
                .numberOfRepeats(cyclicEvent.getNumberOfRepeats())
                .hashtags(cyclicEvent.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
