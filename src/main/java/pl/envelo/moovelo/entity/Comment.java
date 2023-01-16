package pl.envelo.moovelo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.events.Event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @OneToMany(mappedBy = "comment")
    private List<Attachment> attachments;

    @ManyToOne
    private BasicUser basicUser;

    @ManyToMany
    private List<Hashtag> hashtags;

    private String text;

    private LocalDateTime date;
}
