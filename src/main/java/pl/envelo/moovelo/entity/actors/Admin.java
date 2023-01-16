package pl.envelo.moovelo.entity.actors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.envelo.moovelo.entity.categories.CategoryProposal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "ADMIN")
@Getter
@Setter
public class Admin extends User {

    public Admin() {
    }

    public Admin(String firstname, String lastname, String email,
                 String login, String password, Role role) {
        super(firstname, lastname, email, login, password, role);
    }
}
