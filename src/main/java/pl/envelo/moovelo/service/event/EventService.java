package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchspecification.EventSearchSpecification;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.event.EventRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;
    private EventSearchSpecification eventSearchSpecification;

    @Autowired
    public EventService(EventRepository eventRepository, EventSearchSpecification eventSearchSpecification) {
        this.eventRepository = eventRepository;
        this.eventSearchSpecification = eventSearchSpecification;
    }

    public Page<? extends Event> getAllEvents(String privacy, String group, String cat, String sort, String sortOrder, int page) {
        log.info("EventService - getAllEvents()");

        int size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(eventSearchSpecification.createSortOrder(sort, sortOrder.toUpperCase())));

        Page<? extends Event> allEvents = eventRepository.findAll(eventSearchSpecification.getEventsSpecification(privacy, group, cat), pageable);

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

}