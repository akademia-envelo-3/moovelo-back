package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.EventOwnerListResponseMapper;
import pl.envelo.moovelo.controller.mapper.HashtagListResponseMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
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
        return getMappedEventByEventType(eventRequestDto, eventType);
    }

    private <T extends Event> T getMappedEventByEventType(EventRequestDto eventRequestDto, EventType eventType) {
        T event = null;
        switch (eventType) {
            case EVENT -> event = (T) returnMappedEvent(eventRequestDto, eventType);
            case INTERNAL_EVENT -> event = (T) returnMappedInternalEvent(eventRequestDto, eventType);
            case CYCLIC_EVENT -> event = (T) returnMappedCyclicEvent(eventRequestDto, eventType);
            case EXTERNAL_EVENT -> event = (T) returnMappedExternalEvent(eventRequestDto, eventType);
        }
        return event;
    }

    private static Event returnMappedEvent(EventRequestDto eventRequestDto, EventType eventType) {
        Event event = new Event();
        setMappedFieldsForEvent(eventRequestDto, eventType, event);
        return event;
    }

    private InternalEvent returnMappedInternalEvent(EventRequestDto eventRequestDto, EventType eventType) {
        InternalEvent internalEvent = new InternalEvent();
        setMappedFieldsForInternalEvent(eventRequestDto, eventType, internalEvent);
        return internalEvent;
    }

    private CyclicEvent returnMappedCyclicEvent(EventRequestDto eventRequestDto, EventType eventType) {
        CyclicEvent cyclicEvent = new CyclicEvent();
        setMappedFieldsForInternalEvent(eventRequestDto, eventType, cyclicEvent);
        cyclicEvent.setFrequencyInDays(eventRequestDto.getFrequencyInDays());
        cyclicEvent.setNumberOfRepeats(eventRequestDto.getNumberOfRepeats());
        return cyclicEvent;
    }

    private ExternalEvent returnMappedExternalEvent(EventRequestDto eventRequestDto, EventType eventType) {
        ExternalEvent externalEvent = new ExternalEvent();
        setMappedFieldsForEvent(eventRequestDto, eventType, externalEvent);
        return externalEvent;
    }

    private static <T extends Event> T setMappedFieldsForEvent(EventRequestDto eventRequestDto, EventType eventType, T event) {
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

    private static void setMappedFieldsForInternalEvent(EventRequestDto eventRequestDto, EventType eventType,
                                                        InternalEvent internalEvent) {
        setMappedFieldsForEvent(eventRequestDto, eventType, internalEvent);
        internalEvent.setPrivate(eventRequestDto.isPrivate());
        internalEvent.setGroup(new Group());
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
