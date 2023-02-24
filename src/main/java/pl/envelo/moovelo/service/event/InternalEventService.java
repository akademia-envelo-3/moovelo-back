package pl.envelo.moovelo.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.controller.searchutils.EventSearchSpecification;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;
import pl.envelo.moovelo.repository.event.EventRepositoryManager;
import pl.envelo.moovelo.service.HashTagService;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.actors.EventOwnerService;
import pl.envelo.moovelo.service.group.GroupService;

@Service
@Slf4j
public class InternalEventService<C extends InternalEvent> extends EventService<C> {

    private final GroupService groupService;

    public InternalEventService(EventRepositoryManager eventRepositoryManager,
                                EventInfoService eventInfoService,
                                EventOwnerService eventOwnerService,
                                HashTagService hashTagService,
                                BasicUserService basicUserService,
                                EventSearchSpecification eventSearchSpecification,
                                GroupService groupService) {
        super(eventRepositoryManager, eventInfoService, eventOwnerService, hashTagService,
                basicUserService, eventSearchSpecification);
        this.groupService = groupService;
    }

    @Override
    protected void setValidatedBasicEventFields(C event, Long userId, C eventWithFieldsAfterValidation) {
        super.setValidatedBasicEventFields(event, userId, eventWithFieldsAfterValidation);
        eventWithFieldsAfterValidation.setPrivate(event.isPrivate());
    }

    @Override
    protected void setGroupToEventIfEventIsInternal(C eventAfterFieldValidation, Long groupId) {
        Group groupById = groupService.getGroupById(groupId);
        eventAfterFieldValidation.setGroup(groupById);
    }

//    public List<? extends Event> getAllInternalEvents() {
//        log.info("InternalEventService - getAllInternalEvents()");
//        List<? extends Event> allInternalEvents = internalEventRepository.findAll();
//
//        log.info("InternalEventService - getAllInternalEvents() return {}", allInternalEvents.toString());
//        return allInternalEvents;
//    }
}
