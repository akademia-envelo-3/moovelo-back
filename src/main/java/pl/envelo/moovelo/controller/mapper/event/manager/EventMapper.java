package pl.envelo.moovelo.controller.mapper.event.manager;

import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.EventOwnerListResponseMapper;
import pl.envelo.moovelo.controller.mapper.HashtagListResponseMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventInfoMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.controller.mapper.event.EventParticipationStatsMapper;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.stream.Collectors;

public class EventMapper implements EventMapperInterface {
    private final EventMapperManager eventMapperManager = new EventMapperManager();

    public static EventIdDto mapEventToEventIdDto(Event event) {
        return new EventIdDto(event.getId());
    }

    @Override
    public <T extends Event> T mapEventRequestDtoToEventByEventType(EventRequestDto eventRequestDto, EventType eventType) {
        return eventMapperManager.getMappedEventByEventType(eventRequestDto, eventType);
    }

    static <T extends Event> T mapEventRequestDtoToEvent(EventRequestDto eventRequestDto, EventType eventType, T event) {
        event.setEventOwner(new EventOwner());
        event.setEventInfo(EventInfoMapper.mapEventInfoDtoToEventInfo(eventRequestDto.getEventInfo()));
        event.setLimitedPlaces(eventRequestDto.getLimitedPlaces());
        event.setEventType(eventType);
        // TODO: 16.02.2023 Zastanowic sie, co z updatem
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

    static <T extends InternalEvent> T mapEventRequestDtoToInternalEvent(EventRequestDto eventRequestDto, EventType eventType,
                                                                         T internalEvent) {
        mapEventRequestDtoToEvent(eventRequestDto, eventType, internalEvent);
        internalEvent.setPrivate(eventRequestDto.isPrivate());
        internalEvent.setGroup(new Group());
        return internalEvent;
    }

    static CyclicEvent setMappedFieldsForCyclicEvent(EventRequestDto eventRequestDto, EventType eventType, CyclicEvent cyclicEvent) {
        mapEventRequestDtoToInternalEvent(eventRequestDto, eventType, cyclicEvent);
        cyclicEvent.setFrequencyInDays(eventRequestDto.getFrequencyInDays());
        cyclicEvent.setNumberOfRepeats(eventRequestDto.getNumberOfRepeats());
        return cyclicEvent;
    }


    public static DisplayEventResponseDto mapEventToEventResponseDto(Event event) {
        DisplayEventResponseDto displayEventResponseDto = new DisplayEventResponseDto();
        displayEventResponseDto.setId(event.getId());
        displayEventResponseDto.setEventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()));
        displayEventResponseDto.setEventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(event.getEventInfo()));
        displayEventResponseDto.setLimitedPlaces(event.getLimitedPlaces());
        displayEventResponseDto.setUsersWithAccess(event.getUsersWithAccess().stream().map(BasicUserMapper::map).collect(Collectors.toList()));
        displayEventResponseDto.setEventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(event));
        displayEventResponseDto.setPrivate(false);
        displayEventResponseDto.setCyclic(false);
        displayEventResponseDto.setHashtags(event.getHashtags().stream().map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                .collect(Collectors.toList()));
        return displayEventResponseDto;
    }

    public static DisplayEventResponseDto mapInternalEventToEventResponseDto(InternalEvent internalEvent) {
        DisplayEventResponseDto displayEventResponseDto = mapEventToEventResponseDto(internalEvent);
        // TODO: 16.02.2023 Na ten moment nie mamy grup 
//        displayEventResponseDto.setGroup(GroupResponseMapper.mapGroupToGroupResponseMapper(internalEvent.getGroup()));
        displayEventResponseDto.setPrivate(internalEvent.isPrivate());
        return displayEventResponseDto;
    }

    // TODO: 16.02.2023 Czemu tutaj?
//    private static GroupResponseDto getGroupResponseDto(Group group) {
//        return group != null ? GroupResponseMapper.mapGroupToGroupResponseMapper(group) : null;
//    }

    public static DisplayEventResponseDto mapCyclicEventToEventResponseDto(CyclicEvent cyclicEvent) {
        DisplayEventResponseDto displayEventResponseDto = mapInternalEventToEventResponseDto(cyclicEvent);
        displayEventResponseDto.setFrequencyInDays(cyclicEvent.getFrequencyInDays());
        displayEventResponseDto.setNumberOfRepeats(cyclicEvent.getNumberOfRepeats());
        return displayEventResponseDto;
    }

    public static DisplayEventResponseDto mapExternalEventToEventResponseDto(ExternalEvent externalEvent) {
        return mapEventToEventResponseDto(externalEvent);
    }
}
