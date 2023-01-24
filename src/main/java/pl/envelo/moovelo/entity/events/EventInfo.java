package pl.envelo.moovelo.entity.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Event event;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @ManyToOne
    private Location location;

    private Boolean isConfirmationRequired;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<Attachment> files;
}
