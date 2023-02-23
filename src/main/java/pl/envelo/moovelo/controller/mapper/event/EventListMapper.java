package pl.envelo.moovelo.controller.mapper.event;

import org.springframework.util.ObjectUtils;
import pl.envelo.moovelo.controller.dto.event.response.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.HashtagMapper;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;

public class EventListMapper implements EventMapperInterface {

    @Override
    public EventListResponseDto mapEventToEventResponseDto(Event event) {
        return mapBasicEventToEventListResponseDto(event);
    }

    @Override
    public EventListResponseDto mapInternalEventToEventResponseDto(InternalEvent event) {
        EventListResponseDto internalEventListResponseDto = mapBasicEventToEventListResponseDto(event);
        internalEventListResponseDto.setPrivate(event.isPrivate());
        internalEventListResponseDto.setGroup((!ObjectUtils.isEmpty(event.getGroup())));
        return internalEventListResponseDto;
    }

    @Override
    public EventListResponseDto mapCyclicEventToEventResponseDto(CyclicEvent event) {
        EventListResponseDto cyclicEventListResponseDto = mapInternalEventToEventResponseDto(event);
        cyclicEventListResponseDto.setCyclic(event.getNumberOfRepeats() > 0);
        return cyclicEventListResponseDto;
    }

    // TODO: 21.02.2023  ogarnac External
    @Override
    public EventListResponseDto mapExternalEventToEventResponseDto(ExternalEvent event) {
        EventListResponseDto eventListResponseDto = mapBasicEventToEventListResponseDto(event);
        return eventListResponseDto;
    }

    private EventListResponseDto mapBasicEventToEventListResponseDto(Event event) {
        EventListResponseDto eventListResponseDto = new EventListResponseDto();
        eventListResponseDto.setId(event.getId());
        eventListResponseDto.setEventOwner(EventOwnerMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()));
        eventListResponseDto.setEventInfo(EventInfoMapper.mapEventInfoToEventInfoListResponseDto(event.getEventInfo()));
        eventListResponseDto.setHashtags(event.getHashtags().stream()
                .map(HashtagMapper::mapHashtagToHashtagListResponseDto)
                .toList());
        eventListResponseDto.setStartDate(event.getEventInfo().getStartDate().toString());
        eventListResponseDto.setConfirmationRequired(event.getEventInfo().getIsConfirmationRequired());
        eventListResponseDto.setPrivate(false);
        eventListResponseDto.setGroup(false);
        eventListResponseDto.setCyclic(false);
        eventListResponseDto.setCity(event.getEventInfo().getLocation().getCity());
        eventListResponseDto.setAcceptedStatusUsers(event.getAcceptedStatusUsers().size());
        return eventListResponseDto;
    }
}
