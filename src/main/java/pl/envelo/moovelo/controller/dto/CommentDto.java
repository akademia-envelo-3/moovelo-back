package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

@Builder
@Getter
public class CommentDto {
    private BasicUserDto user;
    private String text;
}
