package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.ExternalEvent;

@Repository
public interface ExternalEventRepository extends EventRepository<ExternalEvent> {
}