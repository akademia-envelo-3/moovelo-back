package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.controller.dto.comment.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.comment.CommentResponseDto;
import pl.envelo.moovelo.controller.mapper.CommentMapper;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.exception.UnauthorizedRequestException;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.service.AuthorizationService;
import pl.envelo.moovelo.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class CommentController {

    private CommentService commentService;
    private AuthorizationService authorizationService;

    @PostMapping(value = "events/{eventId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long eventId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        log.info("CommentController - addComment(eventId = '{}', commentRequestDto = '{}')", eventId, commentRequestDto);
        Comment comment = CommentMapper.mapCommentRequestDtoToComment(commentRequestDto);

        if (authorizationService.isLoggedUserIdEqualToBasicUserIdParam(commentRequestDto.getBasicUser().getId())) {
            throw new UnauthorizedRequestException("You can't add comments for other users");
        }

        if (commentRequestDto.getFiles() != null) {
            List<Attachment> attachments = commentRequestDto.getFiles().stream().map(AttachmentMapper::mapMultipartFileToAttachment).toList();
            comment.setAttachments(attachments);
        }

        comment = commentService.addCommentToEvent(comment, eventId);
        CommentResponseDto commentResponseDto = CommentMapper.mapCommentToCommentResponseDto(comment);

        log.info("CommentController - addComment(eventId = '{}', commentRequestDto = '{}') - return commentResponseDto = '{}'",
                eventId, commentRequestDto, commentResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    @GetMapping("events/{eventId}/comments")
    public ResponseEntity<Page<CommentResponseDto>> getEventComments(
            @PathVariable Long eventId,
            SortingAndPagingCriteria sortingAndPagingCriteria
    ) {
        log.info("EventController - getEvents(eventId = '{}', sortingAndPagingCriteria = '{}')",
                eventId, sortingAndPagingCriteria);

        Page<Comment> comments = commentService.getEventComments(eventId, sortingAndPagingCriteria);
        Page<CommentResponseDto> commentResponseDtos = comments.map(CommentMapper::mapCommentToCommentResponseDto);

        log.info("EventController - getEvents(eventId = '{}', sortingAndPagingCriteria = '{}') - return commentResponseDtos = '{}'",
                eventId, sortingAndPagingCriteria, commentResponseDtos);

        return ResponseEntity.ok(commentResponseDtos);
    }
}
