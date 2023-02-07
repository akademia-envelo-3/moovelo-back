package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.CommentDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.entity.Comment;

public class CommentMapper {

    public static CommentDto mapFromCommentToCommentDto(Comment comment){

        CommentDto commentDto = CommentDto.builder()
                .user(BasicUserMapper.map(comment.getBasicUser()))
                .text(comment.getText())
                .build();

        return commentDto;
    }
}
