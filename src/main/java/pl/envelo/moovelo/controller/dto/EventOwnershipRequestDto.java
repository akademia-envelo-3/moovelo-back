package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class EventOwnershipRequestDto {
    private long id;
    private long newOwnerId;
}
