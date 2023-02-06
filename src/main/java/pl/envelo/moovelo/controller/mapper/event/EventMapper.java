package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupResponseDto;
import pl.envelo.moovelo.controller.mapper.EventOwnerListResponseMapper;
import pl.envelo.moovelo.controller.mapper.HashtagListResponseMapper;
import pl.envelo.moovelo.controller.mapper.group.GroupResponseMapper;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class EventMapper implements EventMapperInterface {

    public static EventIdDto mapEventToEventIdDto(Event event) {
        return new EventIdDto(event.getId());
    }

    @Override
    public <T extends Event> T mapEventRequestDtoToEventByEventType(EventRequestDto eventRequestDto, EventType eventType) {
        T event = createEventByEventType(eventType);
        event.setEventOwner(new EventOwner());
        event.setEventInfo(EventInfoMapper.mapEventInfoDtoToEventInfo(eventRequestDto.getEventInfo()));
        event.setLimitedPlaces(eventRequestDto.getLimitedPlaces());
//        event.setComments(new ArrayList<>());
//        event.setUsersWithAccess(new ArrayList<>());
//        event.setAcceptedStatusUsers(new HashSet<>());
//        event.setRejectedStatusUsers(new HashSet<>());
//        event.setPendingStatusUsers(new HashSet<>());
        event.setHashtags(eventRequestDto.getHashtags().stream()
                .map(HashtagListResponseMapper::mapHashTagDtoToHashtag)
                .collect(Collectors.toList())
        );
        return event;
    }

    private <T extends Event> T createEventByEventType(EventType eventType) {
        Event event = switch (eventType) {
            case EVENT -> new Event();
            case EXTERNAL_EVENT -> new ExternalEvent();
            case INTERNAL_EVENT -> new InternalEvent();
            case CYCLIC_EVENT -> new CyclicEvent();
        };
        return (T) event;
    }

    public static DisplayEventResponseDto mapEventToEventResponseDto(Event event) {
        return DisplayEventResponseDto.builder()
                .id(event.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()))
                .eventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(event.getEventInfo()))
                .limitedPlaces(event.getLimitedPlaces())
                //TODO Wysypuje blad
//                .eventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(event))
                .isPrivate(false)
                .isCyclic(false)
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
                .isCyclic(false)
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
                .isCyclic(true)
                .group(GroupResponseMapper.mapGroupToGroupResponseMapper(cyclicEvent.getGroup()))
                .frequencyInDays(cyclicEvent.getFrequencyInDays())
                .numberOfRepeats(cyclicEvent.getNumberOfRepeats())
                .hashtags(cyclicEvent.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static DisplayEventResponseDto mapExternalEventToEventResponseDto(ExternalEvent externalEvent) {
        return DisplayEventResponseDto.builder()
                .id(externalEvent.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(externalEvent.getEventOwner()))
                .eventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(externalEvent.getEventInfo()))
                .limitedPlaces(externalEvent.getLimitedPlaces())
                .eventParticipationStats(EventParticipationStatsMapper.mapExternalEventToEventParticipationStatsDto(externalEvent))
                .isPrivate(false)
                .isCyclic(false)
                .group(null)
                .frequencyInDays(0)
                .numberOfRepeats(0)
                .hashtags(externalEvent.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
