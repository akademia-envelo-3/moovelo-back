package pl.envelo.moovelo.controller.dto.event.participation;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;

import java.util.List;

@Builder
@Getter

public class EventParticipationStatsDto {
    private List<BasicUserDto> accepted;
    private List<BasicUserDto> pending;
    private List<BasicUserDto> rejected;
    private List<VisitorDto> visitors;
}
