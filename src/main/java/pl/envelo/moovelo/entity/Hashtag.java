package pl.envelo.moovelo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashtagValue;

    private Boolean visible;

    @ManyToMany(mappedBy = "hashtags")
    private List<Event> events;

    private int occurrences;
}
