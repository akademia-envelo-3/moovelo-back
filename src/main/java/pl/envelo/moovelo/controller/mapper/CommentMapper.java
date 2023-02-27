package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;
import pl.envelo.moovelo.controller.dto.comment.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.comment.CommentResponseDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public class CommentMapper {

    public static Comment mapCommentRequestDtoToComment(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setBasicUser(BasicUserMapper.map(commentRequestDto.getBasicUser()));
        comment.setDate(LocalDateTime.now());
        return comment;
    }

    public static CommentResponseDto mapCommentToCommentResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setEventId(comment.getEvent().getId());
        commentResponseDto.setUser(BasicUserMapper.map(comment.getBasicUser()));
        commentResponseDto.setText(comment.getText());
        commentResponseDto.setAttachments(
                comment.getAttachments() == null ?
                        List.of() :
                        comment.getAttachments()
                                .stream()
                                .map(AttachmentMapper::mapAttachmentToAttachmentResponseDto)
                                .toList()
        );
        commentResponseDto.setDate(comment.getDate());
        return commentResponseDto;
    }
}
