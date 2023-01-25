package pl.envelo.moovelo.repository.event;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.Event;

@Primary
@Repository
public interface EventRepository<I extends Event> extends JpaRepository<I, Long> {
}