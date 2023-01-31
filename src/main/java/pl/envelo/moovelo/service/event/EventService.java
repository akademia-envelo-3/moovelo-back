package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;

    private LocationService locationService;

    private EventOwnerService eventOwnerService;

    @Autowired
    public EventService(EventRepository eventRepository, LocationService locationService, EventOwnerService eventOwnerService) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
        this.eventOwnerService = eventOwnerService;
    }

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    public List<? extends Event> getAllEventsByEventOwnerBasicUserId(Long basicUserId) {
        log.info("EventService - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);
        List<? extends Event> events = eventRepository.findByEventOwner_UserId(basicUserId);

        log.info("EventService - getAllEventsByEventOwnerBasicUserId() return {}", events);
        return events;
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

    public void removeEventById(long id) {
        log.info("EventService - removeEventById() - id = {}", id);
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new NoContentException("Event with id = " + id + " doesn't exist!");
        } else {
            Event event = eventOptional.get();
            Location location = event.getEventInfo().getLocation();
            EventOwner eventOwner = event.getEventOwner();
            eventRepository.delete(event);
            locationService.checkIfLocationContainsEvents(location);
            eventOwnerService.checkIfEventOwnerContainsEvents(eventOwner);
        }
        log.info("EventService - removeEventById() - event with id = {} removed", id);
    }
}
