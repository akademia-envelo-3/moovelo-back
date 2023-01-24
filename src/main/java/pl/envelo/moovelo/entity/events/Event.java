package pl.envelo.moovelo.entity.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EventOwner eventOwner;

    @OneToOne
    private EventInfo eventInfo;

    private int limitedPlaces;

    @OneToMany(mappedBy = "event")
    private List<Comment> comments;

    @OneToMany(mappedBy = "event")
    private List<EventSurvey> eventSurveys;

    @OneToMany
    private List<BasicUser> usersWithAccess;

    @OneToMany
    private Set<BasicUser> acceptedStatusUsers;

    @OneToMany
    private Set<BasicUser> pendingStatusUsers;

    @OneToMany
    private Set<BasicUser> rejectedStatusUsers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "events_hashtags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;
}
