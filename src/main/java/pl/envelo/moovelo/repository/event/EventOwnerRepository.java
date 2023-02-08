package pl.envelo.moovelo.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.EventOwner;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, Long> {

    EventOwner findEventOwnerByUserId(Long userId);

}
