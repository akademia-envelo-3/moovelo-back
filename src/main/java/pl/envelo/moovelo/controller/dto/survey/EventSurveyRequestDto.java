package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EventSurveyRequestDto {
    private String question;
    private boolean isMultipleChoice;
    List<EventSurveyRequestAnswerDto> answers;
}
