package pl.envelo.moovelo.repository.event;

import io.swagger.models.auth.In;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.entity.groups.Group;

import java.util.List;

@Repository
public interface InternalEventRepository<C extends InternalEvent> extends EventRepository<C> {
    List<C> findAll();

    List<C> findByGroupId(Long groupId);
}