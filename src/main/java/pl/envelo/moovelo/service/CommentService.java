package pl.envelo.moovelo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.CommentPage;
import pl.envelo.moovelo.controller.AuthenticatedUser;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.controller.mapper.CommentMapper;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.actors.User;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.repository.CommentRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;
import pl.envelo.moovelo.service.event.EventService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private CommentRepository commentRepository;
    private EventService eventService;
    private AuthenticatedUser authenticatedUser;
    private BasicUserService basicUserService;

    public Comment addComment(Long eventID, CommentRequestDto commentRequestDto) {
        Event eventById = eventService.getEventById(eventID);
        User user = authenticatedUser.getAuthenticatedUser();
        BasicUser basicUser = basicUserService.getBasicUserById(user.getId());

        Comment comment = new Comment();
        comment.setEvent(eventById);
        comment.setBasicUser(basicUser);
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());

        return comment;
    }

    public Page<Comment> getComments(CommentPage commentPage) {
        Sort sort = Sort.by(commentPage.getSortDirection(), commentPage.getSortBy());
        Pageable pageable = PageRequest.of(commentPage.getPageNumber(), commentPage.getPageSize(), sort);
        return commentRepository.findAll(pageable);
    }
}
