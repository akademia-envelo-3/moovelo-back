package pl.envelo.moovelo.entity.actors;

import lombok.*;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Person {
    private String firstname;
    private String lastname;
    private String email;
}

