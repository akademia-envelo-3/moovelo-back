package pl.envelo.moovelo.controller.dto.survey;

import java.util.List;

public class AnswerRequestDto {
    private long surveyId;
    private long userId;
    private List<Long> userAnswersIds;

}
