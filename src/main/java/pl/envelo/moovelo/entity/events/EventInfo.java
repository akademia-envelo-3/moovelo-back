package pl.envelo.moovelo.entity.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.Location;
import pl.envelo.moovelo.entity.categories.Category;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class EventInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "eventInfo")
    private Event event;

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
