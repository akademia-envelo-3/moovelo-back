package pl.envelo.moovelo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashtagValue;

    private Boolean visible;

    @ManyToMany
    private List<Event> events;

    private int occurrences;
}
