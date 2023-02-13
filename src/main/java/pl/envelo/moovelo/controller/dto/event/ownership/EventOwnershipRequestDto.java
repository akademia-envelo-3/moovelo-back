package pl.envelo.moovelo.controller.dto.event.ownership;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventOwnershipRequestDto {
    private Long newOwnerUserId;

    public EventOwnershipRequestDto(Long newOwnerUserId) {
        this.newOwnerUserId = newOwnerUserId;
    }
}

