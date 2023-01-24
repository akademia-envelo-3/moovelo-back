package pl.envelo.moovelo.controller.dto.event.ownership;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.event.EventResponseDto;

import java.util.List;

@Builder
@Getter
public class EventOwnerDto {
    private long id;
    private long basicUserId;
    private String firstname;
    private String lastname;
    private List<EventResponseDto> events;

}
