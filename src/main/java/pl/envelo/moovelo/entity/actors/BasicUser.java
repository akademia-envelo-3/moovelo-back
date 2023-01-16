package pl.envelo.moovelo.entity.actors;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.categories.CategoryProposal;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "BASIC_USER")
@Getter
@Setter
public class BasicUser extends User {

    @OneToMany
    private List<InternalEvent> accessiblePrivateEvents;

    @OneToMany(mappedBy = "basicUser")
    private List<CategoryProposal> categoryProposals;

    @OneToMany
    private List<Comment> comments;

    @ManyToMany
    private List<Group> groups;

    @OneToMany
    private List<Event> acceptedEvents;

    public BasicUser() {
    }

    public BasicUser(String firstname, String lastname, String email, String login, String password, Role role, List<InternalEvent> accessiblePrivateEvents, List<CategoryProposal> categoryProposals, List<Comment> comments, List<Group> groups, List<Event> acceptedEvents) {
        super(firstname, lastname, email, login, password, role);
        this.accessiblePrivateEvents = accessiblePrivateEvents;
        this.categoryProposals = categoryProposals;
        this.comments = comments;
        this.groups = groups;
        this.acceptedEvents = acceptedEvents;
    }
}
