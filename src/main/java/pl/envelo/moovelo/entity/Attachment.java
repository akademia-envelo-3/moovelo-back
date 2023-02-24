package pl.envelo.moovelo.entity;

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

    private String fileName;

    private String fileType;

    private long fileSize;

    @Lob
    private byte[] data;

    @ManyToOne
    private EventInfo eventInfo;

    @ManyToOne
    private Comment comment;
}
