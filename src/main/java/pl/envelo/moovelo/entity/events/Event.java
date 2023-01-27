package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Event {

    public Event() {
        this.eventType = EventType.EVENT;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EventOwner eventOwner;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EventInfo eventInfo;

    private int limitedPlaces;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventSurvey> eventSurveys;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BasicUser> usersWithAccess;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BasicUser> acceptedStatusUsers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BasicUser> pendingStatusUsers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BasicUser> rejectedStatusUsers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "events_hashtags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;
}
