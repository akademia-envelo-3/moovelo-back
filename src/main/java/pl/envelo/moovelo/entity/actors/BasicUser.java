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
    private List<Group> groups;

    @ManyToMany(mappedBy = "acceptedStatusUsers")
    private List<Event> accessibleEvents;

    @ManyToMany(mappedBy = "acceptedStatusUsers")
    private Set<Event> acceptedEvents;

    @ManyToMany(mappedBy = "pendingStatusUsers")
    private Set<Event> pendingEvents;

    @ManyToMany(mappedBy = "rejectedStatusUsers")
    private Set<Event> rejectedEvents;

    @ManyToMany
    @JoinTable(
            name = "basicUser_surveyAnswers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> surveyAnswers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicUser basicUser = (BasicUser) o;
        return Objects.equals(categoryProposals, basicUser.categoryProposals)
                && Objects.equals(comments, basicUser.comments) && Objects.equals(groups, basicUser.groups)
                && Objects.equals(acceptedEvents, basicUser.acceptedEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryProposals, comments, groups, acceptedEvents);
    }
}
