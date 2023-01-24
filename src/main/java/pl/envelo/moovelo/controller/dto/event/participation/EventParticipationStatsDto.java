package pl.envelo.moovelo.controller.dto.event.participation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventParticipationStatsDto {
    private long eventId;
    private int accepted;
    private int pending;
    private int rejected;
}
