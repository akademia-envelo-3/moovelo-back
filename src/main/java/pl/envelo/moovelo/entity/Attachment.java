package pl.envelo.moovelo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.envelo.moovelo.entity.events.EventInfo;

import javax.persistence.*;

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
