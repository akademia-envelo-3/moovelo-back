package pl.envelo.moovelo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEvent(Event event, Pageable pageable);
}