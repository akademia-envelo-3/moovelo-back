package pl.envelo.moovelo.controller.dto.event.participation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.actor.VisitorDto;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class EventParticipationStatsDto {
    private Set<BasicUserDto> accepted;
    private Set<BasicUserDto> pending;
    private Set<BasicUserDto> rejected;
    private Set<VisitorDto> visitors;
}
