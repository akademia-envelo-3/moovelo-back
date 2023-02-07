package pl.envelo.moovelo.controller.dto;

import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDto {

    private Long id;
    private Event event;
    private String text;
    private LocalDateTime localDateTime;
}
