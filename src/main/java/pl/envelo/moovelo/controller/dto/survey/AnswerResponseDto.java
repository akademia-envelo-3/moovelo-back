package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnswerResponseDto {
    private long id;
    private long eventSurveyId;
    private String value;
    private int voted;
}
