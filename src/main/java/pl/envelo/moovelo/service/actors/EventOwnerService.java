package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class EventOwnerService {

    private final EventOwnerRepository eventOwnerRepository;

    public EventOwner getEventOwnerByUserId(Long userId) {
        EventOwner eventOwnerBasedOnBasicUser;

        if (eventOwnerRepository.findEventOwnerByUserId(userId).isPresent()) {
            eventOwnerBasedOnBasicUser = assignExistingEventOwnerBasedOnUser(userId);
        } else {
            eventOwnerBasedOnBasicUser = createEventOwnerBasedOnExistingUser(userId);
        }
        return eventOwnerBasedOnBasicUser;
    }

    private EventOwner createEventOwnerBasedOnExistingUser(Long userId) {
        EventOwner eventOwner = new EventOwner();
        eventOwner.setUserId(userId);
        return eventOwner;
    }

    private EventOwner assignExistingEventOwnerBasedOnUser(Long userId) {
        return eventOwnerRepository.findEventOwnerByUserId(userId).get();
    }

    /**
     * Method check if EventOwner is assigned to any Event. If not,
     * remove the EventOwner entity remove from database.
     */
    public void removeEventOwnerWithNoEvents(EventOwner eventOwner) {
        log.info("EventOwnerService - removeEventOwnerWithNoEvents() - eventOwner = {}", eventOwner);
        if (eventOwner.getEvents().isEmpty()) {
            eventOwnerRepository.delete(eventOwner);
            log.info("EventOwnerService - removeEventOwnerWithNoEvents() - eventOwner removed");
        }
    }

    public EventOwner getEventOwnerByEventId(Long eventId) {
        log.info("EventOwnerService - getEventOwnerByEventId()");
        Optional<EventOwner> eventOptional = eventOwnerRepository.findByEventsId(eventId);
        if (eventOptional.isEmpty()) {
            throw new NoSuchElementException("No event owner for the event with : " + eventId + " found");
        } else {
            EventOwner eventOwner = eventOptional.get();
            log.info("EventOwnerService - getEventOwnerByEventId() return{}", eventOwner);
            return eventOwner;
        }
    }

    public EventOwner createEventOwner(EventOwner eventOwner) {
        return eventOwnerRepository.save(eventOwner);
    }

    public void removeEventFromEventOwnerEvents(Event event, Long eventOwnerUserId) {
        Optional<EventOwner> eventOwnerOptional = eventOwnerRepository.findEventOwnerByUserId(eventOwnerUserId);
        if (eventOwnerOptional.isPresent()) {
            EventOwner eventOwner = eventOwnerOptional.get();
            List<Event> events = eventOwner.getEvents();
            events.remove(event);
        } else {
            throw new NoSuchElementException("No event owner with user id: " + eventOwnerUserId);
        }
    }
}
