package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;

import java.util.List;

@Builder
public class AnswerRequestDto {
    private long surveyId;
    private long userId;
    private List<Long> userAnswersIds;

}
