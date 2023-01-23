package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class EventResponseDto {
    private long id;
    private EventOwnerDto eventOwner;
    private EventInfoDto eventInfo;
    private int limitedPlaces;
    private boolean isCyclic;
    private String city;
    private List<CommentDto> comments;
    private List<EventSurveyDto> eventSurveys;
    private boolean isPrivate;
    private GroupDto group;
    private int acceptedStatusUsers;
    private List<BasicUserDto> users;
    private List<HashtagDto> hashtags;
    private EventParticipationStatsDto eventParticipationStats;
    private int frequencyInDays;
    private int numberOfRepeats;
}
