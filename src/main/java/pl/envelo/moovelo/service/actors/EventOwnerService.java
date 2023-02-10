package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;

@AllArgsConstructor
@Service
@Slf4j
public class EventOwnerService {

    private final EventOwnerRepository eventOwnerRepository;

    public EventOwner assignEventOwnerToCurrentEvent(Long userId) {
        EventOwner eventOwnerBasedOnBasicUser;

        if (eventOwnerRepository.findEventOwnerByUserId(userId).isPresent()) {
            eventOwnerBasedOnBasicUser = assignExistingEventOwnerBasedOnUser(userId);
        } else {
            eventOwnerBasedOnBasicUser = createEventOwnerBasedOnExistingUser(userId);
        }
        return eventOwnerBasedOnBasicUser;
    }

    private EventOwner createEventOwnerBasedOnExistingUser(Long userId) {
        EventOwner eventOwner = new EventOwner();
        eventOwner.setUserId(userId);
        return eventOwner;
    }

    private EventOwner assignExistingEventOwnerBasedOnUser(Long userId) {
        return eventOwnerRepository.findEventOwnerByUserId(userId).get();
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
