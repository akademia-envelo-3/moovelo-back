package pl.envelo.moovelo.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.EventInfo;

@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
}