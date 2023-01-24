package pl.envelo.moovelo.entity;

import lombok.*;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @ManyToOne
    private EventInfo eventInfo;

    @ManyToOne
    private Comment comment;


    public Attachment(EventInfo info) {
    }

    public Attachment(Comment comment) {

    }
}
