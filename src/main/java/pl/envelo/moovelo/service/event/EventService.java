package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.envelo.moovelo.controller.searchUtils.EventSearchSpecification;
import pl.envelo.moovelo.controller.searchUtils.PagingUtils;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.exception.StatusNotExistsException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.repository.event.EventRepository;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.LocationService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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
    private LocationService locationService;
    private EventSearchSpecification eventSearchSpecification;

    public Page<? extends Event> getAllEvents(String privacy, String group, String cat, String sort, String sortOrder, int page) {
        log.info("EventService - getAllEvents()");

        int sizeOfPage = 10;

        Pageable pageable = PageRequest.of(page, sizeOfPage, Sort.by(eventSearchSpecification.createSortOrder(sort, sortOrder)));
        Page<? extends Event> allEvents = eventRepository.findAll(eventSearchSpecification.getEventsSpecification(privacy, group, cat), pageable);

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

    public void updateEventOwnershipByEventId(Long eventId, Long newOwnerUserId) {
        EventOwner newEventOwner = getEventOwnerByUserId(newOwnerUserId);
        Long currentEventOwnerUserId = getEventOwnerUserIdByEventId(eventId);
        log.info("EventService - updateEventOwnershipById() - eventId = {}", eventId);
        Event event = getEventById(eventId);
        eventOwnerService.createEventOwner(newEventOwner);
        event.setEventOwner(newEventOwner);
        eventOwnerService.removeEventOwnerWithNoEvents(getEventOwnerByUserId(currentEventOwnerUserId));
        eventRepository.save(event);
        log.info("EventService - updateEventOwnershipById() - eventId = {} updated", eventId);
    }

    public boolean checkIfEventExistsById(Long eventId) {
        return eventRepository.findById(eventId).isPresent();
    }

    public Page<BasicUser> getUsersWithAccess(Long eventId, int page, int size) {
        log.info("EventService - getUsersWithAccess()");
        Event event = getEventById(eventId);
        List<BasicUser> usersWithAccessList = event.getUsersWithAccess();

        Pageable pageable = PageRequest.of(page, size);
        Page<BasicUser> usersWithAccess = PagingUtils.listToPage(pageable, usersWithAccessList);
        log.info("EventService - getUsersWithAccess() return {}", usersWithAccess);
        return usersWithAccess;
    }



    @Transactional
    public void setStatus(Long eventId, Long userId, String status) {
        log.info("EventService - setStatus()");

        Event event = getEventById(eventId);
        BasicUser user = basicUserService.getBasicUserById(userId);

        if (!event.getUsersWithAccess().contains(user)) {
            throw new UnauthorizedRequestException("User with id " + userId + " does not have an access to event with id " + eventId);
        }

        Set<BasicUser> setOfAccepted = event.getAcceptedStatusUsers();
        Set<BasicUser> setOfPending = event.getPendingStatusUsers();
        Set<BasicUser> setOfRejected = event.getRejectedStatusUsers();

        switch (status.toLowerCase()) {
            case "accepted" -> setAcceptedStatus(user, setOfAccepted, setOfPending, setOfRejected);
            case "pending" -> setPendingStatus(user, setOfAccepted, setOfPending, setOfRejected);
            case "rejected" -> setRejectedStatus(user, setOfAccepted, setOfPending, setOfRejected);
            default -> throw new StatusNotExistsException("Status " + status + " does not exist");
        }

        event.setAcceptedStatusUsers(setOfAccepted);
        event.setPendingStatusUsers(setOfPending);
        event.setRejectedStatusUsers(setOfRejected);
    }

    private void setAcceptedStatus(BasicUser user,
                                   Set<BasicUser> setOfAccepted,
                                   Set<BasicUser> setOfPending,
                                   Set<BasicUser> setOfRejected) {

        if (!setOfAccepted.contains(user)) {
            setOfAccepted.add(user);
            setOfPending.remove(user);
            setOfRejected.remove(user);
        }
    }

    private void setPendingStatus(BasicUser user,
                                  Set<BasicUser> setOfAccepted,
                                  Set<BasicUser> setOfPending,
                                  Set<BasicUser> setOfRejected) {

        if (!setOfPending.contains(user)) {
            setOfPending.add(user);
            setOfAccepted.remove(user);
            setOfRejected.remove(user);
        }
    }

    private void setRejectedStatus(BasicUser user,
                                   Set<BasicUser> setOfAccepted,
                                   Set<BasicUser> setOfPending,
                                   Set<BasicUser> setOfRejected) {

        if (!setOfRejected.contains(user)) {
            setOfRejected.add(user);
            setOfPending.remove(user);
            setOfAccepted.remove(user);
        }
    }
}