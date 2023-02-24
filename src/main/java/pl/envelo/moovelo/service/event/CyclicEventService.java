package pl.envelo.moovelo.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchutils.EventSearchSpecification;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.group.GroupService;
import pl.envelo.moovelo.service.survey.EventSurveyService;

@Service
@Slf4j
public class CyclicEventService extends InternalEventService<CyclicEvent> {
    public CyclicEventService(EventRepositoryManager eventRepositoryManager,
                              EventInfoService eventInfoService,
                              EventOwnerService eventOwnerService,
                              HashTagService hashTagService,
                              BasicUserService basicUserService,
                              EventSearchSpecification eventSearchSpecification,
                              GroupService groupService,
                              EventSurveyService eventSurveyService) {
        super(eventRepositoryManager, eventInfoService, eventOwnerService, hashTagService,
                basicUserService, eventSearchSpecification, groupService, eventSurveyService);
    }

    @Override
    protected void setValidatedBasicEventFields(CyclicEvent event, Long userId, CyclicEvent eventWithFieldsAfterValidation) {
        super.setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        eventWithFieldsAfterValidation.setNumberOfRepeats(event.getNumberOfRepeats());
        eventWithFieldsAfterValidation.setFrequencyInDays(event.getFrequencyInDays());
    }
}
