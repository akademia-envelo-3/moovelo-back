package pl.envelo.moovelo.controller.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class EventSurveyRequestAnswerDto {
    private String value;

    public EventSurveyRequestAnswerDto(String value) {
        this.value = value;
    }
}
