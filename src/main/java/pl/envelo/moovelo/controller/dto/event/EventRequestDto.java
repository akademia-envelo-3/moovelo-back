package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.group.GroupDto;

import java.util.List;

@Builder
@Getter
public class EventRequestDto {

    private EventInfoDto eventInfo;
    private int limitedPlaces;
    private List<HashtagDto> hashtags;
    private List<AttachmentDto> attachments;
    private List<Long> invited;
    private GroupDto group;
    private boolean isPrivate;
    private int frequencyInDays;
    private int numberOfRepeats;
}
