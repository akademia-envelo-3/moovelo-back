package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.repository.event.InternalEventRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class InternalEventService {

    private InternalEventRepository internalEventRepository;

    @Autowired
    public InternalEventService(InternalEventRepository internalEventRepository) {
        this.internalEventRepository = internalEventRepository;
    }

    public List<? extends Event> getAllInternalEvents() {
        log.info("InternalEventService - getAllInternalEvents()");
        List<? extends Event> allInternalEvents = internalEventRepository.findAll();

        log.info("InternalEventService - getAllInternalEvents() return {}", allInternalEvents.toString());
        return allInternalEvents;
    }

    public InternalEvent getInternalEventById(Long id) {
        log.info("InternalEventService - getInternalEventById()");
        Optional<? extends InternalEvent> internalEventOptional = internalEventRepository.findById(id);
        if (internalEventOptional.isEmpty()) {
            throw new NoSuchElementException("No internal event with id: " + id);
        }
        log.info("EventService - getEventById() return {}", internalEventOptional.get());
        return internalEventOptional.get();
    }
}
