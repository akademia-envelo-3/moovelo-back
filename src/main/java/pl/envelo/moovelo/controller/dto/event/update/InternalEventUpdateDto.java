package pl.envelo.moovelo.controller.dto.event.update;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.event.EventInfoDto;
import pl.envelo.moovelo.controller.dto.location.LocationDto;

import java.util.List;

@Builder
@Getter
public class InternalEventUpdateDto {
    private long id;
    private EventInfoDto eventInfo;
    private LocationDto location;
    private int limitedPlaces;
    private List<HashtagDto> hashtags;
    private List<Long> invited;
    private List<AttachmentDto> attachments;
}
