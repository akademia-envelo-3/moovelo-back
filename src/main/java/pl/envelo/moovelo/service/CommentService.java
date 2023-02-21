package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.CommentPage;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class CommentService {
    private CommentRepository commentRepository;
    private AuthorizationService authorizationService;
    private BasicUserService basicUserService;

    public Comment addComment(Event event, CommentRequestDto commentRequestDto) {
        log.info("CommentService - addComment(eventID = {} ,commentRequestDto = {} )"
                , event, commentRequestDto);

        Long basicUserId = authorizationService.getLoggedBasicUserId();
        BasicUser basicUser = basicUserService.getBasicUserById(basicUserId);

        Comment comment = new Comment();
        comment.setEvent(event);
        comment.setBasicUser(basicUser);
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());


        if (commentRequestDto.getAttachments() == null) {
            comment.setAttachments(List.of());
        }

        log.info("CommentService - return comment = {}", comment);

        return commentRepository.save(comment);
    }

    public Page<Comment> getAllCommentsByEvent(Event event, CommentPage commentPage) {
        log.info("CommentService - getAllCommentsByEvent(event = {}, commentPage = {}", event, commentPage);

        Sort sort = Sort.by(commentPage.getSortDirection(), commentPage.getSortBy());
        Pageable pageable = PageRequest.of(commentPage.getPageNumber(), commentPage.getPageSize(), sort);
        List<Comment> comments = commentRepository.findAllByEvent(event, pageable);
        Page<Comment> commentResponsePage = new PageImpl<>(comments);

        log.info("CommentService - return commentResponsePage = {}", commentResponsePage);

        return commentResponsePage;
    }

    public Comment getCommentById(Long id) {
        log.info("CommentService - getCommentById(Long id)");

        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            return commentOptional.get();
        } else {
            throw new NoSuchElementException("No comment with id: " + id);
        }
    }
}
