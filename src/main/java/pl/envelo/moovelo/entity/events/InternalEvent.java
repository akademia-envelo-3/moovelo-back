package pl.envelo.moovelo.entity.events;

import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.entity.actors.BasicUser;

import java.util.List;

public class InternalEvent extends Event {
    Group group;
    List<BasicUser> invited;
    boolean isPrivate;

    public InternalEvent(Group group) {

    }

    public InternalEvent(List<BasicUser> users) {

    }
}
