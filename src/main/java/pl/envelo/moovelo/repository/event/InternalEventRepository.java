package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.InternalEvent;

import java.util.List;

@Repository
public interface InternalEventRepository<C extends InternalEvent> extends EventRepository<C> {
    List<C> findAll();
}