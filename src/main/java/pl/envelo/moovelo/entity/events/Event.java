package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
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

    @ManyToOne
    private EventOwner eventOwner;

    @OneToOne(cascade = CascadeType.ALL)
    private EventInfo eventInfo;

    private int limitedPlaces;

    private boolean isPrivate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventSurvey> eventSurveys;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_X_basic_users_access",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<BasicUser> usersWithAccess;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_X_basic_users_accepted",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<BasicUser> acceptedStatusUsers;

    @Formula("select count(*) from events_X_basic_users_accepted x where x.event_id = id")
    private int numOfAcceptedStatusUsers;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_X_basic_users_pending",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<BasicUser> pendingStatusUsers;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_X_basic_users_rejected",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<BasicUser> rejectedStatusUsers;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "events_hashtags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

}
