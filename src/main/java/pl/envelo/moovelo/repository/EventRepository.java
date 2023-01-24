package pl.envelo.moovelo.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

@Repository
@Primary
public interface EventRepository<E extends Event> extends JpaRepository<E, Long> {

    List<E> findAll();
}
