package pl.envelo.moovelo.controller.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.envelo.moovelo.CommentPage;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.CommentResponseDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.entity.Comment;

import java.util.List;

public class CommentMapper {

    public static CommentResponseDto mapFromCommentToCommentResponseDto(Comment comment){
       return CommentResponseDto.builder()
                .id(comment.getId())
                .basicUserDto(BasicUserMapper.map(comment.getBasicUser()))
                .date(comment.getDate())
                .text(comment.getText())
                .attachments(comment.getAttachments())
                .build();
    }

    public static CommentRequestDto mapFromCommentToCommentRequestDto(Comment comment){
        List<AttachmentDto> attachmentDtoList = comment.getAttachments().stream()
                .map(AttachmentMapper::mapFromAttachmentToAttachmentDto)
                .toList();

        return CommentRequestDto.builder()
                .basicUserId(comment.getBasicUser().getId())
                .text(comment.getText())
                .attachments(attachmentDtoList)
                .build();
    }
}
