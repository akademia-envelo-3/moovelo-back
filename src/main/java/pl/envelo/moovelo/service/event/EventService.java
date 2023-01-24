package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.EventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<?> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<?> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents);
        return allEvents;
    }
}
