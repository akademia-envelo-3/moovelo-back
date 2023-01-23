package pl.envelo.moovelo.controller.dto.event.eventownership;

import lombok.Builder;

@Builder
public class EventOwnershipRequestDto {
    private long id;
    private long newOwnerId;
}
