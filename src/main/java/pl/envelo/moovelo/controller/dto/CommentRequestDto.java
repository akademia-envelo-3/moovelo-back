package pl.envelo.moovelo.controller.dto;

import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

public class CommentRequestDto {

    private Long id;
    private List<Attachment> attachments;
    private String text;

}
