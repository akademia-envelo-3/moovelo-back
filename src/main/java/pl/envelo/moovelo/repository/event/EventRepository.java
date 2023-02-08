package pl.envelo.moovelo.repository.event;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventOwner;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

@Primary
@Repository
public interface EventRepository<I extends Event> extends JpaRepository<I, Long> {

    List<I> findAll();

    List<I> findAllByEventType(EventType eventType);

    List<I> findByEventOwner_UserId(Long userId);

    List<I> findAllByEventOwnerId(Long eventOwnerId);
}