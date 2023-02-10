package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Builder
@Setter
public class CommentRequestDto {

    @NotNull
    @PositiveOrZero
    private Long basicUserId;

    @NotBlank
    private String text;

}
