package pl.envelo.moovelo.entity.groups;

import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.InternalEvent;

import java.time.LocalDateTime;
import java.util.List;

public class Group {
    Long id;
    GroupOwner groupOwner;
    List<BasicUser> members;
    GroupInfo groupInfo;
    List<InternalEvent> events;
    LocalDateTime creationDate;
}
