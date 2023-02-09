package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;

import java.util.List;

@Getter
@Builder
@Setter
public class CommentRequestDto {

    private Long basicUserId;
    private String text;

}
