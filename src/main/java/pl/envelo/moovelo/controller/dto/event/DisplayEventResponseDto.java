package pl.envelo.moovelo.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.HashtagListResponseDto;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerListResponseDto;
import pl.envelo.moovelo.controller.dto.event.participation.EventParticipationStatsDto;
import pl.envelo.moovelo.controller.dto.group.groupownership.GroupResponseDto;

import java.util.List;

@Builder
@Getter
public class DisplayEventResponseDto {
    @JsonProperty("eventId")
    private long id;
    private EventOwnerListResponseDto eventOwner;
    private EventInfoDto eventInfo;
    private int limitedPlaces;
    private boolean isCyclic;
    private boolean isPrivate;
    private GroupResponseDto group;
    private List<HashtagListResponseDto> hashtags;
    private EventParticipationStatsDto eventParticipationStats;
    private int frequencyInDays;
    private int numberOfRepeats;

    @JsonProperty("isCyclic")
    public boolean isCyclic() {
        return isCyclic;
    }

    @JsonProperty("isPrivate")
    public boolean isPrivate() {
        return isPrivate;
    }
}
