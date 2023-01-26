package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.repository.event.CyclicEventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CyclicEventService {

    private CyclicEventRepository cyclicEventRepository;

    @Autowired
    public CyclicEventService(CyclicEventRepository cyclicEventRepository) {
        this.cyclicEventRepository = cyclicEventRepository;
    }

    public List<? extends Event> getAllCyclicEvents() {
        log.info("CyclicEventService - getAllCyclicEvents()");
        List<? extends Event> allCyclicEvents = cyclicEventRepository.findAllByEventType(EventType.CYCLIC_EVENT);

        log.info("CyclicEventService - getAllCyclicEvents() return {}", allCyclicEvents.toString());
        return allCyclicEvents;
    }
}
