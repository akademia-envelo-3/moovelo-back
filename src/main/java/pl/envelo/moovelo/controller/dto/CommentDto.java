package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import pl.envelo.moovelo.entity.Attachment;

import java.util.List;

@Builder
public class CommentDto {
    private long id;
    private long commentId;
    private BasicUserDto user;
    private String date;
    private String text;
    private List<AttachmentDto> attachments;
}
