package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.repository.event.InternalEventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }
}
