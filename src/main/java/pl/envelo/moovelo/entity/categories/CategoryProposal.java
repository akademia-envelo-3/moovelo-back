package pl.envelo.moovelo.entity.categories;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.actors.BasicUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BasicUser basicUser;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
}
