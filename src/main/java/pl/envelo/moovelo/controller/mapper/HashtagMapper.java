package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.hashtag.HashtagDto;
import pl.envelo.moovelo.controller.dto.hashtag.HashtagListResponseDto;
import pl.envelo.moovelo.entity.Hashtag;

public class HashtagMapper {

    public static HashtagListResponseDto mapHashtagToHashtagListResponseDto(Hashtag hashtag) {
        return HashtagListResponseDto.builder()
                .id(hashtag.getId())
                .value(hashtag.getHashtagValue())
                .build();
    }

    public static Hashtag mapHashTagDtoToHashtag(HashtagDto hashtagDto) {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagValue(hashtagDto.getValue());
        return hashtag;
    }
}
