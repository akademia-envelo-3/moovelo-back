package pl.envelo.moovelo.controller.dto.event.ownership;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventOwnerListResponseDto {
    private Long userId;
}
