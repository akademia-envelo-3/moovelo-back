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
public abstract class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private Role role;

    public User() {
    }

    public User(String firstname, String lastname, String email,
                String login, String password, Role role) {
        super(firstname, lastname, email);
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
