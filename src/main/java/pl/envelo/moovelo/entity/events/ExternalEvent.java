package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.actors.Visitor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExternalEvent extends Event {

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(
            name = "external_events_visitors",
            joinColumns = @JoinColumn(name = "external_event_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    private List<Visitor> visitors;

    @Column(unique = true)
    private String invitationUuid;
}
