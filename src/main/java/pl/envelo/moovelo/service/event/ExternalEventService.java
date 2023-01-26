package pl.envelo.moovelo.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.repository.event.ExternalEventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExternalEventService {

    private ExternalEventRepository externalEventRepository;

    @Autowired
    public ExternalEventService(ExternalEventRepository externalEventRepository) {
        this.externalEventRepository = externalEventRepository;
    }

    public List<? extends Event> getAllExternalEvents() {
        log.info("ExternalEventService - getAllExternalEvents()");
        List<? extends Event> allExternalEvents = externalEventRepository.findAllByEventType(EventType.EXTERNAL_EVENT);

        log.info("ExternalEventService - getAllExternalEvents() return {}", allExternalEvents.toString());
        return allExternalEvents;
    }
}
