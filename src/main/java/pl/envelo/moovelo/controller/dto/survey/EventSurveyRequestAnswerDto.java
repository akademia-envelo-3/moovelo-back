package pl.envelo.moovelo.controller.dto.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
public class EventSurveyRequestAnswerDto {

    private Long id;
    private String value;

}
