package pl.envelo.moovelo.entity;

import pl.envelo.moovelo.entity.events.EventInfo;

import java.io.File;

public class Attachment {
    Long id;
    File file;
    EventInfo eventInfo;
    Comment comment;

    public Attachment(EventInfo info){

    }

    public Attachment(Comment comment){

    }
}
