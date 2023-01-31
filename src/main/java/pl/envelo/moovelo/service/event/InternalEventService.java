package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.repository.event.InternalEventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class InternalEventService {

    private InternalEventRepository internalEventRepository;

    @Autowired
    public InternalEventService(InternalEventRepository internalEventRepository) {
        this.internalEventRepository = internalEventRepository;
    }

    public List<? extends Event> getAllInternalEvents() {
        log.info("InternalEventService - getAllInternalEvents()");
        List<? extends Event> allInternalEvents = internalEventRepository.findAll();

        log.info("InternalEventService - getAllInternalEvents() return {}", allInternalEvents.toString());
        return allInternalEvents;
    }

    public List <? extends Event> getAllInternalEventsByGroupId(Long id){
        log.info("InternalEventService - getAllInternalEventsByGroupId()");
        List<? extends Event> allInternalEvents = internalEventRepository.findByGroupId(id);

        log.info("InternalEventService - getAllInternalEventsByGroupId() return {}", allInternalEvents.toString());
        return allInternalEvents;
    }
}
