package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class CyclicEvent extends InternalEvent {
    private Integer frequencyInDays;
    private Integer numberOfRepeats;

    public CyclicEvent() {
    }

    public CyclicEvent(Long id, EventOwner eventOwner, EventInfo eventInfo, int limitedPlaces,
                       List<Comment> comments, List<EventSurvey> eventSurveys,
                       Set<BasicUser> acceptedStatusUsers, Set<BasicUser> pendingStatusUsers,
                       Set<BasicUser> rejectedStatusUsers, List<Hashtag> hashtags, Group group,
                       List<BasicUser> invited, boolean isPrivate, Integer frequencyInDays,
                       Integer numberOfRepeats) {
        super(id, eventOwner, eventInfo, limitedPlaces, comments, eventSurveys,
                acceptedStatusUsers, pendingStatusUsers, rejectedStatusUsers,
                hashtags, group, invited, isPrivate);
        this.frequencyInDays = frequencyInDays;
        this.numberOfRepeats = numberOfRepeats;
    }

    public CyclicEvent(Group group) {
        super(group);
    }

    public CyclicEvent(List<BasicUser> users) {
        super(users);
    }

}
