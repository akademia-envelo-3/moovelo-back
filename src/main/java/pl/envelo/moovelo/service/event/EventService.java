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

    public List<? extends Event> getAllEventsByEventOwnerBasicUserId(Long basicUserId) {
        log.info("EventService - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);
        List<? extends Event> events = eventRepository.findByEventOwner_UserId(basicUserId);

        log.info("EventService - getAllEventsByEventOwnerBasicUserId() return {}", events);
        return events;
    }
}
