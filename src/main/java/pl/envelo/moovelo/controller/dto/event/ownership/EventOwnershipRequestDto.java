package pl.envelo.moovelo.controller.dto.event.ownership;

import lombok.Builder;

@Builder
public class EventOwnershipRequestDto {
    private long eventId;
    private long newOwnerId;
}
