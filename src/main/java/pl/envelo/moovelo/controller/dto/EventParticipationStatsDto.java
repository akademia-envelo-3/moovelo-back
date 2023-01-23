package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class EventParticipationStatsDto {
    private int accepted;
    private int pending;
    private int rejected;
}
