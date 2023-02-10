package pl.envelo.moovelo.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.EventOwner;

import java.util.Optional;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, Long> {

    Optional<EventOwner> findEventOwnerByUserId(Long userId);

}
