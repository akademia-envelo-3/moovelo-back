package pl.envelo.moovelo.controller.mapper.survey;

import pl.envelo.moovelo.controller.dto.survey.EventSurveyDto;
import pl.envelo.moovelo.controller.dto.survey.EventSurveyRequestDto;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.Answer;
import pl.envelo.moovelo.entity.surveys.EventSurvey;

import java.util.List;
import java.util.stream.Collectors;

public class EventSurveyMapper {
    public static EventSurveyDto mapEventSurveyToEventSurveyDto(EventSurvey eventSurvey, BasicUser basicUser) {
        return EventSurveyDto.builder()
                .id(eventSurvey.getId())
                .question(eventSurvey.getQuestion())
                .isMultipleChoice(eventSurvey.getIsMultipleChoice())
                .answers(eventSurvey.getAnswers()
                        .stream()
                        .map(AnswerMapper::mapAnswerToAnswerResponseDto)
                        .collect(Collectors.toList()))
                .yourAnswerIds(basicUser.getSurveyAnswers()
                        .stream()
                        .filter(answer -> answer.getEventSurvey().getId().equals(eventSurvey.getId()))
                        .map(Answer::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public static EventSurveyDto mapEventSurveyToEventSurveyDto(EventSurvey eventSurvey) {
        return EventSurveyDto.builder()
                .id(eventSurvey.getId())
                .question(eventSurvey.getQuestion())
                .isMultipleChoice(eventSurvey.getIsMultipleChoice())
                .answers(eventSurvey.getAnswers()
                        .stream()
                        .map(AnswerMapper::mapAnswerToAnswerResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static EventSurvey mapEventSurveyRequestDtoToEventSurvey(EventSurveyRequestDto eventSurveyRequestDto) {
        EventSurvey eventSurvey = new EventSurvey();
        eventSurvey.setQuestion(eventSurveyRequestDto.getQuestion());
        eventSurvey.setIsMultipleChoice(eventSurveyRequestDto.isMultipleChoice());

        List<Answer> answers = eventSurveyRequestDto.getAnswers()
                .stream()
                .map(AnswerMapper::mapEventSurveyRequestAnswerDtoToAnswer)
                .collect(Collectors.toList());
        eventSurvey.setAnswers(answers);

        return eventSurvey;
    }
}
