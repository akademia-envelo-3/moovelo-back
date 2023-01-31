package pl.envelo.moovelo.service.actors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.repository.event.EventOwnerRepository;

@AllArgsConstructor
@Service
public class EventOwnerService {

    private final EventOwnerRepository eventOwnerRepository;

    public EventOwner createNewEventOwner(Long userId) {
        EventOwner eventOwnerBasedOnBasicUser = createEventOwnerBasedOnExistingUser(userId);
        eventOwnerRepository.save(eventOwnerBasedOnBasicUser);
        return eventOwnerBasedOnBasicUser;
    }

    private EventOwner createEventOwnerBasedOnExistingUser(Long userId) {
        EventOwner eventOwner = new EventOwner();
        eventOwner.setUserId(userId);
        return eventOwner;
    }
}
