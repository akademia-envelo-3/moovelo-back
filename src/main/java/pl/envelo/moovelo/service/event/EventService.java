package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import java.util.List;

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

    public void removeEventById(long id) {
        eventRepository.deleteById(id);
    }
}
