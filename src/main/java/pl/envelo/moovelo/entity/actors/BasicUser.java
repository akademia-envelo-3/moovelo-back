package pl.envelo.moovelo.entity.actors;

import pl.envelo.moovelo.entity.categories.CategoryProposal;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.List;

public class BasicUser extends User {
    List<InternalEvent> accessiblePrivateEvents;
    List<CategoryProposal> categoryProposals;
    List<Comment> comments;
    List<Group> groups;
    List<Event> acceptedEvents;
}
