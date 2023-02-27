package pl.envelo.moovelo.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.categories.CategoryProposal;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.surveys.Answer;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BasicUser extends User {

    @OneToMany(mappedBy = "basicUser")
    private List<CategoryProposal> categoryProposals;

    @OneToMany
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Set<Group> groups;

    @ManyToMany(mappedBy = "acceptedStatusUsers")
    private List<Event> accessibleEvents;

    @ManyToMany(mappedBy = "acceptedStatusUsers")
    private Set<Event> acceptedEvents;

    @ManyToMany(mappedBy = "pendingStatusUsers")
    private Set<Event> pendingEvents;

    @ManyToMany(mappedBy = "rejectedStatusUsers")
    private Set<Event> rejectedEvents;

    @ManyToMany(mappedBy = "basicUsers")
    private List<Answer> surveyAnswers;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
