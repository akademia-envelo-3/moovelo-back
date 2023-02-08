package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.HashtagDto;
import pl.envelo.moovelo.controller.dto.HashtagListResponseDto;
import pl.envelo.moovelo.entity.Hashtag;

//TODO zmienci nazwe HashtagMapper?
public class HashtagListResponseMapper {

    public static HashtagListResponseDto mapHashtagToHashtagListResponseDto(Hashtag hashtag) {
        return HashtagListResponseDto.builder()
                .id(hashtag.getId())
                .value(hashtag.getHashtagValue())
                .build();
    }

    //TODO w ktoryms miejscu trzeba zweryfikowac z repo
    //TODO Edit: Nigdy nie stworzymy hasztaga bez Eventu?
    public static Hashtag mapHashTagDtoToHashtag(HashtagDto hashtagDto) {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagValue(hashtagDto.getValue());
//        hashtag.setVisible(true);  TODO nie powinno sie tego ustawiac?
        return hashtag;
    }
}
