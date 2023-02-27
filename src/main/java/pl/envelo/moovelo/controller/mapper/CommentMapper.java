package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.comment.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.comment.CommentResponseDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.entity.Comment;

public class CommentMapper {

    public static Comment mapCommentRequestDtoToComment(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setBasicUser(BasicUserMapper.map(commentRequestDto.getBasicUser()));
        return comment;
    }

    public static CommentResponseDto mapCommentToCommentResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setAttachments(
                comment.getAttachments()
                        .stream()
                        .map(AttachmentMapper::mapAttachmentToAttachmentResponseDto)
                        .toList()
        );
        return commentResponseDto;
    }
}
