package pl.envelo.moovelo.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchutils.EventSearchSpecification;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.survey.EventSurveyService;

@Service
@Slf4j
public class InternalEventService<C extends InternalEvent> extends EventService<C> {

    public InternalEventService(EventRepositoryManager eventRepositoryManager, EventInfoService eventInfoService,
                                EventOwnerService eventOwnerService,
                                HashTagService hashTagService, BasicUserService basicUserService,
                                EventSearchSpecification eventSearchSpecification, EventSurveyService eventSurveyService) {
        super(eventRepositoryManager, eventInfoService, eventOwnerService, hashTagService,
                basicUserService, eventSearchSpecification, eventSurveyService);
    }

    @Override
    protected void setValidatedBasicEventFields(C event, Long userId, C eventWithFieldsAfterValidation) {
        super.setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        eventWithFieldsAfterValidation.setPrivate(event.isPrivate());
    }

    //    public C createNewEvent(C event, Long userId) {
//        log.info("EventService - createNewEvent()");
//        if (checkIfEntityExist(event)) {
//            throw new EntityExistsException(EVENT_EXIST_MESSAGE);
//        } else {
//            List<Hashtag> hashtagsToAssign = hashTagService.getHashtagsToAssign(event.getHashtags());
//            EventInfo validatedEventInfo = eventInfoService.validateEventInfoForCreateEvent(event.getEventInfo());
//
//            InternalEvent eventAfterFieldValidation = validateAggregatedEntitiesForCreateEvent(event, userId);
//            eventAfterFieldValidation.setHashtags(hashtagsToAssign);
//            eventAfterFieldValidation.setEventInfo(validatedEventInfo);
//            eventAfterFieldValidation.setPrivate(event.isPrivate());
//
//            log.info("EventService - createNewEvent() return {}", eventAfterFieldValidation);
//
//            return (C) repositoryManager.getRepositoryForSpecificEvent(EventType.INTERNAL_EVENT).save(eventAfterFieldValidation);
//        }
//    }

//    public List<? extends Event> getAllInternalEvents() {
//        log.info("InternalEventService - getAllInternalEvents()");
//        List<? extends Event> allInternalEvents = internalEventRepository.findAll();
//
//        log.info("InternalEventService - getAllInternalEvents() return {}", allInternalEvents.toString());
//        return allInternalEvents;
//    }
}
