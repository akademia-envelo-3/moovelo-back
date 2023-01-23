package pl.envelo.moovelo.controller.dto.survey;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.survey.AnswerResponseDto;

import java.util.List;

@Builder
public class EventSurveyDto {
    private long id;
    private long eventId;
    private String question;
    private boolean isMultipleChoice;
    private List<AnswerResponseDto> answers;
    private List<Long> yourAnswerIds;

}
