package pl.envelo.moovelo.entity.groups;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.InternalEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "api_group")
@ToString
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

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private GroupInfo groupInfo;

    @OneToMany(mappedBy = "group")
    private List<InternalEvent> events;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;
}
