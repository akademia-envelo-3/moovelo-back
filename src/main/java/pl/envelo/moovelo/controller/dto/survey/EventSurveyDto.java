package pl.envelo.moovelo.controller.dto.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EventSurveyDto {
    private long id;
    private String question;
    private boolean isMultipleChoice;
    private List<AnswerResponseDto> answers;
    private List<Long> yourAnswerIds;

    @JsonProperty(value = "isMultipleChoice")
    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }
}
