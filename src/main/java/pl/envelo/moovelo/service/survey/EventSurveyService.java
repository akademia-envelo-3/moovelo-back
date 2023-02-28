package pl.envelo.moovelo.service.survey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.surveys.Answer;
import pl.envelo.moovelo.entity.surveys.EventSurvey;
import pl.envelo.moovelo.exception.UserAlreadyVotedException;
import pl.envelo.moovelo.repository.survey.EventSurveyRepository;
import pl.envelo.moovelo.service.actors.BasicUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventSurveyService {
    private final EventSurveyRepository eventSurveyRepository;

    private final BasicUserService basicUserService;

    public EventSurvey getSurveyById(Long id) {
        log.info("EventSurvey - getSurveyById()");
        Optional<EventSurvey> surveyOptional = eventSurveyRepository.findById(id);
        if (surveyOptional.isEmpty()) {
            throw new NoSuchElementException("No survey with id: " + id);
        }
        log.info("EventSurvey - getSurveyById() return {}", surveyOptional.get());
        return surveyOptional.get();
    }

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

    @Transactional
    public void voteInSurvey(List<Long> userAnswersIds, Long surveyId, Long basicUserId) {
        log.info("EventSurveyService - voteInSurvey()");
        EventSurvey eventSurvey = getSurveyById(surveyId);

        validateSurveyInput(userAnswersIds, eventSurvey);

        addUserToSurveyees(basicUserId, eventSurvey);

        basicUserService.voteForAnswers(userAnswersIds, basicUserId);
    }

    @Transactional
    public void addUserToSurveyees(Long basicUserId, EventSurvey eventSurvey) {
        log.info("EventSurveyService - addUserToSurveyees()");
        BasicUser basicUser = basicUserService.getBasicUserById(basicUserId);

        List<BasicUser> surveyees = eventSurvey.getSurveyees();
        if (surveyees.contains(basicUser)) {
            throw new UserAlreadyVotedException("Logged in user already voted in survey with id " + eventSurvey.getId());
        } else {
            surveyees.add(basicUser);
            eventSurvey.setSurveyees(surveyees);
        }
    }

    private void validateSurveyInput(List<Long> userAnswersIds, EventSurvey eventSurvey) {

        List<Long> surveyAnswersIds = eventSurvey
                .getAnswers().stream()
                .map(Answer::getId).toList();

        for (Long id : userAnswersIds) {
            if (!surveyAnswersIds.contains(id)) {
                throw new IllegalArgumentException("Survey with id " + eventSurvey.getId() + " does not have an answer with id " + id);
            }
        }

        if (!eventSurvey.getIsMultipleChoice() && userAnswersIds.size() > 1) {
            throw new IllegalArgumentException("Too many answers. Survey with id " + eventSurvey.getId() + " is not multiple choice.");
        }

    }
}
