package pl.envelo.moovelo.controller.dto.event.participation;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

import java.util.List;

@Builder
public class EventAcceptedStatusDto {
    List<BasicUserDto> users;
    List<VisitorDto> visitors;
}
