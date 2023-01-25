package pl.envelo.moovelo.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.event.EventIdDto;

import java.util.List;

@Builder
@Getter
public class EventOwnerDto {
    private long id;
    @JsonProperty("newOwnerUserId")
    private long userId;
    private String firstname;
    private String lastname;
    private List<EventIdDto> events;
}
