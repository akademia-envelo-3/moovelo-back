package pl.envelo.moovelo.entity.surveys;

import pl.envelo.moovelo.entity.actors.BasicUser;

import java.util.List;

public class Answer {
    Long id;
    EventSurvey eventSurvey;
    String value;
    List<BasicUser> users;
}
