package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CommentRequestDto {

    private Long basicUserId;
    private String text;

}
