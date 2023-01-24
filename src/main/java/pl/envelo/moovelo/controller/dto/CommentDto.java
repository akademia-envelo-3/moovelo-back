package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

import java.util.List;

@Builder
@Getter
public class CommentDto {
    private long id;
    private long commentId;
    private BasicUserDto user;
    private String date;
    private String text;
    private List<AttachmentDto> attachments;
}
