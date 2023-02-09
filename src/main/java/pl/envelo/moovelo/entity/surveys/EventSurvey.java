package pl.envelo.moovelo.entity.surveys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BasicUser> surveyees;

    @ManyToOne
    private Event event;

    private Boolean isMultipleChoice;

    private String question;

    @OneToMany(mappedBy = "eventSurvey", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
