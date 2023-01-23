package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.group.GroupDto;
import pl.envelo.moovelo.controller.dto.location.LocationDto;

import java.util.List;

@Builder
public class EventCreateDto {
    private boolean isPrivate;
    private GroupDto group;
    private EventInfoDto eventInfo;
    private boolean isConfirmationRequired;
    private LocationDto location;
    private int limitedPlaces;
    private List<HashtagDto> hashtags;
    private List<AttachmentDto> attachments;
}
