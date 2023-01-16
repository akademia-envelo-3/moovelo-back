package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class InternalEvent extends Event {

    @ManyToOne
    private Group group;

    @OneToMany
    private List<BasicUser> invited;

    private boolean isPrivate;

    public InternalEvent() {
    }

    public InternalEvent(Long id, EventOwner eventOwner, EventInfo eventInfo,
                         int limitedPlaces, List<Comment> comments, List<EventSurvey> eventSurveys,
                         Set<BasicUser> acceptedStatusUsers, Set<BasicUser> pendingStatusUsers,
                         Set<BasicUser> rejectedStatusUsers, List<Hashtag> hashtags,
                         Group group, List<BasicUser> invited, boolean isPrivate) {
        super(id, eventOwner, eventInfo, limitedPlaces, comments, eventSurveys,
                acceptedStatusUsers, pendingStatusUsers, rejectedStatusUsers, hashtags);
        this.group = group;
        this.invited = invited;
        this.isPrivate = isPrivate;
    }

    public InternalEvent(Group group) {

    }

    public InternalEvent(List<BasicUser> users) {

    }
}
