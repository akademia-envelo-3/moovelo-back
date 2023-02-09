package pl.envelo.moovelo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", hashtagValue='" + hashtagValue + '\'' +
                ", visible=" + visible +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hashtag hashtag)) return false;
        return Objects.equals(
                hashtagValue.compareToIgnoreCase(hashtag.hashtagValue),
                hashtag.hashtagValue.compareToIgnoreCase(hashtagValue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashtagValue);
    }

    public void addEvent(Event savedEvent) {
        events.add(savedEvent);
    }
}
