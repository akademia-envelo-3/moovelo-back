package pl.envelo.moovelo.entity.actors;

import pl.envelo.moovelo.entity.events.ExternalEvent;

import java.util.List;

public class Visitor extends Person {
    Long id;
    List<ExternalEvent> events;
}
