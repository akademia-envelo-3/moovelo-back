package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.Attachment;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@Setter
public class CommentRequestDto {

    @NotNull
    @PositiveOrZero
    private Long basicUserId;

    @NotBlank
    private String text;

    private List<AttachmentDto> attachments;

}
