package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {
    private final EventOwnerRepository eventOwnerRepository;
    private final static String EVENT_EXIST_MESSAGE = "Entity exists in Database";
    private EventRepository<Event> eventRepository;
    private final EventInfoService eventInfoService;
    private final EventOwnerService eventOwnerService;
    private final HashTagService hashTagService;

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    @Transactional
    public Event createNewEvent(Event event, Long userId) {
        log.info("EventService - createNewEvent()");
        if (checkIfEntityExist(event)) {
            throw new EntityExistsException(EVENT_EXIST_MESSAGE);
        } else {
            Event eventAfterFieldValidation = validateAggregatedEntities(event, userId);
            return eventRepository.save(eventAfterFieldValidation);
        }
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

    private boolean checkIfEntityExist(Event event) {
        if (event.getId() == null) {
            return false;
        }
        return eventRepository.findById(event.getId()).isPresent();
    }

    private Event validateAggregatedEntities(Event event, Long userId) {
        Event eventWithFieldsAfterValidation = new Event();
        eventWithFieldsAfterValidation.setEventOwner(eventOwnerService.assignEventOwnerToCurrentEvent(userId));
        eventWithFieldsAfterValidation
                .setEventInfo(eventInfoService.getEventInfoWithLocationCoordinates(event.getEventInfo()));
        eventWithFieldsAfterValidation.setLimitedPlaces(event.getLimitedPlaces());
        eventWithFieldsAfterValidation.setHashtags(hashTagService.validateHashtags(event.getHashtags()));
        return eventWithFieldsAfterValidation;
    }
}


