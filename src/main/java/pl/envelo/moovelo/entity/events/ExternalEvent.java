package pl.envelo.moovelo.entity.events;

import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

public class ExternalEvent extends Event {
    List<Visitor> visitors;
}
