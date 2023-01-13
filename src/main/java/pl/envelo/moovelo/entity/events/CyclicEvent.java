package pl.envelo.moovelo.entity.events;

import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.actors.BasicUser;

import java.util.List;

public class CyclicEvent extends InternalEvent {
    Integer frequencyInDays;
    Integer numberOfRepeats;

    public CyclicEvent(Group group) {
        super(group);
    }

    public CyclicEvent(List<BasicUser> users) {
        super(users);
    }
}
