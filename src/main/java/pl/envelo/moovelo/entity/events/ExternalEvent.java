package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class ExternalEvent extends Event {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "external_events_visitors",
            joinColumns = @JoinColumn(name = "external_event_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    private List<Visitor> visitors;

    public ExternalEvent() {
    }

    public ExternalEvent(Long id, EventOwner eventOwner, EventInfo eventInfo, int limitedPlaces,
                         List<Comment> comments, List<EventSurvey> eventSurveys,
                         Set<BasicUser> acceptedStatusUsers, Set<BasicUser> pendingStatusUsers,
                         Set<BasicUser> rejectedStatusUsers, List<Hashtag> hashtags,
                         List<Visitor> visitors) {
        super(id, eventOwner, eventInfo, limitedPlaces, comments, eventSurveys, acceptedStatusUsers, pendingStatusUsers, rejectedStatusUsers, hashtags);
        this.visitors = visitors;
    }
}
