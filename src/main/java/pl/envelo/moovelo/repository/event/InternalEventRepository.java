package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.InternalEvent;

@Repository
public interface InternalEventRepository<C extends InternalEvent> extends EventRepository<InternalEvent> {
}