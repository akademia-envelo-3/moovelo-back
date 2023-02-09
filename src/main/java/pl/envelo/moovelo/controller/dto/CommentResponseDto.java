package pl.envelo.moovelo.controller.dto;

import pl.envelo.moovelo.entity.events.Event;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long id;
    private Event event;
    private String text;
    private LocalDateTime localDateTime;
}
