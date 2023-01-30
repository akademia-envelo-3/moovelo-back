package pl.envelo.moovelo.repository.event;

import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.ExternalEvent;

import java.util.List;

@Repository
public interface ExternalEventRepository extends EventRepository<ExternalEvent> {
    List<ExternalEvent> findAll();
}