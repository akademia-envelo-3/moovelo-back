package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchspecification.EventSearchSpecification;
import org.springframework.transaction.annotation.Transactional;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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

    public Page<BasicUser> getUsersWithAccess(Long eventId, int page, int size) {
        log.info("EventService - getUsersWithAccess()");
        Event event = getEventById(eventId);
        List<BasicUser> usersWithAccessList = event.getUsersWithAccess();

        Pageable pageable = PageRequest.of(page, size);
        Page<BasicUser> usersWithAccess = listToPage(pageable, usersWithAccessList);
        log.info("EventService - getUsersWithAccess() return {}", usersWithAccess);
        return usersWithAccess;
    }

    public static <T> Page<T> listToPage(final Pageable pageable, List<T> list) {
        int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), list.size());;
        int last = Math.min(first + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(first, last), pageable, list.size());
    }

    @Transactional
    public void setStatus(Long eventId, Long userId, String status) {
        log.info("EventService - setStatus()");

        List<String> statuses = List.of("accepted", "pending", "rejected");
        if (!statuses.contains(status.toLowerCase())) {
            return;
        }

        Event event = getEventById(eventId);
        BasicUser user = basicUserService.getBasicUserById(userId);

        Set<BasicUser> setOfAccepted = event.getAcceptedStatusUsers();
        Set<BasicUser> setOfPending = event.getPendingStatusUsers();
        Set<BasicUser> setOfRejected = event.getRejectedStatusUsers();

        switch (status.toLowerCase()) {
            case "accepted" -> {
                setAcceptedStatus(user, setOfAccepted, setOfPending, setOfRejected);
            }
            case "pending" -> {
                setPendingStatus(user, setOfAccepted, setOfPending, setOfRejected);
            }
            case "rejected" -> {
                setRejectedStatus(user, setOfAccepted, setOfPending, setOfRejected);
            }
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
            if (setOfPending.contains(user)) {
                setOfPending.remove(user);
            }
            if (setOfRejected.contains(user)) {
                setOfRejected.remove(user);
            }
        }
    }

    private void setPendingStatus(BasicUser user,
                                   Set<BasicUser> setOfAccepted,
                                   Set<BasicUser> setOfPending,
                                   Set<BasicUser> setOfRejected) {

        if (!setOfPending.contains(user)) {
            setOfPending.add(user);
            if (setOfAccepted.contains(user)) {
                setOfAccepted.remove(user);
            }
            if (setOfRejected.contains(user)) {
                setOfRejected.remove(user);
            }
        }
    }

    private void setRejectedStatus(BasicUser user,
                                   Set<BasicUser> setOfAccepted,
                                   Set<BasicUser> setOfPending,
                                   Set<BasicUser> setOfRejected) {

        if (!setOfRejected.contains(user)) {
            setOfRejected.add(user);
            if (setOfPending.contains(user)) {
                setOfPending.remove(user);
            }
            if (setOfAccepted.contains(user)) {
                setOfAccepted.remove(user);
            }
        }
    }
}


