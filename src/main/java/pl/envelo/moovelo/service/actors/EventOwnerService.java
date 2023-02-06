package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;

@AllArgsConstructor
@Service
public class EventOwnerService {
    private final EventOwnerRepository eventOwnerRepository;

    public EventOwner assignEventOwnerToCurrentEvent(Long userId) {
        EventOwner eventOwnerBasedOnBasicUser;

        if (eventOwnerRepository.findEventOwnerByUserId(userId) == null) {
            eventOwnerBasedOnBasicUser = createEventOwnerBasedOnExistingUser(userId);
        } else {
            eventOwnerBasedOnBasicUser = assignExistingEventOwnerBasedOnUser(userId);
        }
        return eventOwnerBasedOnBasicUser;
    }

    private EventOwner createEventOwnerBasedOnExistingUser(Long userId) {
        EventOwner eventOwner = new EventOwner();
        eventOwner.setUserId(userId);
        return eventOwner;
    }

    private EventOwner assignExistingEventOwnerBasedOnUser(Long userId) {
        return eventOwnerRepository.findEventOwnerByUserId(userId);
    }
}
