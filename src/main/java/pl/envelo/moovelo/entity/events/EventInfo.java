package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @ManyToOne
    private Location location;

    private Boolean isConfirmationRequired;

    @ManyToOne
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventInfo")
    private List<Attachment> files;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventInfo eventInfo = (EventInfo) o;
        return Objects.equals(id, eventInfo.id) && Objects.equals(name, eventInfo.name)
                && Objects.equals(description, eventInfo.description) && Objects.equals(startDate, eventInfo.startDate)
                && Objects.equals(location, eventInfo.location) && Objects.equals(isConfirmationRequired, eventInfo.isConfirmationRequired)
                && Objects.equals(category, eventInfo.category) && Objects.equals(files, eventInfo.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startDate, location, isConfirmationRequired, category, files);
    }
}
