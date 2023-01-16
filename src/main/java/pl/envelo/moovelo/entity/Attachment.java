package pl.envelo.moovelo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;
import java.io.File;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private File file;

    @ManyToOne
    private EventInfo eventInfo;

    @ManyToOne
    private Comment comment;


    public Attachment(EventInfo info) {
    }

    public Attachment(Comment comment) {

    }
}
