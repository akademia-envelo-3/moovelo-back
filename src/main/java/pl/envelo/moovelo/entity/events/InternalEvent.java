package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class InternalEvent extends Event {

    @ManyToOne
    private Group group;

    private boolean isPrivate;
}
