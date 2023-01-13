package pl.envelo.moovelo.entity.events;

import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import java.util.List;
import java.util.Set;

public class Event {
    Long id;
    EventOwner eventOwner;
    EventInfo eventInfo;
    int limitedPlaces;
    List<Comment> comments;
    List<EventSurvey> eventSurveys;
    Set<BasicUser> acceptedStatusUsers;
    Set<BasicUser> pendingStatusUsers;
    Set<BasicUser> rejectedStatusUsers;
    List<Hashtag> hashtags;
}
