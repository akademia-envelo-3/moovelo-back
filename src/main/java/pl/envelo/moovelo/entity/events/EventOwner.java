package pl.envelo.moovelo.entity.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "eventOwner")
    private List<Event> events;
}
