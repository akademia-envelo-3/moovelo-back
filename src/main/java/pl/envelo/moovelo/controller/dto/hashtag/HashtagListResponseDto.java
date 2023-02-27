package pl.envelo.moovelo.controller.dto.hashtag;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HashtagListResponseDto {
    private Long id;
    private String value;
}
