package pl.envelo.moovelo.entity.surveys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.surveys.Answer;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<BasicUser> surveys;

    @ManyToOne
    private Event event;

    private Boolean isMultipleChoice;

    private String question;

    @OneToMany(mappedBy = "eventSurvey")
    private List<Answer> answers;
}
