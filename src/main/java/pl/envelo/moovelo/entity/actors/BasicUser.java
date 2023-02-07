package pl.envelo.moovelo.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.categories.CategoryProposal;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.groups.Group;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BasicUser extends User {

    @OneToMany(mappedBy = "basicUser")
    private List<CategoryProposal> categoryProposals;

    @OneToMany
    private List<Comment> comments;

    @ManyToMany
    private List<Group> groups;
    @ManyToMany(mappedBy = "usersWithAccess")
    private List<Event> accessibleEvents;

    @OneToMany
    private List<Event> acceptedEvents;
//    private List<Event> acceptedEvents;
//    private List<Event> acceptedEvents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
