package pl.envelo.moovelo.service.survey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.surveys.Answer;
import pl.envelo.moovelo.entity.surveys.EventSurvey;
import pl.envelo.moovelo.repository.survey.EventSurveyRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventSurveyService {
    private final EventSurveyRepository eventSurveyRepository;

    public EventSurvey createNewSurvey(EventSurvey eventSurvey, Event event) {

        eventSurvey.setSurveyees(new ArrayList<>());
        eventSurvey.setEvent(event);

        List<Answer> answers = eventSurvey.getAnswers();
        answers.forEach(answer -> {
            answer.setBasicUsers(new ArrayList<>());
            answer.setEventSurvey(eventSurvey);
        });

        eventSurvey.setAnswers(answers);
        eventSurveyRepository.save(eventSurvey);
        return eventSurvey;
    }
}
