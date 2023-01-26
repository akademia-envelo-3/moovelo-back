package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.HashtagListResponseDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerListResponseDto;

import java.util.List;

@Builder
@Getter
public class EventListResponseDto {

    private Long id;
    private EventOwnerListResponseDto eventOwner;
    private EventInfoListResponseDto eventInfo;
    private String startDate;
    private List<HashtagListResponseDto> hashtags;
    private boolean isConfirmationRequired;
    private boolean isPrivate;
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
