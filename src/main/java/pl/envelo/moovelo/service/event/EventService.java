package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.envelo.moovelo.controller.searchspecification.EventSearchSpecification;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EventService<I extends Event> {
    EventRepositoryManager eventRepositoryManager;
    private static final String EVENT_EXIST_MESSAGE = "Entity exists in Database";
    protected final EventInfoService eventInfoService;
    protected final EventOwnerService eventOwnerService;
    protected final HashTagService hashTagService;
    protected final BasicUserService basicUserService;
    protected EventSearchSpecification eventSearchSpecification;

    public I createNewEvent(I event, EventType eventType, Long userId) {
        log.info("EventService - createNewEvent()");
        if (checkIfEventExistsById(event.getId(), eventType)) {
            throw new EntityExistsException(EVENT_EXIST_MESSAGE);
        } else {
            List<Hashtag> hashtagsToAssign = hashTagService.getHashtagsToAssign(event.getHashtags());
            EventInfo validatedEventInfo = eventInfoService.validateEventInfoForCreateEvent(event.getEventInfo());

            I eventAfterFieldValidation = validateAggregatedEntitiesForCreateEvent(event, eventType, userId);
            eventAfterFieldValidation.setHashtags(hashtagsToAssign);
            eventAfterFieldValidation.setEventInfo(validatedEventInfo);

            log.info("EventService - createNewEvent() return {}", eventAfterFieldValidation);
            return (I) eventRepositoryManager
                    .getRepositoryForSpecificEvent(eventType)
                    .save(eventAfterFieldValidation);
        }
    }

    public void updateEventById(Long eventId, I eventFromDto, EventType eventType, Long userId) {
        log.info("EventService - updateEventById() - eventId = {}", eventId);
        I eventInDb = getEventById(eventId, eventType);
        Location formerLocation = eventInDb.getEventInfo().getLocation();
        Long eventInfoInDbId = eventInDb.getEventInfo().getId();
        List<Hashtag> hashtagsToAssign = hashTagService.validateHashtagsForUpdateEvent(eventFromDto.getHashtags(), eventInDb.getHashtags());
        EventInfo validatedEventInfo = eventInfoService.validateEventInfoForUpdateEvent(eventFromDto.getEventInfo(), eventInfoInDbId);
        setValidatedEntitiesForUpdateEvent(eventInDb, eventFromDto, userId);
        eventInDb.setHashtags(hashtagsToAssign);
        eventInDb.setEventInfo(validatedEventInfo);
        eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .save(eventInDb);
        eventInfoService.removeLocationWithNoEvents(formerLocation);
        log.info("EventService - updateEventById() - eventId = {} updated", eventId);
    }

    public I getEventById(Long id, EventType eventType) {
        log.info("EventService - getEventById()");
        Optional<I> eventOptional = eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .findById(id);

        if (eventOptional.isEmpty()) {
            throw new NoSuchElementException("No event with id: " + id);
        }
        log.info("EventService - getEventById() return {}", eventOptional.get());
        return eventOptional.get();
    }

    public void removeEventById(long id, EventType eventType) {
        log.info("EventService - removeEventById() - id = {}", id);
        Optional<I> eventOptional = eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .findById(id);
        if (eventOptional.isEmpty()) {
            throw new NoContentException("Event with id = " + id + " doesn't exist!");
        } else {
            I event = eventOptional.get();
            Location location = event.getEventInfo().getLocation();
            EventOwner eventOwner = event.getEventOwner();
            List<Hashtag> hashtags = event.getHashtags();
            eventRepositoryManager.getRepositoryForSpecificEvent(eventType).delete(event);
            eventInfoService.removeLocationWithNoEvents(location);
            eventOwnerService.removeEventOwnerWithNoEvents(eventOwner);
            hashtags.forEach(hashTagService::decrementHashtagOccurrence);
        }
        log.info("EventService - removeEventById() - event with id = {} removed", id);
    }

    public Page<I> getAllEvents(String privacy, String group, String cat, String sort, String sortOrder, int page, EventType eventType) {
        log.info("EventService - getAllEvents()");

        int sizeOfPage = 10;

        Pageable pageable = PageRequest.of(page, sizeOfPage, Sort.by(eventSearchSpecification.createSortOrder(sort, sortOrder)));
        Page<I> allEvents = eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .findAll(eventSearchSpecification.getEventsSpecification(privacy, group, cat), pageable);

        log.info("EventService - getAllEvents() return {}", allEvents.toString());

        return allEvents;
    }

    // TODO: 21.02.2023 Z List dziala, Page trzeba powalczyc
//    public Page<I> getAllEventsByEventOwnerBasicUserId(Long basicUserId, EventType eventType) {
//        log.info("EventService - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);
//        Page<I> events = eventRepositoryManager
//                .getRepositoryForSpecificEvent(eventType)
//                .findByEventOwner_UserId(basicUserId);
//
//        log.info("EventService - getAllEventsByEventOwnerBasicUserId() return {}", events);
//
//        return events;
//    }

    I validateAggregatedEntitiesForCreateEvent(I event, EventType eventType, Long userId) {
        I eventWithFieldsAfterValidation = getEventByEventType(eventType);
        setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        return eventWithFieldsAfterValidation;
    }

    private <I extends Event> I getEventByEventType(EventType eventType) {
        Event event = switch (eventType) {
            case EVENT -> new Event();
            case EXTERNAL_EVENT -> new ExternalEvent();
            case INTERNAL_EVENT -> new InternalEvent();
            case CYCLIC_EVENT -> new CyclicEvent();
        };
        return (I) event;
    }

    protected void setValidatedBasicEventFields(I event, Long userId, I eventWithFieldsAfterValidation) {
        eventWithFieldsAfterValidation.setEventOwner(eventOwnerService.getEventOwnerByUserId(userId));
        eventWithFieldsAfterValidation.setEventType(event.getEventType());
        eventWithFieldsAfterValidation.setLimitedPlaces(event.getLimitedPlaces());
        eventWithFieldsAfterValidation.setUsersWithAccess(basicUserService.getAllBasicUsers());
    }

    private I setValidatedEntitiesForUpdateEvent(I eventInDb, I event, Long userId) {
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
    public void updateEventOwnershipByEventId(Long eventId, EventOwner eventOwner, Long currentEventOwnerUserId, EventType eventType) {
        log.info("EventService - updateEventOwnershipById() - eventId = {}", eventId);
        I event = getEventById(eventId, eventType);
        eventOwnerService.createEventOwner(eventOwner);
        event.setEventOwner(eventOwner);
        eventOwnerService.removeEventFromEventOwnerEvents(event, currentEventOwnerUserId);
        eventOwnerService.removeEventOwnerWithNoEvents(getEventOwnerByUserId(currentEventOwnerUserId));
        log.info("EventService - updateEventOwnershipById() - eventId = {} updated", eventId);
    }

    public boolean checkIfEventExistsById(Long eventId, EventType eventType) {
        if (eventId != null) {
            return eventRepositoryManager
                    .getRepositoryForSpecificEvent(eventType)
                    .findById(eventId)
                    .isPresent();
        }
        return false;
    }



    public Page<BasicUser> getUsersWithAccess(Long eventId, int page, int size, EventType eventType) {
        log.info("EventService - getUsersWithAccess()");
        I event = getEventById(eventId, eventType);
        List<BasicUser> usersWithAccessList = event.getUsersWithAccess();

        Pageable pageable = PageRequest.of(page, size);
        Page<BasicUser> usersWithAccess = listToPage(pageable, usersWithAccessList);
        log.info("EventService - getUsersWithAccess() return {}", usersWithAccess);
        return usersWithAccess;
    }

    public static <T> Page<T> listToPage(final Pageable pageable, List<T> list) {
        int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), list.size());
        int last = Math.min(first + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(first, last), pageable, list.size());
    }
}


