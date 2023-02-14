package pl.envelo.moovelo.repository.event;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;

import java.util.List;

@Primary
@Repository
public interface EventRepository<I extends Event> extends JpaRepository<I, Long>, JpaSpecificationExecutor<I> {

    Page<I> findAll(Specification<I> eventSpecification, Pageable pageable);

    List<I> findAllByEventType(EventType eventType);

    List<I> findByEventOwner_UserId(Long userId);

    List<I> findAllByEventOwnerId(Long eventOwnerId);

}