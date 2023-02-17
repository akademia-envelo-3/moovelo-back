package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.CommentPage;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.controller.mapper.AttachmentMapper;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        List<Attachment> attachments;

        Event event = new Event();
        event.setId(1L);

        Comment comment = new Comment();
        comment.setEvent(event);
        comment.setBasicUser(basicUser);
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());


        if (commentRequestDto.getAttachments() == null) {
            attachments = new ArrayList<>();
            comment.setAttachments(attachments);
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
