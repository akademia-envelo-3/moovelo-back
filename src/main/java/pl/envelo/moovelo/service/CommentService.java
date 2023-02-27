package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.event.EventService;

@AllArgsConstructor
@Service
@Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private EventService eventService;

    public Comment addCommentToEvent(Comment comment, Long eventId) {
        log.info("CommentService - addCommentToEvent(comment = '{}', eventId = '{}')", comment, eventId);
        Event event = eventService.getEventById(eventId, EventType.EVENT);
        comment.setEvent(event);
        Comment savedComment = commentRepository.save(comment);
        log.info("CommentService - addCommentToEvent(comment = '{}', eventId = '{}') - return savedComment = '{}'",
                comment, event, savedComment);
        return comment;
    }
}
