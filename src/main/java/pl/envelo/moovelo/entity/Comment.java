package pl.envelo.moovelo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @ManyToOne
    private BasicUser basicUser;

    private String text;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
}
