package pl.envelo.moovelo.service.actors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventOwnerService {

    private EventOwnerRepository eventOwnerRepository;

    @Autowired
    public EventOwnerService(EventOwnerRepository eventOwnerRepository) {
        this.eventOwnerRepository = eventOwnerRepository;
    }

    /**
     * Method check if location is assigned to any EventInfo. If list of EventInfos is empty,
     * then location entity is remove from database.
     */
    public void removeEventOwnerWithNoEvents(EventOwner eventOwner) {
        log.info("EventOwnerService - removeEventOwnerWithNoEvents() - eventOwner = {}", eventOwner);
        if (eventOwner.getEvents().isEmpty()) {
            eventOwnerRepository.delete(eventOwner);
            log.info("EventOwnerService - removeEventOwnerWithNoEvents() - eventOwner removed");
        }
    }
}
