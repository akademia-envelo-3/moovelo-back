package pl.envelo.moovelo.service.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchspecification.EventSearchSpecification;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.Role;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.*;
import pl.envelo.moovelo.entity.surveys.EventSurvey;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.exception.StatusNotExistsException;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.model.EventsForUserCriteria;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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

    public Page<I> getAllEvents(String privacy, String group, String cat, Long groupId, String sort, String sortOrder, int page, EventType eventType) {
        log.info("EventService - getAllEvents()");

        int sizeOfPage = 10;

        Pageable pageable = PageRequest.of(page, sizeOfPage, Sort.by(eventSearchSpecification.createSortOrder(sort, sortOrder)));
        Page<I> allEvents = eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .findAll(eventSearchSpecification.getEventsSpecification(privacy, group, cat, groupId), pageable);

        log.info("EventService - getAllEvents() return {}", allEvents.toString());

        return allEvents;
    }

    public Page<? extends Event> getEventsForUser(
            Long userId,
            EventsForUserCriteria filterCriteria,
            SortingAndPagingCriteria sortingAndPagingCriteria,
            EventType eventType
    ) {
        log.info("EventService - getEventsForUser(userId = '{}', filterCriteria = '{}', sortingAndPagingCriteria = '{}')",
                userId, filterCriteria, sortingAndPagingCriteria);

        Pageable pageable = PageRequest.of(
                sortingAndPagingCriteria.getPageNumber(),
                sortingAndPagingCriteria.getPageSize(),
                Sort.by(eventSearchSpecification.createSortOrderForUserSpecification(
                                sortingAndPagingCriteria.getSortBy(),
                                sortingAndPagingCriteria.getSortDirection()
                        )
                )
        );

        Page<? extends Event> allEvents = eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .findAll(
                        eventSearchSpecification.getEventsAvailableForUserSpecification(
                                userId,
                                filterCriteria
                        ),
                        pageable
                );

        log.info("EventService - getEventsForUser(userId = '{}', filterCriteria = '{}', sortingAndPagingCriteria = '{}') return '{}'",
                userId, filterCriteria, sortingAndPagingCriteria, allEvents);

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

    public void updateEventOwnershipByEventId(Long eventId, Long newOwnerUserId, EventType eventType) {
        EventOwner newEventOwner = getEventOwnerByUserId(newOwnerUserId);
        Long currentEventOwnerUserId = getEventOwnerUserIdByEventId(eventId);
        log.info("EventService - updateEventOwnershipById() - eventId = {}", eventId);
        I event = getEventById(eventId, eventType);
        eventOwnerService.createEventOwner(newEventOwner);
        event.setEventOwner(newEventOwner);
        // TODO: 22.02.2023 repository manager
        eventOwnerService.removeEventOwnerWithNoEvents(getEventOwnerByUserId(currentEventOwnerUserId));
        eventRepositoryManager
                .getRepositoryForSpecificEvent(eventType)
                .save(event);
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

    @Transactional
    public void setStatus(Long eventId, Long userId, String status, EventType eventType) {
        log.info("EventService - setStatus()");

        I event = getEventById(eventId, eventType);
        BasicUser user = basicUserService.getBasicUserById(userId);

        checkIfUserHasAccessToEvent(event, user);

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

    public List<EventSurvey> getEventSurveysByEventId(Long eventId, User user, EventType eventType) {
        log.info("EventService - getEventSurveysByEventId()");
        I event = getEventById(eventId, eventType);

        if (user.getRole().equals(Role.ROLE_USER)) {
            checkIfUserHasAccessToEvent(event, (BasicUser) user);
        }

        List<EventSurvey> surveys = event.getEventSurveys();

        log.info("EventService - getEventSurveysByEventId() return {}", surveys);
        return surveys;
    }

    private void checkIfUserHasAccessToEvent(Event event, BasicUser user) {
        if (!event.getUsersWithAccess().contains(user)) {
            throw new UnauthorizedRequestException("User with id " + user.getId() + " does not have an access to event with id " + event.getId());
        }
    }
}