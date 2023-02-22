package pl.envelo.moovelo.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchspecification.EventSearchSpecification;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;

@Service
@Slf4j
public class CyclicEventService extends InternalEventService<CyclicEvent> {
    public CyclicEventService(EventRepositoryManager eventRepositoryManager, EventInfoService eventInfoService,
                              EventOwnerService eventOwnerService, HashTagService hashTagService,
                              BasicUserService basicUserService, EventSearchSpecification eventSearchSpecification) {
        super(eventRepositoryManager, eventInfoService, eventOwnerService, hashTagService, basicUserService,
                eventSearchSpecification);
    }

    @Override
    protected void setValidatedBasicEventFields(CyclicEvent event, Long userId, CyclicEvent eventWithFieldsAfterValidation) {
        super.setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        eventWithFieldsAfterValidation.setNumberOfRepeats(event.getNumberOfRepeats());
        eventWithFieldsAfterValidation.setFrequencyInDays(event.getFrequencyInDays());
    }

    // TODO: 22.02.2023  
//        public List<CyclicEvent> getAllCyclicEvents() {
//        log.info("CyclicEventService - getAllCyclicEvents()");
//        List<CyclicEvent> allCyclicEvents = cyclicEventRepository.findAll();
//
//        log.info("CyclicEventService - getAllCyclicEvents() return {}", allCyclicEvents.toString());
//        return allCyclicEvents;
//    }
}
