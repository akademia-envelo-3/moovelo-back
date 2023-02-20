package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class EventSurveyDto {
    private long id;
    private long eventId;
    private String question;
    private boolean isMultipleChoice;
    private List<AnswerResponseDto> answers;
    private List<Long> yourAnswerIds;

}
