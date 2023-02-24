package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.actor.UserIdDto;
import pl.envelo.moovelo.controller.dto.event.eventInfo.EventInfoDto;
import pl.envelo.moovelo.controller.dto.hashtag.HashtagDto;

import java.util.List;

@Builder
@Getter
public class EventRequestDto {

    private EventInfoDto eventInfo;
    private int limitedPlaces;
    private List<HashtagDto> hashtags;
    private List<AttachmentDto> attachments;
    private List<UserIdDto> invited;
    private Long groupId;
    private boolean isPrivate;
    private int frequencyInDays;
    private int numberOfRepeats;
}
