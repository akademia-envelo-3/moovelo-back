package pl.envelo.moovelo.entity.surveys;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.actors.BasicUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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