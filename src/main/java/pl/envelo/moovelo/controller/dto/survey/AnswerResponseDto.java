package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;

@Builder
public class AnswerResponseDto {
    private long id;
    private long eventSurveyId;
    private String value;
    private int voted;
}
