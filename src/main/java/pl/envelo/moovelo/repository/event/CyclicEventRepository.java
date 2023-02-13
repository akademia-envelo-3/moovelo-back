package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.CyclicEvent;

import java.util.List;

@Repository
public interface CyclicEventRepository extends InternalEventRepository<CyclicEvent> {
    List<CyclicEvent> findAllEventByEventOwnerId(Long eventOwnerId);

}