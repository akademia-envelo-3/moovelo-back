package pl.envelo.moovelo.controller.dto.event.participation;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

import java.util.List;

@Builder
@Getter
public class EventAcceptedStatusDto {
    private long eventId;
    List<BasicUserDto> users;
    List<VisitorDto> visitors;
}
