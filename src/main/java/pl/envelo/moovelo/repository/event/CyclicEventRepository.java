package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.CyclicEvent;

@Repository
public interface CyclicEventRepository extends InternalEventRepository<CyclicEvent> {
}