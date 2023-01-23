package pl.envelo.moovelo.entity.actors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public abstract class Person {
    private String firstname;
    private String lastname;
    private String email;
}

