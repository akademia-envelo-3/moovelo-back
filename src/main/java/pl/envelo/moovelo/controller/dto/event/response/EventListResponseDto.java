package pl.envelo.moovelo.controller.dto.event.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.controller.dto.hashtag.HashtagListResponseDto;
import pl.envelo.moovelo.controller.dto.event.eventInfo.EventInfoListResponseDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerListResponseDto;

import java.util.List;

@Setter
@Getter
public class EventListResponseDto {

    private Long id;
    private EventOwnerListResponseDto eventOwner;
    private EventInfoListResponseDto eventInfo;
    private boolean isConfirmationRequired;
    private List<HashtagListResponseDto> hashtags;
    private boolean isPrivate;
    private String startDate;
    private boolean group;
    private boolean isCyclic;
    private String city;
    private int acceptedStatusUsers;

    @JsonProperty(value = "isConfirmationRequired")
    public boolean isConfirmationRequired() {
        return isConfirmationRequired;
    }

    @JsonProperty(value = "isPrivate")
    public boolean isPrivate() {
        return isPrivate;
    }

    @JsonProperty(value = "isCyclic")
    public boolean isCyclic() {
        return isCyclic;
    }
}
