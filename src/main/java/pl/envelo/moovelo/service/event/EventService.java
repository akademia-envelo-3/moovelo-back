package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.actors.UserRepository;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import javax.persistence.EntityExistsException;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {
    private final UserRepository userRepository;

    private final static String EVENT_EXIST_MESSAGE = "Entity exists in Database";

    private EventRepository<Event> eventRepository;
    private EventInfoService eventInfoService;
    private EventOwnerService eventOwnerService;

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    public void createNewEvent(Event event) {
        if (checkIfEntityExist(event)) {
            throw new EntityExistsException(EVENT_EXIST_MESSAGE);
        } else {
            Event eventAfterFieldValidation = checkIfAggregatedEntitiesExistInDatabase(event);
            eventRepository.save(eventAfterFieldValidation);
        }
    }

    private boolean checkIfEntityExist(Event event) {
        if (event.getId() == null) {
            return false;
        }
        return eventRepository.findById(event.getId()).isPresent();
    }

    private Event checkIfAggregatedEntitiesExistInDatabase(Event event) {
        Event eventWithFieldsAfterValidation = new Event();
        eventWithFieldsAfterValidation.setEventInfo(event.getEventInfo());
//        TODO Ustalić walidacje dla Event Ownera
//        eventWithFieldsAfterValidation.setEventOwner(event.getEventOwner());
        eventWithFieldsAfterValidation.setLimitedPlaces(event.getLimitedPlaces());
        eventWithFieldsAfterValidation.setComments(event.getComments());
        eventWithFieldsAfterValidation.setAcceptedStatusUsers(event.getAcceptedStatusUsers());
        eventWithFieldsAfterValidation.setRejectedStatusUsers(event.getRejectedStatusUsers());
        eventWithFieldsAfterValidation.setPendingStatusUsers(event.getPendingStatusUsers());
        eventWithFieldsAfterValidation.setHashtags(event.getHashtags());
        return eventWithFieldsAfterValidation;
    }
}


