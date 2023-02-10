package pl.envelo.moovelo.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.EventOwner;

import java.util.Optional;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, Long> {

    // TODO: 09.02.2023  - zmiana sygnatury metody - typ zwracany Optional<EventOwner>
    EventOwner findEventOwnerByUserId(Long userId);

    Optional<EventOwner> findByEventsId(Long eventId);
}
