package pl.envelo.moovelo.controller.dto.comment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponseDto {
    private Long id;
    private Long eventId;
    private BasicUserDto user;
    private String text;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    private List<AttachmentResponseDto> attachments;
}
