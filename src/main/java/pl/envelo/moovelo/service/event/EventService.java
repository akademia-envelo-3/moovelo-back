package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.event.EventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    public Event getEventById(Long id) {
        log.info("EventService - getEventById()");
        Optional<? extends Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new NoSuchElementException("No event with id: " + id);
        }
        log.info("EventService - getEventById() return {}", eventOptional.get());
        return eventOptional.get();
    }

    public List<? extends Event> getAllEventsByEventNameContains(String name) {
        log.info("EventService - getAllEventsByEventNameContains(String name)");
        List<? extends Event> eventsByNameContains = eventRepository.findByEventInfoNameContainsIgnoreCase(name);

        log.info("EventService - getAllEventsByEventNameContains(String name)", eventsByNameContains.toString());
        return eventsByNameContains;
    }
}
