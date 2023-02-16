package pl.envelo.moovelo.controller.mapper.survey;

import pl.envelo.moovelo.controller.dto.survey.EventSurveyDto;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import java.util.stream.Collectors;

public class EventSurveyMapper {
    public static EventSurveyDto mapEventSurveyToEventSurveyDto(EventSurvey eventSurvey) {
        return EventSurveyDto.builder()
                .id(eventSurvey.getId())
                .eventId(eventSurvey.getEvent().getId())
                .question(eventSurvey.getQuestion())
                .isMultipleChoice(eventSurvey.getIsMultipleChoice())
                .answers(eventSurvey.getAnswers()
                        .stream()
                        .map(AnswerMapper::mapAnswerToAnswerResponseDto)
                        .collect(Collectors.toList()))
                .build();

    }
}
