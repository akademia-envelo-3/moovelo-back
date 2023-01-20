package pl.envelo.moovelo.entity.actors;

import lombok.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "USER_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@Getter
@Setter
@Table(name = "api_user")
@NoArgsConstructor
public abstract class User extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
