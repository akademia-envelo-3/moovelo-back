package pl.envelo.moovelo.entity;

import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

public class Hashtag {
    Long id;
    String value;
    Boolean visible;
    List<Event> events;
    List<Comment> comments;
    int occurrences;
}
