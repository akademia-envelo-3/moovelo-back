package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private EventRepository eventRepository;

    private BasicUserService basicUserService;

    private InternalEventService internalEventService;

    @Autowired
    public EventService(EventRepository eventRepository, BasicUserService basicUserService, InternalEventService internalEventService) {
        this.eventRepository = eventRepository;
        this.basicUserService = basicUserService;
        this.internalEventService = internalEventService;
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

    public List<BasicUser> getUsersWithAccess(Long eventId) {
        log.info("EventService - getUsersWithAccess()");
        Event event = getEventById(eventId);

        if (event.getEventType().equals(EventType.INTERNAL_EVENT)
                || event.getEventType().equals(EventType.CYCLIC_EVENT)) {

            InternalEvent internalEvent = internalEventService.getInternalEventById(eventId);

            if (internalEvent.getGroup() != null) {
                return internalEvent.getGroup().getMembers();
            } else {
                return event.getUsersWithAccess();
            }

        } else {

            return basicUserService.getAllBasicUsers();
        }
    }
}
