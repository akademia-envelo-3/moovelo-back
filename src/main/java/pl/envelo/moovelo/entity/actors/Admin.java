package pl.envelo.moovelo.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
}
