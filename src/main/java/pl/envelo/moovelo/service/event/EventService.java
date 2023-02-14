package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {
    private static final String EVENT_EXIST_MESSAGE = "Entity exists in Database";
    private EventRepository<Event> eventRepository;
    private final EventInfoService eventInfoService;
    private final EventOwnerService eventOwnerService;
    private final HashTagService hashTagService;
    private final BasicUserService basicUserService;

    public List<? extends Event> getAllEvents() {
        log.info("EventService - getAllEvents()");
        List<? extends Event> allEvents = eventRepository.findAll();

        log.info("EventService - getAllEvents() return {}", allEvents.toString());
        return allEvents;
    }

    public Event createNewEvent(Event event, Long userId) {
        log.info("EventService - createNewEvent()");
        if (checkIfEntityExist(event)) {
            throw new EntityExistsException(EVENT_EXIST_MESSAGE);
        } else {
            List<Hashtag> hashtagsToAssign = hashTagService.getHashtagsToAssign(event.getHashtags());
            EventInfo validatedEventInfo = eventInfoService.validateEventInfoForCreateEvent(event.getEventInfo());

            Event eventAfterFieldValidation = validateAggregatedEntitiesForCreateEvent(event, userId);
            eventAfterFieldValidation.setHashtags(hashtagsToAssign);
            eventAfterFieldValidation.setEventInfo(validatedEventInfo);

            log.info("EventService - createNewEvent() return {}", eventAfterFieldValidation);
            return eventRepository.save(eventAfterFieldValidation);
        }
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

    private boolean checkIfEntityExist(Event event) {
        if (event.getId() == null) {
            return false;
        }
        return eventRepository.findById(event.getId()).isPresent();
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
            List<Hashtag> hashtags = event.getHashtags();
            eventRepository.delete(event);
            eventInfoService.removeLocationWithNoEvents(location);
            eventOwnerService.removeEventOwnerWithNoEvents(eventOwner);
            hashtags.forEach(hashTagService::decrementHashtagOccurrence);
        }
        log.info("EventService - removeEventById() - event with id = {} removed", id);
    }

    private Event validateAggregatedEntitiesForCreateEvent(Event event, Long userId) {
        Event eventWithFieldsAfterValidation = new Event();
        setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        return eventWithFieldsAfterValidation;
    }

    private void setValidatedBasicEventFields(Event event, Long userId, Event eventWithFieldsAfterValidation) {
        eventWithFieldsAfterValidation.setEventOwner(eventOwnerService.getEventOwnerByUserId(userId));
        eventWithFieldsAfterValidation.setEventType(event.getEventType());
        eventWithFieldsAfterValidation.setLimitedPlaces(event.getLimitedPlaces());
        eventWithFieldsAfterValidation.setUsersWithAccess(basicUserService.getAllBasicUsers());
    }

    public void updateEventById(Long eventId, Event eventFromDto, Long userId) {
        log.info("EventService - updateEventById() - eventId = {}", eventId);
        Event eventInDb = getEventById(eventId);
        Location formerLocation = eventInDb.getEventInfo().getLocation();
        Long eventInfoInDbId = eventInDb.getEventInfo().getId();
        List<Hashtag> hashtagsToAssign = hashTagService.validateHashtagsForUpdateEvent(eventFromDto.getHashtags(), eventInDb.getHashtags());
        EventInfo validatedEventInfo = eventInfoService.validateEventInfoForUpdateEvent(eventFromDto.getEventInfo(), eventInfoInDbId);
        setValidatedEntitiesForUpdateEvent(eventInDb, eventFromDto, userId);
        eventInDb.setHashtags(hashtagsToAssign);
        eventInDb.setEventInfo(validatedEventInfo);
        eventRepository.save(eventInDb);
        eventInfoService.removeLocationWithNoEvents(formerLocation);
        log.info("EventService - updateEventById() - eventId = {} updated", eventId);
    }

    private Event setValidatedEntitiesForUpdateEvent(Event eventInDb, Event event, Long userId) {
        setValidatedBasicEventFields(event, userId, eventInDb);
        return eventInDb;
    }

    public Long getEventOwnerUserIdByEventId(Long eventId) {
        log.info("EventService - getEventOwnerUserIdByEventId() - eventId = {}", eventId);
        EventOwner eventOwnerByEventId = eventOwnerService.getEventOwnerByEventId(eventId);
        Long userId = eventOwnerByEventId.getUserId();
        log.info("EventService - getEventOwnerUserIdByEventId() return{}", userId);
        return userId;
    }

    public EventOwner getEventOwnerByUserId(Long userId) {
        return eventOwnerService.getEventOwnerByUserId(userId);
    }

    @Transactional
    public void updateEventOwnershipByEventId(Long eventId, EventOwner eventOwner, Long currentEventOwnerUserId) {
        log.info("EventService - updateEventOwnershipById() - eventId = {}", eventId);
        Event event = getEventById(eventId);
        eventOwnerService.createEventOwner(eventOwner);
        event.setEventOwner(eventOwner);
        eventOwnerService.removeEventFromEventOwnerEvents(event, currentEventOwnerUserId);
        eventOwnerService.removeEventOwnerWithNoEvents(getEventOwnerByUserId(currentEventOwnerUserId));
        log.info("EventService - updateEventOwnershipById() - eventId = {} updated", eventId);
    }

    public boolean checkIfEventExistsById(Long eventId) {
        return eventRepository.findById(eventId).isPresent();
    }
}


