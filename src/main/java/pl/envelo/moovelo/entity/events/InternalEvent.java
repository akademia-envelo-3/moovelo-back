package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.groups.Group;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class InternalEvent extends Event {

    @ManyToOne(cascade = CascadeType.DETACH)
    private Group group;

    private boolean isPrivate;
}
