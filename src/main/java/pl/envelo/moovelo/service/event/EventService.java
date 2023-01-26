package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.event.EventRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {


    private EventRepository<Event> eventRepository;

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    public void createNewEvent(Event event) {
        eventRepository.save(event);
    }
}
