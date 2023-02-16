package pl.envelo.moovelo.controller.mapper.survey;

import pl.envelo.moovelo.controller.dto.survey.AnswerResponseDto;
import pl.envelo.moovelo.entity.surveys.Answer;

public class AnswerMapper {
    public static AnswerResponseDto mapAnswerToAnswerResponseDto(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .eventSurveyId(answer.getEventSurvey().getId())
                .value(answer.getAnswerValue())
                .voted(answer.getBasicUsers().size())
                .build();
    }
}
