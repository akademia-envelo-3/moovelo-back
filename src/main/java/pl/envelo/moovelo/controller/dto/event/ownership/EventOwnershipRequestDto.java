package pl.envelo.moovelo.controller.dto.event.ownership;

import lombok.Getter;

@Getter
public class EventOwnershipRequestDto {

    private long userId;
    private long eventId;
    private long newOwnerId;

    public EventOwnershipRequestDto(long userId, long eventId, long newOwnerId) {
        this.userId = userId;
        this.eventId = eventId;
        this.newOwnerId = newOwnerId;
    }
}
