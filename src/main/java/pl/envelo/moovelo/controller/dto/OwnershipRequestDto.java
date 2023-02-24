package pl.envelo.moovelo.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnershipRequestDto {
    private Long newOwnerUserId;

    // TODO: 24.02.2023 remove controctur if not neccessary
//    public EventOwnershipRequestDto(Long newOwnerUserId) {
//        this.newOwnerUserId = newOwnerUserId;
//    }
}

