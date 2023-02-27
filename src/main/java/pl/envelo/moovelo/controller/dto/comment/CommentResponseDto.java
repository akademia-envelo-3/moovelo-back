package pl.envelo.moovelo.controller.dto.comment;

import lombok.Data;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;

import java.util.List;

@Data
public class CommentResponseDto {
    private Long id;
    private List<AttachmentResponseDto> attachments;
}
