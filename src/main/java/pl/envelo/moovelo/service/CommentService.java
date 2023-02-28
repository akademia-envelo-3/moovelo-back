package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventType;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.event.EventService;

@AllArgsConstructor
@Service
@Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private BasicUserService basicUserService;
    private EventService eventService;

    public Comment addCommentToEvent(Comment comment, Long eventId) {
        log.info("CommentService - addCommentToEvent(comment = '{}', eventId = '{}')", comment, eventId);
        Event event = eventService.getEventById(eventId, EventType.EVENT);
        BasicUser basicUser = basicUserService.getBasicUserById(comment.getBasicUser().getId());
        comment.setEvent(event);
        comment.setBasicUser(basicUser);
        Comment savedComment = commentRepository.save(comment);
        log.info("CommentService - addCommentToEvent(comment = '{}', eventId = '{}') - return savedComment = '{}'",
                comment, event, savedComment);
        return comment;
    }

    public Page<Comment> getEventComments(Long eventId, SortingAndPagingCriteria sortingAndPagingCriteria) {
        log.info("CommentService - getEventComments(eventId = '{}', sortingAndPagingCriteria = '{}')",
                eventId, sortingAndPagingCriteria);

        Pageable pageable = PageRequest.of(
                sortingAndPagingCriteria.getPageNumber(),
                sortingAndPagingCriteria.getPageSize(),
                Sort.by(new Sort.Order(
                        sortingAndPagingCriteria.getSortDirection(),
                        sortingAndPagingCriteria.getSortBy()
                ))
        );

        Page<Comment> comments = commentRepository.findAllByEvent_Id(eventId, pageable);
        log.info("CommentService - getEventComments(eventId = '{}', sortingAndPagingCriteria = '{}') - return comments = '{}'",
                eventId, sortingAndPagingCriteria, comments);
        return comments;
    }
}
