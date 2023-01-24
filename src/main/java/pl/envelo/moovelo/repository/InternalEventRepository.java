package pl.envelo.moovelo.repository;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.InternalEvent;

@Repository
public interface InternalEventRepository<I extends InternalEvent> extends EventRepository<I> {
}
