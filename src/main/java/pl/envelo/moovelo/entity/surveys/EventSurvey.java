package pl.envelo.moovelo.entity.surveys;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class EventSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<BasicUser> surveyees;

    @ManyToOne
    private Event event;

    private Boolean isMultipleChoice;

    private String question;

    @OneToMany(mappedBy = "eventSurvey", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
