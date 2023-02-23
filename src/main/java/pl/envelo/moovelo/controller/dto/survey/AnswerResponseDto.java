package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnswerResponseDto {
    private long id;
    private String value;
    private int voted;
}
