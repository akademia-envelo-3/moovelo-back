package pl.envelo.moovelo.controller.mapper.survey;

import pl.envelo.moovelo.controller.dto.survey.AnswerResponseDto;
import pl.envelo.moovelo.controller.dto.survey.EventSurveyRequestAnswerDto;
import pl.envelo.moovelo.entity.surveys.Answer;

public class AnswerMapper {
    public static AnswerResponseDto mapAnswerToAnswerResponseDto(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .value(answer.getAnswerValue())
                .voted(answer.getBasicUsers().size())
                .build();
    }

    public static Answer mapEventSurveyRequestAnswerDtoToAnswer(EventSurveyRequestAnswerDto eventSurveyRequestAnswerDto) {
        Answer answer = new Answer();
        answer.setAnswerValue(eventSurveyRequestAnswerDto.getValue());
        return answer;
    }
}
