package pl.envelo.moovelo.controller.dto.comment;

import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;

import java.util.List;

public class CommentResponseDto {
    private Long id;
    private List<AttachmentResponseDto> attachments;
}
