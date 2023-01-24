package pl.envelo.moovelo.repository;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.ExternalEvent;

@Repository
public interface ExternalEventRepository<E extends ExternalEvent> extends EventRepository<E> {

}
