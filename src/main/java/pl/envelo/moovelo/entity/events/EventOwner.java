package pl.envelo.moovelo.entity.events;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long basicUserId;

    @OneToMany(mappedBy = "eventOwner")
    private List<Event> events;
}
