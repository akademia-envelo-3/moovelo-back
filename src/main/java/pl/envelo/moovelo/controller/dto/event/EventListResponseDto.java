package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.event.ownership.EventOwnerDto;
import pl.envelo.moovelo.controller.dto.group.GroupDto;

import java.util.List;

@Builder
@Getter
public class EventListResponseDto {

    private Long id;
    private EventOwnerDto eventOwner;
    private EventInfoDto eventInfo;
    private String startDate;
    private List<HashtagDto> hashtags;
    private boolean isConfirmationRequired;
    private boolean isPrivate;
    private boolean group;
    private boolean isCyclic;
    private String city;
    private int acceptedStatusUsers;
}
