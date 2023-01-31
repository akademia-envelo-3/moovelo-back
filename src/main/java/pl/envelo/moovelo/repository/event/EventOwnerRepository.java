package pl.envelo.moovelo.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.envelo.moovelo.entity.events.EventOwner;

public interface EventOwnerRepository extends JpaRepository<EventOwner, Long> {
}
