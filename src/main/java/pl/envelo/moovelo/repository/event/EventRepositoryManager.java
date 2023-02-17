package pl.envelo.moovelo.repository.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class EventRepositoryManager {

    EventRepository<Event> eventRepository;
    InternalEventRepository<InternalEvent> internalEventRepository;
    CyclicEventRepository cyclicEventRepository;
    ExternalEventRepository externalEventRepository;

    public EventRepository getRepositoryForSpecificEvent(EventType eventType) {
        switch (eventType) {
            case EVENT -> {
                return eventRepository;
            }
            case INTERNAL_EVENT -> {
                return internalEventRepository;
            }
            case CYCLIC_EVENT -> {
                return cyclicEventRepository;
            }
            case EXTERNAL_EVENT -> {
                return externalEventRepository;
            }
        }
        throw new NoSuchElementException();
    }
}
