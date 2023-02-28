package pl.envelo.moovelo.controller.dto.event.response;

import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;

import java.util.Set;

@Getter
@Setter
public class EventParticipationStatsDto {
    private Set<BasicUserDto> accepted;
    private Set<BasicUserDto> pending;
    private Set<BasicUserDto> rejected;
    private Set<VisitorDto> visitors;
}
