package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.CommentPage;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.CommentResponseDto;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.event.EventService;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class CommentService {
    private CommentRepository commentRepository;
    private AuthenticatedUser authenticatedUser;
    private BasicUserService basicUserService;

    public Comment addComment(Long eventID, CommentRequestDto commentRequestDto) {
        log.info("CommentService - addComment(eventID = {} ,commentRequestDto = {} )"
                , eventID, commentRequestDto);

        User user = authenticatedUser.getAuthenticatedUser();
        BasicUser basicUser = basicUserService.getBasicUserById(user.getId());

        Event event = new Event();
        event.setId(1L);

        Comment comment = new Comment();
        comment.setEvent(event);
        comment.setBasicUser(basicUser);
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());

        log.info("return comment = {}", comment);

        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsByEvent(Event event, CommentPage commentPage) {
        Sort sort = Sort.by(commentPage.getSortDirection(), commentPage.getSortBy());
        Pageable pageable = PageRequest.of(commentPage.getPageNumber(), commentPage.getPageSize(), sort);
        List<Comment> comments = commentRepository.findAllByEvent(event, pageable);
        Page<Comment> commentResponsePage = new PageImpl<>(comments);
        return commentResponsePage;
    }
}
