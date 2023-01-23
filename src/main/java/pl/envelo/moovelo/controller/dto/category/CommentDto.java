package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.user.BasicUserDto;

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
