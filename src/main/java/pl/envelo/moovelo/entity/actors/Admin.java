package pl.envelo.moovelo.entity.actors;

import lombok.*;
import pl.envelo.moovelo.entity.categories.CategoryProposal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
}
