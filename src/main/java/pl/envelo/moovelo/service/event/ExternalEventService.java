package pl.envelo.moovelo.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.Constants;
import pl.envelo.moovelo.controller.searchutils.EventSearchSpecification;
import pl.envelo.moovelo.entity.actors.Visitor;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.exception.NoContentException;
import pl.envelo.moovelo.exception.VisitorAlreadyAssignedException;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.repository.event.ExternalEventRepository;
import pl.envelo.moovelo.service.AttachmentService;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.survey.EventSurveyService;

import java.util.UUID;


@Service
@Slf4j
public class ExternalEventService extends EventService<ExternalEvent> {
    private final ExternalEventRepository externalEventRepository;

    public ExternalEventService(EventRepositoryManager eventRepositoryManager,
                                EventInfoService eventInfoService,
                                EventOwnerService eventOwnerService,
                                HashTagService hashTagService,
                                BasicUserService basicUserService,
                                EventSearchSpecification eventSearchSpecification,
                                EventSurveyService eventSurveyService,
                                AttachmentService attachmentService,
                                ExternalEventRepository externalEventRepository) {
        super(eventRepositoryManager, eventInfoService, eventOwnerService, hashTagService, basicUserService,
                eventSearchSpecification, eventSurveyService, attachmentService);
        this.externalEventRepository = externalEventRepository;
    }

    public void addVisitorToExternalEvent(ExternalEvent externalEvent, Visitor visitor, EventType eventType) {
        if (externalEvent.getVisitors().contains(visitor)) {
            throw new VisitorAlreadyAssignedException("Visitor with the same email is already assigned to the event!");
        }

        externalEvent.getVisitors().add(visitor);

        eventRepositoryManager.getRepositoryForSpecificEvent(eventType).save(externalEvent);
    }

    public void removeVisitorFromExternalEvent(ExternalEvent externalEvent, Visitor visitor, EventType eventType) {
        externalEvent.getVisitors().remove(visitor);

        eventRepositoryManager.getRepositoryForSpecificEvent(eventType).save(externalEvent);
    }

    public boolean checkForAvailablePlaces(ExternalEvent externalEvent) {
        if (externalEvent.getLimitedPlaces() == 0) {
            return true;
        } else {
            return externalEvent.getLimitedPlaces() > (externalEvent.getVisitors().size() + externalEvent.getAcceptedStatusUsers().size());
        }
    }

    public String createInvitationLink(ExternalEvent event, EventType eventType) {
        log.info("ExternalEventsService - createInvitationLink(event = '{}')", event);
        String uuid = UUID.randomUUID().toString();
        event.setInvitationUuid(uuid);
        eventRepositoryManager.getRepositoryForSpecificEvent(eventType).save(event);
        String link = Constants.URL + "/api/v1/externalEvents/" + uuid;
        log.info("ExternalEventsService - createInvitationLink(event = '{}') - return '{}'", event, link);
        return link;
    }

    public String getInvitationLink(Long eventId, EventType eventType) {
        log.info("ExternalEventsService - getInvitationLink(eventId = '{}')", eventId);
        ExternalEvent event = getEventById(eventId, eventType);

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
