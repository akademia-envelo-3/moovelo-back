package pl.envelo.moovelo.entity.groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.InternalEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GroupOwner groupOwner;

    @ManyToMany
    @JoinTable(
            name = "groups_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "basic_user_id")
    )
    private List<BasicUser> members;

    @OneToOne
    private GroupInfo groupInfo;

    @OneToMany(mappedBy = "group")
    private List<InternalEvent> events;

    private LocalDateTime creationDate;
}
