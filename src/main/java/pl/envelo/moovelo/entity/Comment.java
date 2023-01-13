package pl.envelo.moovelo.entity;

import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.events.Event;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    Long id;
    Event event;
    List<Attachment> attachments;
    BasicUser basicUser;
    String text;
    LocalDateTime date;
}
