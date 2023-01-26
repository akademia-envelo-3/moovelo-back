package pl.envelo.moovelo.entity.surveys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.actors.BasicUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EventSurvey eventSurvey;

    private String answerValue;

    @OneToMany
    private List<BasicUser> basicUsers;
}