package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.exception.IllegalEventException;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.exception.VisitorAlreadyAssignedException;
import pl.envelo.moovelo.repository.event.ExternalEventRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExternalEventService {

    private ExternalEventRepository externalEventRepository;

    @Autowired
    public ExternalEventService(ExternalEventRepository externalEventRepository) {
        this.externalEventRepository = externalEventRepository;
    }

    public List<ExternalEvent> getAllExternalEvents() {
        log.info("ExternalEventService - getAllExternalEvents()");
        List<ExternalEvent> allExternalEvents = externalEventRepository.findAll();

        log.info("ExternalEventService - getAllExternalEvents() return {}", allExternalEvents.toString());
        return allExternalEvents;
    }

    public ExternalEvent getExternalEventById(Long externalEventId) {
        Optional<ExternalEvent> externalEventById = externalEventRepository.findExternalEventById(externalEventId);

        if (externalEventById.isEmpty()) {
            throw new IllegalEventException("External event with id = " + externalEventId + " does not exits");
        } else {
            return externalEventById.get();
        }
    }

    public void addVisitorToExternalEvent(ExternalEvent externalEvent, Visitor visitor) {
        if (externalEvent.getVisitors().contains(visitor)) {
            throw new VisitorAlreadyAssignedException("Visitor with the same email is already assigned to the event!");
        }

        externalEvent.getVisitors().add(visitor);

        externalEventRepository.save(externalEvent);
    }

    public void removeVisitorFromExternalEvent(ExternalEvent externalEvent, Visitor visitor) {
        externalEvent.getVisitors().remove(visitor);

        externalEventRepository.save(externalEvent);
    }

    public boolean checkForAvailablePlaces(ExternalEvent externalEvent) {
        if (externalEvent.getLimitedPlaces() == 0) {
            return true;
        } else {
            return externalEvent.getLimitedPlaces() > (externalEvent.getVisitors().size() + externalEvent.getAcceptedStatusUsers().size());
        }
    }

    public String createInvitationLink(ExternalEvent event) {
        log.info("ExternalEventsService - createInvitationLink(event = '{}')", event);
        String uuid = UUID.randomUUID().toString();
        event.setInvitationUuid(uuid);
        externalEventRepository.save(event);
        String link = Constants.URL + "/api/v1/externalEvents/" + uuid;
        log.info("ExternalEventsService - createInvitationLink(event = '{}') - return '{}'", event, link);
        return link;
    }

    public String getInvitationLink(Long eventId) {
        log.info("ExternalEventsService - getInvitationLink(eventId = '{}')", eventId);
        ExternalEvent event = getExternalEventById(eventId);

        if (event.getInvitationUuid() == null) {
            throw new NoContentException("Event doesn't have invitation link!");
        }

        String link = Constants.URL + "/api/v1/externalEvents/" + event.getInvitationUuid();
        log.info("ExternalEventsService - getInvitationLink(eventId = '{}') - return '{}'", eventId, link);
        return link;
    }

    public Long getExternalEventIdByUuid(String uuid) {
        log.info("ExternalEventService - getExternalEventIdByUuid(uuid = '{}')", uuid);
        ExternalEvent event = externalEventRepository.getExternalEventByInvitationUuid(uuid)
                .orElseThrow(() -> new NoContentException("No event with invitation uuid = " + uuid));
        Long eventId = event.getId();
        log.info("ExternalEventService - getExternalEventIdByUuid(uuid = '{}') - return eventId = '{}'", uuid, eventId);
        return eventId;
    }
}
