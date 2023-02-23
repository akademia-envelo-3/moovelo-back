package pl.envelo.moovelo.controller.mapper.event.manager;

import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.dto.event.response.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.response.EventResponseDto;
import pl.envelo.moovelo.controller.mapper.HashtagMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.event.EventInfoMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapperInterface;
import pl.envelo.moovelo.controller.mapper.event.EventOwnerMapper;
import pl.envelo.moovelo.controller.mapper.event.EventParticipationStatsMapper;
import pl.envelo.moovelo.controller.mapper.group.GroupMapper;
import pl.envelo.moovelo.entity.events.*;

import java.util.stream.Collectors;

public class EventMapper implements EventMapperInterface {

    public static EventIdDto mapEventToEventIdDto(Event event) {
        return new EventIdDto(event.getId());
    }

    static <T extends Event> void mapEventRequestDtoToEvent(EventRequestDto eventRequestDto, EventType eventType, T event) {
        event.setEventOwner(new EventOwner());
        event.setEventInfo(EventInfoMapper.mapEventInfoDtoToEventInfo(eventRequestDto.getEventInfo()));
        event.setLimitedPlaces(eventRequestDto.getLimitedPlaces());
        event.setEventType(eventType);
        // TODO: 16.02.2023 przenosimy do EventService
//        event.setComments(new ArrayList<>());
//        event.setUsersWithAccess(new ArrayList<>());
//        event.setAcceptedStatusUsers(new HashSet<>());
//        event.setRejectedStatusUsers(new HashSet<>());
//        event.setPendingStatusUsers(new HashSet<>());
        event.setHashtags(eventRequestDto.getHashtags().stream()
                .map(HashtagMapper::mapHashTagDtoToHashtag)
                .collect(Collectors.toList())
        );
    }

    static <T extends InternalEvent> void mapEventRequestDtoToInternalEvent(EventRequestDto eventRequestDto, EventType eventType,
                                                                            T internalEvent) {
        mapEventRequestDtoToEvent(eventRequestDto, eventType, internalEvent);
        internalEvent.setPrivate(eventRequestDto.isPrivate());
    }

    static void mapEventRequestDtoToCyclicEvent(EventRequestDto eventRequestDto, EventType eventType, CyclicEvent cyclicEvent) {
        mapEventRequestDtoToInternalEvent(eventRequestDto, eventType, cyclicEvent);
        cyclicEvent.setFrequencyInDays(eventRequestDto.getFrequencyInDays());
        cyclicEvent.setNumberOfRepeats(eventRequestDto.getNumberOfRepeats());
    }

    public EventResponseDto mapEventToEventResponseDto(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setEventOwner(EventOwnerMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()));
        eventResponseDto.setEventInfo(EventInfoMapper.mapEventInfoToEventInfoDto(event.getEventInfo()));
        eventResponseDto.setLimitedPlaces(event.getLimitedPlaces());
        eventResponseDto.setUsersWithAccess(event.getUsersWithAccess().stream().map(BasicUserMapper::map).collect(Collectors.toList()));
        eventResponseDto.setEventParticipationStats(EventParticipationStatsMapper.mapEventToEventParticipationStatsDto(event));
        eventResponseDto.setPrivate(false);
        eventResponseDto.setCyclic(false);
        eventResponseDto.setHashtags(event.getHashtags().stream().map(HashtagMapper::mapHashtagToHashtagListResponseDto)
                .collect(Collectors.toList()));
        return eventResponseDto;
    }

    public EventResponseDto mapInternalEventToEventResponseDto(InternalEvent internalEvent) {
        EventResponseDto eventResponseDto = mapEventToEventResponseDto(internalEvent);
        eventResponseDto.setGroup(GroupMapper.mapGroupToGroupResponseDtoForEvent(internalEvent.getGroup()));
        eventResponseDto.setPrivate(internalEvent.isPrivate());
        return eventResponseDto;
    }

    public EventResponseDto mapCyclicEventToEventResponseDto(CyclicEvent cyclicEvent) {
        EventResponseDto eventResponseDto = mapInternalEventToEventResponseDto(cyclicEvent);
        eventResponseDto.setFrequencyInDays(cyclicEvent.getFrequencyInDays());
        eventResponseDto.setNumberOfRepeats(cyclicEvent.getNumberOfRepeats());
        return eventResponseDto;
    }

    public EventResponseDto mapExternalEventToEventResponseDto(ExternalEvent externalEvent) {
        // TODO: 22.02.2023 mappaer dla externl event z EventPArticipationStats
        return mapEventToEventResponseDto(externalEvent);
    }
}
