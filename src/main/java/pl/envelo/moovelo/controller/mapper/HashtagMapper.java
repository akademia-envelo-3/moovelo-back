package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.entity.Hashtag;

public class HashtagMapper {

    public static HashtagDto mapHashtagToHashtagDto(Hashtag hashtag) {
        return HashtagDto.builder()
                .id(hashtag.getId())
                .value(hashtag.getHashtagValue())
                .build();
    }
}
