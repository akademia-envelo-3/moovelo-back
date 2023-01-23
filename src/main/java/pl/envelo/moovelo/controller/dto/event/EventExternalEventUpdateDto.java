package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.location.LocationDto;

import java.util.List;

@Builder
public class EventExternalEventUpdateDto {
    private long id;
    private EventInfoDto eventInfo;
    private LocationDto location;
    private int limitedPlaces;
    private List<HashtagDto> hashtags;
    private List<AttachmentDto> attachments;
}
