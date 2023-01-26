package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.HashtagListResponseDto;
import pl.envelo.moovelo.entity.Hashtag;

public class HashtagListResponseMapper {

    public static HashtagListResponseDto mapHashtagToHashtagListResponseDto(Hashtag hashtag) {
        return HashtagListResponseDto.builder()
                .id(hashtag.getId())
                .value(hashtag.getHashtagValue())
                .build();
    }
}
