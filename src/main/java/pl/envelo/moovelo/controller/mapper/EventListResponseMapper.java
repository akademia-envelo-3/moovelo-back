package pl.envelo.moovelo.controller.mapper;

import org.springframework.util.ObjectUtils;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.*;

import java.util.List;
import java.util.Set;

public class EventListResponseMapper {

    public static EventListResponseDto mapBasicEventToEventListResponseDto(Event event) {
        return mapEventToEventListResponseDto(event.getId(), event.getEventOwner(), event.getEventInfo(),
                event.getHashtags(), event.getAcceptedStatusUsers(), event);
    }

    public static EventListResponseDto mapInternalEventToEventListResponseDto(InternalEvent event) {
        return EventListResponseDto.builder()
                .id(event.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()))
                .eventInfo(EventInfoListResponseMapper.mapEventInfoToEventInfoListResponseDto(event.getEventInfo()))
                .hashtags(event.getHashtags().stream()
                        .map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .toList())
                .startDate(event.getEventInfo().getStartDate().toString())
                .isConfirmationRequired(event.getEventInfo().getIsConfirmationRequired())
                .isPrivate(event.isPrivate())
                .group(!ObjectUtils.isEmpty(event.getGroup()))
                .isCyclic(false)
                .city(event.getEventInfo().getLocation().getCity())
                .acceptedStatusUsers(event.getAcceptedStatusUsers().size())
                .build();
    }

    public static EventListResponseDto mapCyclicEventToEventListResponseDto(CyclicEvent event) {
        return EventListResponseDto.builder()
                .id(event.getId())
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(event.getEventOwner()))
                .eventInfo(EventInfoListResponseMapper.mapEventInfoToEventInfoListResponseDto(event.getEventInfo()))
                .hashtags(event.getHashtags().stream()
                        .map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .toList())
                .startDate(event.getEventInfo().getStartDate().toString())
                .isConfirmationRequired(event.getEventInfo().getIsConfirmationRequired())
                .isPrivate(event.isPrivate())
                .group(!ObjectUtils.isEmpty(event.getGroup()))
                .isCyclic(event.getNumberOfRepeats() > 0)
                .city(event.getEventInfo().getLocation().getCity())
                .acceptedStatusUsers(event.getAcceptedStatusUsers().size())
                .build();
    }

    public static EventListResponseDto mapExternalEventToEventListResponseDto(ExternalEvent event) {
        return mapEventToEventListResponseDto(event.getId(), event.getEventOwner(), event.getEventInfo(),
                event.getHashtags(), event.getAcceptedStatusUsers(), event);
    }

    private static EventListResponseDto mapEventToEventListResponseDto(
            Long id, EventOwner eventOwner, EventInfo eventInfo,
            List<Hashtag> hashtags, Set<BasicUser> acceptedStatusUsers, Event event) {
        return EventListResponseDto.builder()
                .id(id)
                .eventOwner(EventOwnerListResponseMapper.mapEventOwnerToEventOwnerListResponseDto(eventOwner))
                .eventInfo(EventInfoListResponseMapper.mapEventInfoToEventInfoListResponseDto(eventInfo))
                .hashtags(hashtags.stream()
                        .map(HashtagListResponseMapper::mapHashtagToHashtagListResponseDto)
                        .toList())
                .startDate(event.getEventInfo().getStartDate().toString())
                .isConfirmationRequired(eventInfo.getIsConfirmationRequired())
                .isPrivate(false)
                .group(false)
                .isCyclic(false)
                .city(eventInfo.getLocation().getCity())
                .acceptedStatusUsers(acceptedStatusUsers.size())
                .build();
    }
}
