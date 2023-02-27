package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.controller.dto.comment.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.comment.CommentResponseDto;
import pl.envelo.moovelo.controller.mapper.CommentMapper;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Comment;
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

    @PostMapping(value = "events/{eventId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long eventId,
            @RequestPart CommentRequestDto commentRequestDto
    ) {
        log.info("CommentController - addComment(eventId = '{}', commentRequestDto = '{}')", eventId, commentRequestDto);
        List<Attachment> attachments = commentRequestDto.getFiles().stream().map(AttachmentMapper::mapMultipartFileToAttachment).toList();

        Comment comment = CommentMapper.mapCommentRequestDtoToComment(commentRequestDto);
        comment.setAttachments(attachments);

        comment = commentService.addCommentToEvent(comment, eventId);
        CommentResponseDto commentResponseDto = CommentMapper.mapCommentToCommentResponseDto(comment);

        log.info("CommentController - addComment(eventId = '{}', commentRequestDto = '{}') - return commentResponseDto = '{}'",
                eventId, commentRequestDto, commentResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }
}
