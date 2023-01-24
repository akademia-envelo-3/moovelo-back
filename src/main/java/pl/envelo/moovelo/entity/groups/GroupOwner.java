package pl.envelo.moovelo.entity.groups;

import lombok.*;
import pl.envelo.moovelo.entity.groups.Group;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GroupOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long basicUserId;

    @OneToMany(mappedBy = "groupOwner")
    private List<Group> groups;
}
