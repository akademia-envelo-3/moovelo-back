package pl.envelo.moovelo.controller.mapper.event;

import pl.envelo.moovelo.controller.dto.event.response.EventParticipationStatsDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.actor.VisitorMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EventParticipationStatsMapper {
    enum EventStatus {
        ACCEPTED,
        PENDING,
        REJECTED
    }

    public static EventParticipationStatsDto mapEventToEventParticipationStatsDto(Event event) {
        Set<BasicUser> acceptedStatuses = validateEmptyList(event, EventStatus.ACCEPTED);
        Set<BasicUser> pendingStatuses = validateEmptyList(event, EventStatus.PENDING);
        Set<BasicUser> rejectedStatuses = validateEmptyList(event, EventStatus.REJECTED);
        return EventParticipationStatsDto.builder()
                .accepted(acceptedStatuses.stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .pending(pendingStatuses.stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .rejected(rejectedStatuses.stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .build();
    }

    public static EventParticipationStatsDto mapExternalEventToEventParticipationStatsDto(ExternalEvent externalEvent) {
        return EventParticipationStatsDto.builder()
                .accepted(externalEvent.getAcceptedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .pending(externalEvent.getPendingStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .rejected(externalEvent.getRejectedStatusUsers().stream().map(BasicUserMapper::map).collect(Collectors.toSet()))
                .visitors(externalEvent.getVisitors().stream().map(VisitorMapper::map).collect(Collectors.toSet()))
                .build();
    }

    private static Set<BasicUser> validateEmptyList(Event event, EventStatus status) {
        Set<BasicUser> listToValidate;
        Set<BasicUser> validatedList;

        switch (status) {
            case ACCEPTED -> {
                listToValidate = event.getAcceptedStatusUsers();
                validatedList = getValidatedList(listToValidate);
            }
            case PENDING -> {
                listToValidate = event.getPendingStatusUsers();
                validatedList = getValidatedList(listToValidate);
            }
            case REJECTED -> {
                listToValidate = event.getRejectedStatusUsers();
                validatedList = getValidatedList(listToValidate);
            }
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }
        return validatedList;
    }

    private static Set<BasicUser> getValidatedList(Set<BasicUser> resultList) {
        return Objects.requireNonNullElseGet(resultList, HashSet::new);
    }
}
