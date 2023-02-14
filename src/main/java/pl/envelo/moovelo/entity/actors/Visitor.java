package pl.envelo.moovelo.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.events.ExternalEvent;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Visitor extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "visitors")
    private List<ExternalEvent> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visitor visitor = (Visitor) o;
        return Objects.equals(id, visitor.id) && Objects.equals(events, visitor.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, events);
    }
}
