package pl.envelo.moovelo.controller.dto.survey;

import lombok.Getter;

import java.util.List;

@Getter
public class AnswerRequestDto {
    private final List<Long> userAnswersIds;

    public AnswerRequestDto(long surveyId, long userId, List<Long> userAnswersIds) {
        this.userAnswersIds = userAnswersIds;
    }
}
