package pl.envelo.moovelo.entity.actors;

import lombok.*;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    private String firstname;
    private String lastname;
    private String email;
}

