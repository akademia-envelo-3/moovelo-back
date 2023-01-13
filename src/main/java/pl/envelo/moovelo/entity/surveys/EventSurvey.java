package pl.envelo.moovelo.entity.surveys;

import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.surveys.Answer;

import java.util.List;

public class EventSurvey {
    Long id;
    List<BasicUser> surveys;
    Event event;
    Boolean isMultipleChoice;
    String question;
    List<Answer> answers;
}
