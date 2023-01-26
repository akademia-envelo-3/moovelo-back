package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HashtagListResponseDto {
    private Long id;
    private String value;
}
