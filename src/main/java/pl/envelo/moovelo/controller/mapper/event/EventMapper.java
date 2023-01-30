package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.EventIdDto;
import pl.envelo.moovelo.controller.dto.event.EventRequestDto;
import pl.envelo.moovelo.controller.mapper.HashtagListResponseMapper;
import pl.envelo.moovelo.entity.events.*;

import java.util.ArrayList;
import java.util.HashSet;
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
        event.setComments(new ArrayList<>());
        event.setUsersWithAccess(new ArrayList<>());
        event.setAcceptedStatusUsers(new HashSet<>());
        event.setRejectedStatusUsers(new HashSet<>());
        event.setPendingStatusUsers(new HashSet<>());
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
}

