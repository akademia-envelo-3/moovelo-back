package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.events.Event;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CommentResponseDto {

    private Long id;
    private BasicUserDto basicUserDto;
    private LocalDateTime date;
    private String text;
    private List<Attachment> attachments;
}
